package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.exception.AuthException;
import ru.alfastrah.account.sber.backend.model.auth.AuthData;
import ru.alfastrah.account.sber.backend.model.auth.CheckResult;
import ru.alfastrah.account.sber.backend.model.auth.RegistrationUser;

import java.io.Serializable;

public interface AuthenticationService extends Serializable {
    /**
     * Процедура авторизации пользователя. Во входящем параметре заполняются, только login и пароль
     * @param data
     */
    void login(AuthData data) throws AuthException;

    /**
     * Проверка пользователя сбербанка
     * @param user
     */
    CheckResult checkUser(RegistrationUser user) throws AuthException;

    /**
     * Регистрация пользователя
     * @param data
     */
    void registerUser(AuthData data) throws AuthException;

    /**
     * Смена пароля
     * @param data
     */
    void changePassword(AuthData data) throws AuthException;
}
