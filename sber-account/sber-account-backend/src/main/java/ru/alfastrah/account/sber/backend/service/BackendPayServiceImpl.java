package ru.alfastrah.account.sber.backend.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.converter.XmlConverter;
import ru.alfastrah.account.sber.backend.exception.PayException;
import ru.alfastrah.account.sber.backend.exception.PolicyException;
import ru.alfastrah.account.sber.backend.mapper.PaymentMapper;
import ru.alfastrah.account.sber.backend.model.PaymentData;
import ru.alfastrah.account.sber.backend.model.policy.InvoiceCreate;
import ru.alfastrah.account.sber.backend.model.policy.InvoiceCreateResult;
import ru.alfastrah.account.sber.backend.platron.ElectronPay;
import ru.alfastrah.account.sber.backend.platron.PlatronInitPaymentInput;
import ru.alfastrah.account.sber.backend.platron.PlatronInitPaymentOutput;
import ru.alfastrah.account.sber.backend.util.DbUtils;

@Service
public class BackendPayServiceImpl implements BackendPayService {
    private static final String CREATE_INVOICE_FAULT_MESSAGE = "invoiceCreation: Не удалось создать счет на оплату";

    private ElectronPay electronPay;
    private PaymentMapper paymentMapper;
    private XmlConverter converter;
    @Value("${pg.payment.system}")
    public String pgPaymentSystem;

    @Autowired
    public BackendPayServiceImpl(PaymentMapper paymentMapper, XmlConverter converter,
                                 @Lazy ElectronPay electronPay) {
        this.paymentMapper = paymentMapper;
        this.electronPay = electronPay;
        this.converter = converter;
    }

    @Override
    public String pay(long invoiceId, String phone, String email) throws PayException {
        getLogger().trace("Enter to pay invoiceId {} email {} paymentSystem {}", invoiceId, email, pgPaymentSystem);
        PlatronInitPaymentInput paymentInput = new PlatronInitPaymentInput();
        paymentInput.setCallerCode("agentru");
        paymentInput.setContractId(invoiceId);
        paymentInput.setProductCode("DMS");
        paymentInput.setPgPaymentSystem(pgPaymentSystem);
        paymentInput.setPgLifetime(86400);
        paymentInput.setUserContactEmail(StringUtils.isNotBlank(email)? email: "");
        paymentInput.setSessionId("");
        paymentInput.setUserPhone(phone);
        paymentInput.setPgUserIp("");
        paymentInput.setPgAlfaclickClientId("");

        try {
            PlatronInitPaymentOutput paymentOutput = electronPay.initPayment(paymentInput);

            return paymentOutput.getPgRedirectUrl();
        } catch (Exception e) {
            getLogger().error("electronPay.initPayment exception {}", e);
            throw new PayException("Извините, на данный момент оплата не доступна.");
        }
    }

    @Override
    public Long invoiceCreation(InvoiceCreate invoice) throws PolicyException {
        InvoiceCreateResult result = new InvoiceCreateResult(invoice);
        result.setXml(converter.doMarshaling(invoice.getXmlData()));

        getLogger().trace("call policyMapper.invoiceCreation with {}", result);
        paymentMapper.invoiceCreation(result);
        getLogger().trace("after call policyMapper.invoiceCreation with {}", result);

        if (DbUtils.isNotSuccess(result.getSuccess())) {
            throw new PolicyException(!org.springframework.util.StringUtils.isEmpty(result.getFaultMessage()) ?
                    result.getFaultMessage() : CREATE_INVOICE_FAULT_MESSAGE);
        }

        return result.getInvoiceId();
    }

    @Override
    public PaymentData getPaymentInfo(long invoiceId) {
        PaymentData info = new PaymentData();
        info.setInvoiceId(invoiceId);
        paymentMapper.getPaymentInfo(info);

        return info;
    }
}
