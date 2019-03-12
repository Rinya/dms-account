package ru.alfastrah.account.sber.service;

import com.vaadin.server.StreamResource;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.avis.pdf.OutException_Exception;
import ru.alfastrah.account.sber.backend.exception.PdfException;

import java.io.Serializable;
import java.math.BigDecimal;

public interface PrintService extends Serializable, HasLogger {
    StreamResource getPolicyPdf(Long policyId) throws PdfException, OutException_Exception;

    StreamResource getBookletPdf(Long policyId) throws PdfException;

    StreamResource getChangeInsuredProgrammPdf(Long policyId) throws PdfException;

    StreamResource getChangeFamilyProgrammPdf(Long policyId) throws PdfException;

    StreamResource getPdf(Long policyId, BigDecimal templateId) throws PdfException;
}
