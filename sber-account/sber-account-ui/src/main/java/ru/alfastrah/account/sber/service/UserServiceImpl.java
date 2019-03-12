package ru.alfastrah.account.sber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.exception.AuthException;
import ru.alfastrah.account.sber.backend.exception.InsuredException;
import ru.alfastrah.account.sber.backend.model.auth.AuthData;
import ru.alfastrah.account.sber.backend.model.auth.CheckResult;
import ru.alfastrah.account.sber.backend.model.auth.RegistrationUser;
import ru.alfastrah.account.sber.backend.model.user.EvailablePagesAndLogo;
import ru.alfastrah.account.sber.backend.model.user.ProfileData;
import ru.alfastrah.account.sber.backend.service.AuthenticationService;
import ru.alfastrah.account.sber.backend.service.InsuredService;
import ru.alfastrah.account.sber.model.LoginUser;
import ru.alfastrah.account.sber.model.RegisterData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, Serializable {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private AuthenticationService authenticationService;
    private InsuredService insuredService;
    private Map<String, LoginUser> rememberedUsers = new HashMap<>();

    @Autowired
    public UserServiceImpl(AuthenticationService authenticationService, InsuredService insuredService) {
        this.authenticationService = authenticationService;
        this.insuredService = insuredService;
    }

    @Override
    public void isAuthenticUser(LoginUser user) throws AuthException {
        authenticationService.login(new AuthData(user.getLogin(), user.getPassword()));
    }

    @Override
    public String rememberUser(LoginUser user) {
        String randomId = UUID.randomUUID().toString();
        rememberedUsers.put(randomId, user);
        return randomId;
    }

    @Override
    public LoginUser getRememberedUser(String id) {
        return rememberedUsers.get(id);
    }

    @Override
    public void removeRememberedUser(String id) {
        rememberedUsers.remove(id);
    }

    @Override
    public void registerUser(RegisterData registerData) throws AuthException {
        AuthData data = new AuthData(registerData.getPhone(), registerData.getPassword());
        data.setUserId(registerData.getUserId());
        data.setEmail(registerData.getEmail());
        authenticationService.registerUser(data);
    }

    @Override
    public CheckResult checkUser(RegisterData data) throws AuthException {
        RegistrationUser user = new RegistrationUser();
        user.setPhoneNumber(data.getPhone());
        user.setPolicyNumber(data.getTableNumber());
        user.setInsuredSurName(data.getSurname());
        user.setInsuredFirstName(data.getName());
        user.setInsuredPatronymic(data.getPatronymic());
        user.setInsuredBirthDate(data.getBirthDate());

        return authenticationService.checkUser(user);
    }

    @Override
    public ProfileData getInsuredInfo(String login) throws InsuredException {
        return insuredService.getUserInfo(login);
    }

    @Override
    public void changePassword(String login, String password) throws AuthException {
        authenticationService.changePassword(new AuthData(login, password));
    }

    @Override
    public EvailablePagesAndLogo getEvailablePages(Long contractId) {
        return insuredService.getEvailablePages(contractId);
    }
}
