package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.exception.PayException;
import ru.alfastrah.account.sber.backend.exception.PolicyException;
import ru.alfastrah.account.sber.backend.model.PaymentData;
import ru.alfastrah.account.sber.backend.model.policy.InvoiceCreate;
import ru.alfastrah.account.sber.backend.platron.CommonException;

import java.io.Serializable;

public interface BackendPayService extends Serializable, HasLogger {

    String pay(long invoiceId, String phone, String email) throws CommonException, PayException;

    Long invoiceCreation(InvoiceCreate invoice) throws PolicyException;

    PaymentData getPaymentInfo(long invoiceId);
}
