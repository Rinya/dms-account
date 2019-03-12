package ru.alfastrah.account.sber.backend.model.auth;

public class AuthResult {
    private AuthData authData;
    private String risk;
    private String success;
    private String faultMessage;

    public AuthResult() {
        this.authData = new AuthData("", "");
    }

    public AuthResult(AuthData authData) {
        this.authData = authData;
    }

    public Long getUserId() {
        return authData.getUserId();
    }

    public void setUserId(Long userId) {
        this.authData.setUserId(userId);
    }

    public String getLogin() {
        return authData.getLogin();
    }

    public void setLogin(String login) {
        this.authData.setLogin(login);
    }

    public String getEmail() {
        return this.authData.getEmail();
    }

    public void setEmail(String email) {
        this.authData.setEmail(email);
    }

    public String getEncodedPassword() {
        return authData.getEncodedPassword();
    }

    public void setEncodedPassword(String encodedPassword) {
        this.authData.setEncodedPassword(encodedPassword);
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFaultMessage() {
        return faultMessage;
    }

    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }

    @Override
    public String toString() {
        return "AuthResult{" +
                "authData=" + authData +
                ", risk='" + risk + '\'' +
                ", success='" + success + '\'' +
                ", faultMessage='" + faultMessage + '\'' +
                '}';
    }
}
