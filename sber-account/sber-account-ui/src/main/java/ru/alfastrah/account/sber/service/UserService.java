package ru.alfastrah.account.sber.service;

import ru.alfastrah.account.sber.backend.exception.AuthException;
import ru.alfastrah.account.sber.backend.exception.InsuredException;
import ru.alfastrah.account.sber.backend.model.auth.CheckResult;
import ru.alfastrah.account.sber.backend.model.user.EvailablePagesAndLogo;
import ru.alfastrah.account.sber.backend.model.user.ProfileData;
import ru.alfastrah.account.sber.model.LoginUser;
import ru.alfastrah.account.sber.model.RegisterData;

import java.io.Serializable;


public interface UserService extends Serializable {
    void isAuthenticUser(LoginUser user) throws AuthException;

    String rememberUser(LoginUser user);

    LoginUser getRememberedUser(String id);

    void removeRememberedUser(String id);

    void registerUser(RegisterData registerData) throws AuthException;

    CheckResult checkUser(RegisterData data) throws AuthException;

    ProfileData getInsuredInfo(String login) throws InsuredException;

    void changePassword(String login, String password) throws AuthException;

    EvailablePagesAndLogo getEvailablePages(Long contractId);
}
