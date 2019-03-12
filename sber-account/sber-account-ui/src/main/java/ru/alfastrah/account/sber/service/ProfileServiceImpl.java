package ru.alfastrah.account.sber.service;

import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.exception.InsuredException;
import ru.alfastrah.account.sber.backend.model.user.ProfileData;
import ru.alfastrah.account.sber.constants.BoardMenu;
import ru.alfastrah.account.sber.model.InsuredProfile;

@Service
@VaadinSessionScope
public class ProfileServiceImpl implements ProfileService {
    private InsuredProfile insuredProfile;
    private UserService userService;

    @Autowired
    public ProfileServiceImpl(UserService userService, InsuredProfile insuredProfile) {
        this.userService = userService;
        this.insuredProfile = insuredProfile;
    }

    @Override
    public void loadProfileData(String login) {
        getLogger().trace("Enter to loadProfileData {}", login);
        try {
            ProfileData info = userService.getInsuredInfo(login);

            convertToInsured(info, insuredProfile);

            insuredProfile.setEvailablePageMapAndCompanyLogo(
                    userService.getEvailablePages(info.getContractId()));
        } catch (InsuredException e) {
            getLogger().error("getInsuredInfo exception {}", e);
        }
    }

    private void convertToInsured(ProfileData data, InsuredProfile profile) {
        profile.setPolicyNumber(data.getPolicyNumber());
        profile.setSurname(data.getSurname());
        profile.setName(data.getName());
        profile.setPatronymic(data.getPatronymic());
        profile.setBirthDate(data.getBirthDate());
        profile.setPhone(data.getPhone());
        profile.setEmail(data.getEmail());
        profile.setTableNumber(data.getTableNumber());
        profile.setBirthDate(data.getBirthDate());
        profile.setShowAcceptFranch(data.isShowAcceptFranch());
        profile.setShowDeposit(data.isShowDeposit());
        profile.setShowInvoice(data.isShowInvoice());
        profile.setShowRelative(data.isShowRelative());
        profile.setNeedProgramm(data.isNeedProgramm());
        profile.setShowDental(data.isShowDental());
        profile.setNotAvailableProgramm(data.isNotFountProgramm());
        profile.setUserId(data.getUserId());
        profile.setPolicyId(data.getPolicyId());
        profile.setProgrammId(data.getProgrammId());
        profile.setHistoryId(data.getHistoryId());
        profile.setEmailReciver(data.getEmailReciver());
        profile.setEmailVzrReciver(data.getEmailVzrReciver());
        profile.setServiceDeskPhone(data.getServiceDeskPhone());
        profile.setCanChangePassword(data.isCanChangePassword());

        profile.setEvailablePage(BoardMenu.CONTRACT, true);
        profile.setEvailablePage(BoardMenu.CHAT, data.isShowChat());
        profile.setEvailablePage(BoardMenu.DENTISTRY, data.isShowDental());
        profile.setEvailablePage(BoardMenu.FAQ, data.isShowFaq());
        profile.setEvailablePage(BoardMenu.VZR, data.isShowVZR());
        profile.setEvailablePage(BoardMenu.INVOICE, data.isShowInvoice());
        profile.setEvailablePage(BoardMenu.DISTANT_RELATIVE, data.isShowDistantRelative());
    }
}
