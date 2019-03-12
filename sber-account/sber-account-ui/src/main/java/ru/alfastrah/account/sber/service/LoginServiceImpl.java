package ru.alfastrah.account.sber.service;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.backend.exception.AuthException;
import ru.alfastrah.account.sber.backend.exception.SmsException;
import ru.alfastrah.account.sber.backend.model.auth.CheckResult;
import ru.alfastrah.account.sber.backend.service.SmsService;
import ru.alfastrah.account.sber.model.LoginUser;
import ru.alfastrah.account.sber.model.RegisterData;
import ru.alfastrah.account.sber.navigation.NavigationManager;
import ru.alfastrah.account.sber.view.component.greeting.GreetingInfoWindow;
import ru.alfastrah.account.sber.view.login.PhoneConfirmView;
import ru.alfastrah.account.sber.view.login.RegistrationView;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@VaadinSessionScope
public class LoginServiceImpl implements LoginService {
    private static final String COOKIE_NAME = "sber-remember-me";

    private final RegisterData registerData;
    private final UserService userService;
    private final NavigationManager navigationManager;
    private final SmsService smsService;
    private final GreetingInfoWindow greetingInfoWindow;

    @Autowired
    public LoginServiceImpl(UserService userService, NavigationManager navigationManager,
                            RegisterData registerData, SmsService smsService,
                            GreetingInfoWindow greetingInfoWindow) {
        this.userService = userService;
        this.navigationManager = navigationManager;
        this.registerData = registerData;
        this.smsService = smsService;
        this.greetingInfoWindow = greetingInfoWindow;
    }

    @Override
    public void authorize(LoginUser loginUser) {
        getLogger().info("Enter to authorize {}", loginUser);
        try {
            authorizeAndRemember(loginUser);

            Notification.show("Авторизация выполнена успешно", Notification.Type.TRAY_NOTIFICATION);

            ((AppUI) AppUI.getCurrent()).updateContent();
        } catch (AuthException e) {
            getLogger().error("authorize exception {}", e);
            Notification.show("Авторизация не выполнена, проверьте вводимые данные. " + e.getMessage()
                    , Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean isAuthenticated() {
        getLogger().trace("Enter to isAuthenticated");
        LoginUser attribute = getCurrentSession().getAttribute(LoginUser.class);
        getLogger().trace("session attribute {}", attribute);
        return attribute != null || loginRememberedUser();
    }

    @Override
    public void backToAuthorization() {
        navigationManager.navigateToAuthorizationView();
    }

    @Override
    public void navigateTo(Class<? extends View> clazz) {
        navigationManager.navigateTo(clazz);
    }

    @Override
    public void registerUser(String login, Boolean rememberMe) {
        getLogger().trace("Enter to registerUser {} {}", login, rememberMe);

        registerData.setPhone(login);
        registerData.setRememberMe(rememberMe);
        getLogger().trace("registerData {}", registerData);

        navigateTo(RegistrationView.class);
    }

    @Override
    public void checkUser() {
        getLogger().trace("Enter to checkUser");
        try {
            CheckResult checkResult = userService.checkUser(registerData);
            registerData.setUserId(checkResult.getUserId());
            registerData.setFranchise(checkResult.getFranchise());

            sendSmsCode();

            navigationManager.navigateTo(PhoneConfirmView.class);
        } catch (AuthException e) {
            getLogger().error("checkUser exception {}", e);
            Notification.show(ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void confirmData() {
        getLogger().trace("Enter to confirmData {}", registerData);
        try {
            userService.registerUser(registerData);

            LoginUser user = new LoginUser();
            user.setLogin(registerData.getPhone());
            user.setPassword(registerData.getPassword());
            user.setRememberMe(registerData.isRememberMe());

            authorizeAndRemember(user);

            UI currentUI = AppUI.getCurrent();
            ((AppUI) AppUI.getCurrent()).updateContent();

            //showGreetingWindow(currentUI);
        } catch (AuthException e) {
            getLogger().error("confirmData exception {}", e);
            Notification.show(ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void sendSmsCode() {
        try {
            registerData.setSmsCode(getSmsCode());
            smsService.sendSms(registerData.getPhone(), "Ваш код: " + registerData.getSmsCode() + "\nПросьба соблюдать конфиденциальность информации");
            getLogger().trace("sendSmsCode registerData {}", registerData);
        } catch (SmsException e) {
            getLogger().error("SendButton sendSms exception {}", e);
            Notification.show(ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void changePassword(String login, String password) {
        getLogger().trace("Enter to changePassword {} {}", login, password);
        try {
            userService.changePassword(login, password);
        } catch (AuthException e) {
            getLogger().error("changePassword exception {}", e);
            Notification.show(ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void logOut() {
        Optional<Cookie> cookie = getRememberMeCookie();
        if (cookie.isPresent()) {
            String id = cookie.get().getValue();
            userService.removeRememberedUser(id);
            deleteRememberMeCookie();
        }
        getLogger().trace("after delete cookie");

        VaadinSession currentSession = VaadinSession.getCurrent();
        currentSession.setAttribute(LoginUser.class, null);
        currentSession.close();
        getLogger().trace("after close vaadin session");
    }

    private void showGreetingWindow(UI currentUI) {
        getLogger().trace("Enter to showGreetingWindow");
        currentUI.addWindow(greetingInfoWindow);

        greetingInfoWindow.focus();
    }

    private VaadinSession getCurrentSession() {
        return VaadinSession.getCurrent();
    }

    private void authorizeAndRemember(LoginUser loginUser) throws AuthException {
        userService.isAuthenticUser(loginUser);
        getCurrentSession().setAttribute(LoginUser.class, loginUser);

        if (loginUser.isRememberMe()) {
            rememberUser(loginUser);
        } else {
            deleteRememberMeCookie();
        }
    }

    private boolean loginRememberedUser() {
        getLogger().trace("Enter to loginRememberedUser");
        Optional<Cookie> rememberMeCookie = getRememberMeCookie();

        if (rememberMeCookie.isPresent()) {
            String id = rememberMeCookie.get().getValue();
            getLogger().trace("remembered id {}", id);
            LoginUser user = userService.getRememberedUser(id);
            getLogger().trace("remembered user {}", user);

            if (user != null) {
                VaadinSession.getCurrent().setAttribute(LoginUser.class, user);
                return true;
            }
        }

        return false;
    }

    private Optional<Cookie> getRememberMeCookie() {
        getLogger().trace("Enter to getRememberMeCookie");
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(COOKIE_NAME))
                .findFirst();
    }

    private void rememberUser(LoginUser user) {
        getLogger().trace("Enter to rememberUser");
        String id = userService.rememberUser(user);
        getLogger().trace("remember id {}", id);

        Cookie cookie = new Cookie(COOKIE_NAME, id);
        cookie.setSecure(true);
        cookie.setPath(VaadinRequest.getCurrent().getContextPath());
        cookie.setMaxAge(60 * 60 * 24 * 30); // valid for 30 days
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    private void deleteRememberMeCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setSecure(true);
        cookie.setPath(VaadinRequest.getCurrent().getContextPath());
        cookie.setMaxAge(0);
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    String getSmsCode() {
        int code = ThreadLocalRandom.current().nextInt(0, 10000);
        return StringUtils.leftPad(String.valueOf(code), 4, "0");
    }
}
