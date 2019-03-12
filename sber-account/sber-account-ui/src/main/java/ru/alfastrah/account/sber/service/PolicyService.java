package ru.alfastrah.account.sber.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.exception.PolicyException;
import ru.alfastrah.account.sber.backend.model.mail.AttachMailData;
import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;
import ru.alfastrah.account.sber.exception.PaymentException;
import ru.alfastrah.account.sber.model.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface PolicyService extends Serializable, HasLogger{
    List<Insured> policyList(String login);

    List<Insured> dentalPolicyList(String login);

    PolicyPdfStore getPolicyPdf(Long policyId, Long programmId);

    void changeInsuredProgramm(InsuredProfile profile) throws PolicyException;

    List<PayingItem> familyList(InsuredProfile profile);

    List<PayingItem> dentalServicesList(InsuredProfile profile);

    String paymentFamilyPolicies(InsuredProfile profile, List<PayingItem> items, BigDecimal totalPremium) throws PaymentException;

    String paymentDentalPolicies(InsuredProfile profile, List<PayingItem> items, BigDecimal totalPremium) throws PaymentException;

    PayingItem getFamilyItemFromProfile(InsuredProfile profile);

    PaymentInfo getPaymentInfo(long invoiceId);

    List<ChatMailData> getChatMailDataList(InsuredProfile profile);

    AttachMailData getAttachRelativeMailData(InsuredProfile profile);

    AttachMailData getVzrMailData(InsuredProfile profile);
}
