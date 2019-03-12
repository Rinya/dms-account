package ru.alfastrah.account.sber.service;

import com.vaadin.navigator.View;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.model.LoginUser;

import java.io.Serializable;

public interface LoginService extends Serializable, HasLogger {
    void authorize(LoginUser loginUser);

    boolean isAuthenticated();

    void backToAuthorization();

    void navigateTo(Class<? extends View> clazz);

    void registerUser(String login, Boolean rememberMe);

    void checkUser();

    void confirmData();

    void sendSmsCode();

    void changePassword(String login, String password);

    void logOut();
}
