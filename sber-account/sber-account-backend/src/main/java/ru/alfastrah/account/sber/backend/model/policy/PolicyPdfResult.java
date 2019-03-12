package ru.alfastrah.account.sber.backend.model.policy;

public class PolicyPdfResult {
    private String policy;
    private String booklet;

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getBooklet() {
        return booklet;
    }

    public void setBooklet(String booklet) {
        this.booklet = booklet;
    }

    @Override
    public String toString() {
        return "PolicyPdfResult{" +
                "policy='" + policy + '\'' +
                ", booklet='" + booklet + '\'' +
                '}';
    }
}
