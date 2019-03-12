package ru.alfastrah.account.sber.model;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

@SpringComponent
@VaadinSessionScope
public class LoginUser {
    private String login;
    private String password;
    private boolean rememberMe;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "login='" + login + '\'' +
                ", pass='" + password + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
