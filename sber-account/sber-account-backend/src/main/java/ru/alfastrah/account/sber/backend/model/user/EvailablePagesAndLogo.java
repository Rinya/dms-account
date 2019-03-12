package ru.alfastrah.account.sber.backend.model.user;

import com.vaadin.server.StreamResource;

import java.io.ByteArrayInputStream;

public class EvailablePagesAndLogo {
    private boolean showPolicies;
    private boolean showFaq;
    private boolean showChat;
    private boolean showDental;
    private boolean showVZR;
    private boolean canChangePassword;
    private StreamResource companyLogo;

    public boolean isShowPolicies() {
        return showPolicies;
    }

    public void setShowPolicies(Integer showPolicies) {
        this.showPolicies = showPolicies == 1;
    }

    public void setShowPolicies(boolean showPolicies) {
        this.showPolicies = showPolicies;
    }

    public boolean isShowFaq() {
        return showFaq;
    }

    public void setShowFaq(boolean showFaq) {
        this.showFaq = showFaq;
    }

    public void setShowFaq(Integer showFaq) {
        this.showFaq = showFaq == 1;
    }

    public boolean isShowChat() {
        return showChat;
    }

    public void setShowChat(boolean showChat) {
        this.showChat = showChat;
    }

    public void setShowChat(Integer showChat) {
        this.showChat = showChat == 1;
    }

    public boolean isShowDental() {
        return showDental;
    }

    public void setShowDental(boolean showDental) {
        this.showDental = showDental;
    }

    public void setShowDental(Integer showDental) {
        this.showDental = showDental == 1;
    }

    public boolean isShowVZR() {
        return showVZR;
    }

    public void setShowVZR(boolean showVZR) {
        this.showVZR = showVZR;
    }

    public void setShowVZR(Integer showVZR) {
        this.showVZR = showVZR == 1;
    }

    public boolean isCanChangePassword() {
        return canChangePassword;
    }

    public void setCanChangePassword(Integer canChangePassword) {
        this.canChangePassword = canChangePassword == 1;
    }

    public StreamResource getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = new StreamResource((StreamResource.StreamSource) () -> new ByteArrayInputStream(companyLogo),
                "companyLogo.jpg");
    }

    @Override
    public String toString() {
        return "EvailablePagesAndLogo{" +
                "showPolicies=" + showPolicies +
                ", showFaq=" + showFaq +
                ", showChat=" + showChat +
                ", showDental=" + showDental +
                ", showVZR=" + showVZR +
                ", companyLogo=" + companyLogo +
                '}';
    }
}
