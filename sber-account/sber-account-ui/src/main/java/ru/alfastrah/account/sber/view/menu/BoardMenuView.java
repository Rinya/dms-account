package ru.alfastrah.account.sber.view.menu;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.constants.BoardMenu;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.navigation.NavigationManager;
import ru.alfastrah.account.sber.service.LoginService;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.view.MenuStyles;
import ru.alfastrah.account.sber.view.board.DentistryView;
import ru.alfastrah.account.sber.view.board.InvoiceView;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.stream.Stream;

import static ru.alfastrah.account.sber.styles.view.MenuStyles.*;

@SpringComponent
@UIScope
public class BoardMenuView extends CustomComponent implements Serializable, HasLogger {
    private static final String LAYOUT_ID = "board-menu";
    private static final String LOGO_IMAGE_ID = "logo_image_id";
    private static final String COMPANY_LOGO_IMAGE = "img/menu/logo.png";

    private NavigationManager navigationManager;
    private ValoMenuItemButton menuItemButton;
    private InsuredProfile profile;
    private LoginService loginService;

    @Autowired
    public BoardMenuView(NavigationManager navigationManager, InsuredProfile profile, LoginService loginService) {
        this.navigationManager = navigationManager;
        this.profile = profile;
        this.loginService = loginService;
    }

    @PostConstruct
    public void init() {
        setPrimaryStyleName(MAIN_STYLE);
        setId(LAYOUT_ID);
        setSizeUndefined();

        addStyleName(BOARD_MENU);

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        /*final CssLayout menuContent = new CssLayout();*/
        final VerticalLayout menuContent = new VerticalLayout();
        menuContent.setMargin(false);
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName(NO_VERTICAL_DRAG_HINTS);
        menuContent.addStyleName(NO_HORIZONTAL_DRAG_HINTS);
        menuContent.setWidthUndefined();
        menuContent.setHeight(100, Unit.PERCENTAGE);

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());
        Component infoLayout = buildInfoLayout();
        menuContent.addComponentsAndExpand(infoLayout);
        menuContent.setComponentAlignment(infoLayout, Alignment.BOTTOM_LEFT);

        return menuContent;
    }

    private Component buildInfoLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setId("info-layout");
        layout.addStyleName(MenuStyles.INFO_LAYOUT);
        layout.setWidthUndefined();
        layout.setHeight(100, Unit.PERCENTAGE);
        Responsive.makeResponsive(layout);

        layout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        //Image image = new Image("", new ThemeResource("img/sber.png"));
        Image image = new Image("", profile.getCompanyLogo());
        image.addStyleName(CommonStyles.MARGIN_TOP_230);
        layout.addComponent(image);

        Label label = new Label(profile.getServiceDeskPhone(), ContentMode.HTML);
        label.setPrimaryStyleName(MenuStyles.INFO_LABEL);
        label.setSizeUndefined();
        layout.addComponent(label);
        return layout;
    }

    private Component buildTitle() {
        Image image = new Image("", new ThemeResource(COMPANY_LOGO_IMAGE));
        image.setId(LOGO_IMAGE_ID);
        image.setSizeFull();
        image.addStyleName(VALO_MENU_TITLE);

        return image;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", (Button.ClickListener) event -> {
            if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                getCompositionRoot().removeStyleName(STYLE_VISIBLE);
            } else {
                getCompositionRoot().addStyleName(STYLE_VISIBLE);
            }
        });

        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName(VALO_MENU_TOGGLE);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName(VALO_MENUITEMS);

        Stream.of(BoardMenu.values())
                .filter(menu -> profile.isEvailableView(menu))
                .filter(menu -> profile.isShowInvoice() ||
                        !StringUtils.equalsIgnoreCase(menu.getNavigationName(), InvoiceView.VIEW_NAME))
                .filter(menu -> profile.showDental() ||
                        !StringUtils.equalsIgnoreCase(menu.getNavigationName(), DentistryView.VIEW_NAME))
                .forEach(menuItem -> {
                    Component menuItemComponent = new ValoMenuItemButton(menuItem);

                    menuItemsLayout.addComponent(menuItemComponent);
                });

        return menuItemsLayout;

    }

    public final class ValoMenuItemButton extends Button {
        ValoMenuItemButton(BoardMenu menuItem) {
            setPrimaryStyleName(VALO_MENU_ITEM);
            setIcon(menuItem.getIcon());
            setCaption(StringUtils.capitalize(menuItem.getMenuName()));

            addClickListener((ClickListener) clickEvent ->
            {
                if (menuItemButton != null) {
                    menuItemButton.removeStyleName(STYLE_SELECTED);
                    menuItemButton = this;
                    this.addStyleName(STYLE_SELECTED);
                }

                if (menuItem.getViewClass() != null) {
                    navigationManager.navigateTo(menuItem.getViewClass());
                } else {
                    logOut();
                }
            });
        }
    }

    private void logOut() {
        loginService.logOut();

        Stream.of(BoardMenu.values()).forEach(menu ->
                navigationManager.removeView(menu.getNavigationName()));

        Page.getCurrent().reload();
        getLogger().trace("reload page");
    }
}
