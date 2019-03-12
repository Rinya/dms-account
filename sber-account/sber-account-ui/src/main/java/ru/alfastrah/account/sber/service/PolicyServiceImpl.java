package ru.alfastrah.account.sber.service;

import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Notification;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.constant.PrintingForm;
import ru.alfastrah.account.sber.backend.exception.PayException;
import ru.alfastrah.account.sber.backend.exception.PolicyException;
import ru.alfastrah.account.sber.backend.model.PaymentData;
import ru.alfastrah.account.sber.backend.model.mail.AttachMailData;
import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;
import ru.alfastrah.account.sber.backend.model.policy.*;
import ru.alfastrah.account.sber.backend.model.user.InsuredResult;
import ru.alfastrah.account.sber.backend.model.user.PayingServiceItem;
import ru.alfastrah.account.sber.backend.platron.CommonException;
import ru.alfastrah.account.sber.backend.service.BackendPayService;
import ru.alfastrah.account.sber.backend.service.BackendPolicyService;
import ru.alfastrah.account.sber.exception.PaymentException;
import ru.alfastrah.account.sber.model.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@VaadinSessionScope
public class PolicyServiceImpl implements PolicyService {
    private static final int POLICY_PAY_TYPE = 1;
    public static final int DENTAL_PAY_TYPE = 3;
    private BackendPolicyService backendPolicyService;
    private BackendPayService backendPayService;

    @Autowired
    public PolicyServiceImpl(BackendPolicyService backendPolicyService, BackendPayService backendPayService) {
        this.backendPolicyService = backendPolicyService;
        this.backendPayService = backendPayService;
    }

    @Override
    public List<Insured> policyList(String login) {
        List<InsuredResult> policyList = backendPolicyService.getPolicyList(login);

        return policyList.stream()
                .map(this::convertToInsured)
                .collect(Collectors.toList());
    }

    @Override
    public List<Insured> dentalPolicyList(String login) {
        List<InsuredResult> policyList = backendPolicyService.getDentalPolicyList(login);

        return policyList.stream()
                .map(this::convertToInsured)
                .collect(Collectors.toList());
    }

    @Override
    public PolicyPdfStore getPolicyPdf(Long policyId, Long programmId) {
        PolicyInfoRequest request = new PolicyInfoRequest();
        request.setPolicyId(policyId);
        request.setProgrammId(programmId);

        PolicyPdfResult pdfs = null;
        try {
            pdfs = backendPolicyService.getPolicyAndBookletPdf(request);
        } catch (Exception e) {
            getLogger().trace("get pdfs for policy id = {}, programm id = {}, exception {}", policyId, programmId, e);
        }

        PolicyPdfStore policyPdfStore = new PolicyPdfStore();
        if (pdfs != null) {
            getLogger().trace("policy in base 64 {}", pdfs.getPolicy());
            policyPdfStore.setPolicy(Base64.decodeBase64(pdfs.getPolicy()));
            getLogger().trace("booklet in base 64 {}", pdfs.getBooklet());
            policyPdfStore.setBooklet(Base64.decodeBase64(pdfs.getBooklet()));
        }

        return policyPdfStore;
    }

    @Override
    public void changeInsuredProgramm(InsuredProfile profile) throws PolicyException {
        PolicyInfoRequest request = new PolicyInfoRequest();
        request.setLogin(profile.getPhone());
        request.setSubjectId(profile.getUserId());
        request.setPolicyId(profile.getPolicyId());
        request.setProgrammId(profile.getProgrammId());
        request.setHistoryId(profile.getHistoryId());

        backendPolicyService.changeInsuredProgramm(request);
    }

    @Override
    public List<PayingItem> familyList(InsuredProfile profile) {
        getLogger().trace("Enter to familyList");
        List<PayingServiceItem> familyList;
        try {
            PolicyInfoRequest request = new PolicyInfoRequest();
            request.setLogin(profile.getPhone());
            request.setSubjectId(profile.getUserId());
            request.setPolicyId(profile.getPolicyId());

            familyList = backendPolicyService.getFamilyList(request);
            familyList.forEach(item -> getLogger().trace("familyList item {}", item));
        } catch (Exception e) {
            getLogger().trace("call backendPolicyService.getFamilyList exception {}", e);
            familyList = new LinkedList<>();
            Notification.show("При получении списка родственников, возникла ошибка " +
                            ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
        }
        return familyList.stream().map(this::createFamilyItem).collect(Collectors.toList());
    }

    @Override
    public List<PayingItem> dentalServicesList(InsuredProfile profile) {
        getLogger().trace("Enter to dentalServicesList");
        List<PayingServiceItem> dentalList;
        try {
            PolicyInfoRequest request = new PolicyInfoRequest();
            request.setLogin(profile.getPhone());
            request.setSubjectId(profile.getUserId());
            request.setPolicyId(profile.getPolicyId());

            dentalList = backendPolicyService.getDentalAttachList(request);
            dentalList.forEach(item -> getLogger().trace("dentalList item {}", item));
        } catch (Exception e) {
            getLogger().trace("call backendPolicyService.dentalServicesList exception {}", e);
            dentalList = new LinkedList<>();
            Notification.show("При получении списка для оплаты стоматологии, возникла ошибка " +
                    ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
        }
        return dentalList.stream().map(this::createFamilyItem).collect(Collectors.toList());
    }

    @Override
    public String paymentFamilyPolicies(InsuredProfile profile, List<PayingItem> items, BigDecimal totalPremium) throws PaymentException {
        getLogger().trace("Enter to paymentFamilyPolicies");

        try {
            return pay(profile, items, totalPremium, POLICY_PAY_TYPE);
        } catch (Exception e) {
            getLogger().error("paymentFamilyPolicies: profile {} exception {}", profile, e);
            throw new PaymentException(ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public String paymentDentalPolicies(InsuredProfile profile, List<PayingItem> items, BigDecimal totalPremium) throws PaymentException {
        getLogger().trace("Enter to paymentDentalPolicies totalPremium {}", totalPremium);

        try {
            return pay(profile, items, totalPremium, DENTAL_PAY_TYPE);
        } catch (Exception e) {
            getLogger().error("paymentDentalPolicies: profile {} exception {}", profile, e);
            throw new PaymentException(ExceptionUtils.getMessage(e));
        }
    }

    private String pay(InsuredProfile profile, List<PayingItem> items, BigDecimal totalPremium, int invoicePayType)
            throws PolicyException, CommonException, PayException {
        InvoiceCreate invoice = fillInvoiceCreate(invoicePayType, profile, items, totalPremium);

        Long invoiceId = backendPayService.invoiceCreation(invoice);
        getLogger().trace("created invoice id {}", invoiceId);

        return backendPayService.pay(invoiceId, profile.getPhone(), profile.getEmail());
    }

    @Override
    public PayingItem getFamilyItemFromProfile(InsuredProfile profile) {
        return createFamilyItem(profile);
    }

    @Override
    public PaymentInfo getPaymentInfo(long invoiceId) {
        getLogger().trace("Enter to getPaymentInfo");
        PaymentData paymentData = backendPayService.getPaymentInfo(invoiceId);
        getLogger().trace("paymentData {}", paymentData);

        return convertPaymentDataToPaymentInfo(paymentData);
    }

    @Override
    public List<ChatMailData> getChatMailDataList(InsuredProfile profile) {
        PolicyInfoRequest request = new PolicyInfoRequest();
        request.setLogin(profile.getPhone());
        request.setSubjectId(profile.getUserId());
        request.setPolicyId(profile.getPolicyId());

        return backendPolicyService.getChatMailDataList(request);
    }

    @Override
    public AttachMailData getAttachRelativeMailData(InsuredProfile profile) {
        PolicyInfoRequest request = new PolicyInfoRequest();
        request.setLogin(profile.getPhone());
        request.setSubjectId(profile.getUserId());
        request.setPolicyId(profile.getPolicyId());

        List<AttachMailData> attachMailDataList = backendPolicyService.getAttachMailDataList(request);
        return CollectionUtils.isNotEmpty(attachMailDataList)? attachMailDataList.get(0) : new AttachMailData();
    }

    @Override
    public AttachMailData getVzrMailData(InsuredProfile profile) {
        PolicyInfoRequest request = new PolicyInfoRequest();
        request.setLogin(profile.getPhone());
        request.setSubjectId(profile.getUserId());
        request.setPolicyId(profile.getPolicyId());

        List<AttachMailData> attachMailDataList = backendPolicyService.getVzrMailDataList(request);
        return CollectionUtils.isNotEmpty(attachMailDataList)? attachMailDataList.get(0) : new AttachMailData();
    }

    private PaymentInfo convertPaymentDataToPaymentInfo(PaymentData paymentData) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPremium(paymentData.getPremium());
        paymentData.setInfo(paymentData.getInfo());
        return paymentInfo;
    }

    private InvoiceCreate fillInvoiceCreate(int payType, InsuredProfile insured, List<PayingItem> items, BigDecimal totalPremium) {
        InvoiceCreate<Relatives> invoice = new InvoiceCreate<>();
        Relatives relatives = new Relatives();
        invoice.setXmlData(relatives);

        relatives.setRelativeList(items.stream().map(item -> createRelative(item, payType)).collect(Collectors.toList()));

        invoice.setUserId(insured.getUserId());
        invoice.setPolicyId(insured.getPolicyId());
        invoice.setPremium(totalPremium);
        invoice.setPayType(payType);
        return invoice;
    }

    private Relative createRelative(PayingItem item, int payType) {
        Relative relative = new Relative();
        relative.setUserID(item.getSubjectId());
        relative.setPremium(item.getPremium());

        if (POLICY_PAY_TYPE == payType) {
            relative.setChangeProgramm(item.getChangeProgramm());
        } else if (DENTAL_PAY_TYPE == payType) {
            relative.setProgrammId(item.getProgrammId());
        }

        return relative;
    }

    private PayingItem createFamilyItem(InsuredProfile profile) {
        PayingItem item = new PayingItem();
        item.setSurname(profile.getSurname());
        item.setName(profile.getName());
        item.setPatronymic(profile.getPatronymic());
        item.setBirthDate(profile.getBirthDate());
        item.setSubjectId(profile.getUserId());
        item.setPolicyId(profile.getPolicyId());
        item.setPremium(BigDecimal.ZERO);
        item.setProgrammId(profile.getProgrammId());
        item.setUser(true);
        item.setCanChangeProgramm(false);
        item.setTemplateId(PrintingForm.CHANGE_INSURED_PROGRAMM.getFormId());

        return item;
    }

    private PayingItem createFamilyItem(PayingServiceItem payingServiceItem) {
        PayingItem item = new PayingItem();
        item.setSurname(payingServiceItem.getSurname());
        item.setName(payingServiceItem.getName());
        item.setPatronymic(payingServiceItem.getPatronymic());
        item.setBirthDate(payingServiceItem.getBirthDate());
        item.setSubjectId(payingServiceItem.getSubjectId());
        item.setPolicyId(payingServiceItem.getPolicyId());
        item.setPremium(payingServiceItem.getPremium());
        item.setProgrammId(payingServiceItem.getProgrammId());
        item.setUser('N' == payingServiceItem.getRelativeIndicator());
        item.setChangeProgramm(payingServiceItem.getChangeProgramm());
        item.setCanChangeProgramm(payingServiceItem.isCanChangeProgramm());
        item.setTemplateId(payingServiceItem.getTemplateId());

        return item;
    }

    private Insured convertToInsured(InsuredResult info) {
        Insured insured = new Insured();
        insured.setPolicyNumber(info.getPolicyNumber());
        insured.setSurname(info.getSurname());
        insured.setName(info.getName());
        insured.setPatronymic(info.getPatronymic());
        insured.setBirthDate(info.getBirthDate());
        insured.setInsurer(info.getInsurer());
        insured.setPhone(info.getPhone());
        insured.setEmail(info.getEmail());
        insured.setFilial(info.getFilial());
        insured.setTableNumber(info.getTableNumber());
        insured.setProgrammGroup(info.getProgrammGroup());
        insured.setBeginDate(info.getBeginDate());
        insured.setEndDate(info.getEndDate());
        insured.setCancelDate(info.getCancelDate());
        insured.setContractNumber(info.getContractNumber());
        insured.setRelativeIndicator(info.getRelativeIndicator());
        insured.setFranchise(info.getFranchise());
        insured.setUserId(info.getUserId());
        insured.setPolicyId(info.getPolicyId());
        insured.setHistoryId(info.getHistoryId());
        insured.setProgrammId(info.getProgrammId());
        insured.setBookletTemplateId(info.getBookletTemplateId());
        insured.setPolicyTemplateId(info.getPolicyTemplateId());

        return insured;
    }
}
