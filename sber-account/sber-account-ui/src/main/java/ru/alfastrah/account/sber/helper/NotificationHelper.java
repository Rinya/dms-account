package ru.alfastrah.account.sber.helper;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class NotificationHelper {
    public static void showSuccessNotification(String message) {
        Notification notification = new Notification(null, Notification.Type.TRAY_NOTIFICATION);
        notification.setDescription(message);
        notification.setPosition(Position.MIDDLE_CENTER);
        notification.setHtmlContentAllowed(true);
        notification.show(Page.getCurrent());
    }

    public static void showErrorNotification(Exception e) {
        showErrorNotification(null, e);
    }

    public static void showErrorNotification(String caption, Exception e) {
        Notification notification = new Notification(StringUtils.defaultString(caption), Notification.Type.ERROR_MESSAGE);
        notification.setDescription(ExceptionUtils.getMessage(e));
        notification.setHtmlContentAllowed(true);
        notification.show(Page.getCurrent());
    }
}
