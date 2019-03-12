package ru.alfastrah.account.sber.constants;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import ru.alfastrah.account.sber.view.board.*;
import ru.alfastrah.account.sber.view.profile.ProfileView;

public enum BoardMenu {
    CONTRACT("Мои полисы", "Мои полисы", ContractsView.class, VaadinIcons.DOCTOR, ContractsView.VIEW_NAME),
    //DOCTOR("Запись к врачу", "Запись к врачу", DefaultView.class, VaadinIcons.DOCTOR_BRIEFCASE, DefaultView.VIEW_NAME),
    CHAT("Формы обратной связи", "Формы обратной связи", ChatView.class, VaadinIcons.CHAT, ChatView.VIEW_NAME),
    INVOICE("Мои счета", "Мои счета", InvoiceView.class, VaadinIcons.INVOICE, InvoiceView.VIEW_NAME),
    DISTANT_RELATIVE("Заявление на прикрепление родственников", "Покупка полиса для родственника",
            DistantRelativeView.class, VaadinIcons.FAMILY, DistantRelativeView.VIEW_NAME),
    DENTISTRY("Заявление на подключение стоматологии", "Покупка стоматологии", DentistryView.class,
            VaadinIcons.TOOTH, DentistryView.VIEW_NAME),
    VZR("Бонусный ВЗР", "Бонусный ВЗР", VzrView.class, VaadinIcons.AIRPLANE, VzrView.VIEW_NAME),
    FAQ("Часто задаваемые вопросы", "Часто задаваемые вопросы", FaqView.class, VaadinIcons.QUESTION, FaqView.VIEW_NAME),
    PROFILE("Личные данные", "Личные данные", ProfileView.class, VaadinIcons.BRIEFCASE, ProfileView.VIEW_NAME),
    EXIT("Выход", "Выход", null, VaadinIcons.SIGN_OUT, null);

    private String name;
    private String menuName;
    private Class<? extends View> viewClass;
    private Resource icon;
    private String navigationName;

    BoardMenu(String name, String menuName, Class<? extends View> viewClass, Resource icon, String navigationName) {
        this.name = name;
        this.menuName = menuName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.navigationName = navigationName;
    }

    public String getName() {
        return name;
    }

    public String getMenuName() {
        return menuName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public String getNavigationName() {
        return navigationName;
    }
}
