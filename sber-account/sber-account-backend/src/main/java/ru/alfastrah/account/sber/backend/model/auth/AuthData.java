package ru.alfastrah.account.sber.backend.model.auth;

public class AuthData {
    private Long userId;
    private String login;
    private String email;
    private String encodedPassword;

    public AuthData(String login, String encodedPassword) {
        this.login = login;
        this.encodedPassword = encodedPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public AuthData withUserId(Long userId) {
        this.userId = userId;

        return this;
    }

    public AuthData withEmail(String email) {
        this.email = email;

        return this;
    }

    @Override
    public String toString() {
        return "AuthData{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", encodedPass='" + encodedPassword + '\'' +
                '}';
    }
}
