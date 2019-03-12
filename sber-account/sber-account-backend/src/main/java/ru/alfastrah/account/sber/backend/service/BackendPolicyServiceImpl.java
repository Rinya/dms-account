package ru.alfastrah.account.sber.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.alfastrah.account.sber.backend.exception.PolicyException;
import ru.alfastrah.account.sber.backend.mapper.PolicyMapper;
import ru.alfastrah.account.sber.backend.model.auth.AuthResult;
import ru.alfastrah.account.sber.backend.model.mail.AttachMailData;
import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;
import ru.alfastrah.account.sber.backend.model.policy.FamilyAddPremium;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoRequest;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoResult;
import ru.alfastrah.account.sber.backend.model.policy.PolicyPdfResult;
import ru.alfastrah.account.sber.backend.model.user.InsuredResult;
import ru.alfastrah.account.sber.backend.model.user.PayingServiceItem;
import ru.alfastrah.account.sber.backend.util.DbUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BackendPolicyServiceImpl implements BackendPolicyService {
    private static final String TOTAL_AMOUNT_FAULT_MESSAGE = "totalAmountToFamily: Ошибка при формировании общей премии";
    private static final String CHANGE_INSURED_PROGRAMM_FAULT_MESSAGE = "changeInsuredProgramm: Ошибка во время смены программы";
    private static final String STANDART_POLICIES = "A";
    private static final String DENTAL_POLICIES = "C";
    private PolicyMapper policyMapper;

    @Autowired
    public BackendPolicyServiceImpl(PolicyMapper policyMapper) {
        this.policyMapper = policyMapper;
    }

    @Override
    public BigDecimal totalAmountToFamily(String familyXml) throws PolicyException {
        getLogger().trace("Enter to totalAmountToFamily");
        FamilyAddPremium totalAmount = policyMapper.getTotalAmountToFamily(familyXml);

        if (DbUtils.isNotSuccess(totalAmount.getSuccess())) {
            throw new PolicyException(!StringUtils.isEmpty(totalAmount.getFaultMessage()) ?
                    totalAmount.getFaultMessage() : TOTAL_AMOUNT_FAULT_MESSAGE);
        }

        return totalAmount.getPremium();
    }

    @Override
    public PolicyPdfResult getPolicyAndBookletPdf(PolicyInfoRequest request) {
        getLogger().trace("Enter to getPolicyAndBookletPdf {}", request);

        return policyMapper.getPolicyPdf(request);
    }

    @Override
    public List<InsuredResult> getPolicyList(String login) {
        AuthResult result = new AuthResult();
        result.setLogin(login);
        result.setRisk(STANDART_POLICIES);
        return policyMapper.policyList(result);
    }

    @Override
    public List<InsuredResult> getDentalPolicyList(String login) {
        AuthResult result = new AuthResult();
        result.setLogin(login);
        result.setRisk(DENTAL_POLICIES);
        return policyMapper.policyList(result);
    }

    @Override
    public List<PayingServiceItem> getFamilyList(PolicyInfoRequest request) {
        return policyMapper.getFamilyAttachmentList(request);
    }

    @Override
    public List<PayingServiceItem> getDentalAttachList(PolicyInfoRequest request) {
        return policyMapper.getDentalFamilyList(request);
    }

    @Override
    public void changeInsuredProgramm(PolicyInfoRequest request) throws PolicyException {
        PolicyInfoResult result = new PolicyInfoResult(request);
        policyMapper.changeInsuredProgramm(result);
        getLogger().trace("policyMapper.changeInsuredProgramm result: {}", result);

        if (DbUtils.isNotSuccess(result.getSuccess())) {
            throw new PolicyException(!StringUtils.isEmpty(result.getFaultMessage()) ?
                    result.getFaultMessage() : CHANGE_INSURED_PROGRAMM_FAULT_MESSAGE);
        }
    }

    @Override
    public List<ChatMailData> getChatMailDataList(PolicyInfoRequest request) {
        return policyMapper.getChatMailDataList(request);
    }

    @Override
    public List<AttachMailData> getAttachMailDataList(PolicyInfoRequest request) {
        return policyMapper.getAttachMailDataList(request);
    }

    @Override
    public List<AttachMailData> getVzrMailDataList(PolicyInfoRequest request) {
        return policyMapper.getVzrMailDataList(request);
    }
}
