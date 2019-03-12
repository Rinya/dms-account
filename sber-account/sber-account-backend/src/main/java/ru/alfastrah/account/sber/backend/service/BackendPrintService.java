package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.constant.PrintingForm;
import ru.alfastrah.account.sber.backend.exception.PdfException;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;

public interface BackendPrintService extends Serializable, HasLogger {
    ByteArrayInputStream getPdf(PrintingForm form, Long policyId) throws PdfException;

    ByteArrayInputStream getPdf(BigDecimal formid, Long policyId) throws PdfException;
}
