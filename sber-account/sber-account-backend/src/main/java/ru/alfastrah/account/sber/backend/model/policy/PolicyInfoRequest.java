package ru.alfastrah.account.sber.backend.model.policy;

public class PolicyInfoRequest {
    private String login;
    private Long subjectId;
    private Long policyId;
    private Long programmId;
    private Long historyId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getProgrammId() {
        return programmId;
    }

    public void setProgrammId(Long programmId) {
        this.programmId = programmId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    @Override
    public String toString() {
        return "PolicyInfoRequest{" +
                "login='" + login + '\'' +
                ", subjectId=" + subjectId +
                ", policyId=" + policyId +
                ", programmId=" + programmId +
                ", historyId=" + historyId +
                '}';
    }
}
