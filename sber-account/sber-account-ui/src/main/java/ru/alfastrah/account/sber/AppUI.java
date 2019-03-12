package ru.alfastrah.account.sber;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.helper.Version;
import ru.alfastrah.account.sber.model.LoginUser;
import ru.alfastrah.account.sber.navigation.NavigationManager;
import ru.alfastrah.account.sber.pay.view.FailView;
import ru.alfastrah.account.sber.pay.view.SuccessView;
import ru.alfastrah.account.sber.service.LoginService;
import ru.alfastrah.account.sber.service.ProfileService;
import ru.alfastrah.account.sber.styles.MainStyles;
import ru.alfastrah.account.sber.view.AccessDeniedView;
import ru.alfastrah.account.sber.view.login.AuthorizationView;
import ru.alfastrah.account.sber.view.login.PhoneConfirmView;
import ru.alfastrah.account.sber.view.login.RegistrationView;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

/**
 *
 */
@Theme("sbertheme")
@SpringUI
@Title("Личный кабинет ДМС")
@SpringViewDisplay
public class AppUI extends UI implements HasLogger {
    private final SpringViewProvider viewProvider;
    private final NavigationManager navigationManager;
    private final LoginService loginService;
    private final ProfileService profileService;
    private final UITexts uiTexts;
    @Value("${vaadin.servlet.productionMode}")
    String productionMode;

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    public AppUI(SpringViewProvider viewProvider, NavigationManager navigationManager, LoginService loginService,
                 ProfileService profileService, UITexts uiTexts) {
        this.viewProvider = viewProvider;
        this.navigationManager = navigationManager;
        this.loginService = loginService;
        this.profileService = profileService;
        this.uiTexts = uiTexts;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getLogger().trace("Enter to mainUI");
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);
        getPage().setTitle(Version.getVersionInfo());

        setErrorHandler(event -> {
            Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
            getLogger().error("Error during request", t);
        });

        getLogger().trace("navigator {}", getNavigator().hashCode());
        viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);

        navigationManager.addViewChangeListener((ViewChangeListener) viewChangeEvent -> {
            //TODO возможно потом надо переделать на vaadin secure view
            LoginUser loginUser = VaadinSession.getCurrent().getAttribute(LoginUser.class);

            getLogger().trace("loginUser {}", loginUser);
            getLogger().trace("viewChangeEvent {}", viewChangeEvent.getNewView());

            if (loginUser == null && !Arrays.asList(AuthorizationView.class, RegistrationView.class,
                    PhoneConfirmView.class, SuccessView.class, FailView.class).contains(viewChangeEvent.getNewView().getClass())) {
                Notification.show("Доуступ запрещен", Notification.Type.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        });

        Stream.of(VaadinRequest.getCurrent().getCookies())
                .forEach(item -> getLogger().trace("cookie {}", reflectionToString(item)));
        getLogger().trace("productionMode {}", productionMode);

        updateContent();
    }

    public void updateContent() {
        getLogger().info("Enter to updateContent");
        loginService.isAuthenticated();

        LoginUser loginUser = VaadinSession.getCurrent().getAttribute(LoginUser.class);
        getLogger().trace("loginUser {}", loginUser);
        try {
            uiTexts.setLogin(loginUser != null? loginUser.getLogin(): StringUtils.EMPTY);
            if (loginUser != null && StringUtils.isNotBlank(loginUser.getLogin())) {
                //setCurrent();
                profileService.loadProfileData(loginUser.getLogin());

                removeStyleName(MainStyles.LOGIN_VIEW);
                navigationManager.navigateToDefaultView();
            } else {
                addStyleName(MainStyles.LOGIN_VIEW);
                navigationManager.navigateToAuthorizationView();
            }
        } catch (Exception e) {
            getLogger().trace("updateContent exception {}", e);
            Notification.show(ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
        }
    }
}
