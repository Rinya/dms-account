package ru.alfastrah.account.sber.backend.service;


import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.exception.AuthException;
import ru.alfastrah.account.sber.backend.mapper.AuthenticationMapper;
import ru.alfastrah.account.sber.backend.model.auth.*;

import java.security.MessageDigest;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, HasLogger {
    private static final String IS_SUCCESS = "Y";
    private static final String LOGIN_FAULT_MESSAGE = "login: Не удалось авторизоваться";
    private static final String CHECK_FAULT_MESSAGE = "checkUser: Не удалось проверить пользователя";
    private static final String REGISTER_FAULT_MESSAGE = "registerUser: Не удалось зарегистрировать пользователя";
    private static final String CHANGE_PASS_FAULT_MESSAGE = "changePassword: Не удалось поменять пароль";
    private AuthenticationMapper authenticationMapper;

    @Value("${dms.lk.salt}")
    private String salt;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationMapper authenticationMapper) {
        this.authenticationMapper = authenticationMapper;
    }

    @Override
    public void login(AuthData data) throws AuthException {
        getLogger().trace("Enter to login");
        AuthResult result = new AuthResult(data);
        result.setEncodedPassword(mdPassword(data.getEncodedPassword()));
        authenticationMapper.login(result);
        getLogger().trace("result: {}", result);

        if (!IS_SUCCESS.equals(result.getSuccess())) {
            throw new AuthException(!StringUtils.isEmpty(result.getFaultMessage()) ?
                    result.getFaultMessage() : LOGIN_FAULT_MESSAGE);
        }
    }

    @Override
    public CheckResult checkUser(RegistrationUser user) throws AuthException {
        getLogger().trace("Enter to checkUser");
        RegistrationDataWithCheckResult result = new RegistrationDataWithCheckResult(user);

        authenticationMapper.checkUser(result);
        getLogger().trace("result: {}", result);

        if (!IS_SUCCESS.equals(result.getSuccess())) {
            throw new AuthException(!StringUtils.isEmpty(result.getFaultMessage()) ?
                    result.getFaultMessage() : CHECK_FAULT_MESSAGE);
        }

        return result.getResult();
    }

    @Override
    public void registerUser(AuthData data) throws AuthException {
        getLogger().trace("Enter to registerUser");
        AuthResult result = new AuthResult(data);
        result.setEncodedPassword(mdPassword(data.getEncodedPassword()));
        authenticationMapper.registerUser(result);
        getLogger().trace("result: {}", result);

        if (!IS_SUCCESS.equals(result.getSuccess())) {
            throw new AuthException(!StringUtils.isEmpty(result.getFaultMessage()) ?
                    result.getFaultMessage() : REGISTER_FAULT_MESSAGE);
        }
    }

    @Override
    public void changePassword(AuthData data) throws AuthException {
        getLogger().trace("Enter to changePassword");
        AuthResult result = new AuthResult(data);
        result.setEncodedPassword(mdPassword(data.getEncodedPassword()));
        authenticationMapper.changePassword(result);
        getLogger().trace("result: {}", result);

        if (!IS_SUCCESS.equals(result.getSuccess())) {
            throw new AuthException(!StringUtils.isEmpty(result.getFaultMessage()) ?
                    result.getFaultMessage() : CHANGE_PASS_FAULT_MESSAGE);
        }
    }

    /**
     * генерация хэша пароля клиента
     *
     * @param password пароль
     * @return HEX представление хэша
     */
    public String mdPassword(final String password) {
        getLogger().info("Enter to mdPassword");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            if (password != null) {
                md.update(password.getBytes("UTF-8"));
            }

            if (salt != null) {
                md.update(salt.getBytes("UTF-8"));
            }

            byte[] digest = md.digest();

            String res = Hex.encodeHexString(digest).toUpperCase();
            getLogger().warn("Хэш пароля: {}", res);
            return res;
        } catch (Exception ex) {
            getLogger().error("Не возможно получить хэш пароля", ex);
        }
        return "";
    }
}
