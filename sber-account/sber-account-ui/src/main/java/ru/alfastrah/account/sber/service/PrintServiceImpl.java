package ru.alfastrah.account.sber.service;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.constant.PrintingForm;
import ru.alfastrah.account.sber.backend.exception.PdfException;
import ru.alfastrah.account.sber.backend.service.BackendPrintService;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

@Service
@VaadinSessionScope
public class PrintServiceImpl implements PrintService {
    private BackendPrintService printService;

    @Autowired
    public PrintServiceImpl(BackendPrintService printService) {
        this.printService = printService;
    }

    @Override
    public StreamResource getPolicyPdf(Long policyId) throws PdfException {
        ByteArrayInputStream pdf = printService.getPdf(PrintingForm.POLICY, policyId);

        return new StreamResource((StreamResource.StreamSource) () -> pdf, "policy.pdf");
    }

    @Override
    public StreamResource getBookletPdf(Long policyId) throws PdfException {
        ByteArrayInputStream pdf = printService.getPdf(PrintingForm.BOOKLET, policyId);

        return new StreamResource((StreamResource.StreamSource) () -> pdf, "booklet.pdf");
    }

    @Override
    public StreamResource getChangeInsuredProgrammPdf(Long policyId) throws PdfException {
        ByteArrayInputStream pdf = printService.getPdf(PrintingForm.CHANGE_INSURED_PROGRAMM, policyId);

        return new StreamResource((StreamResource.StreamSource) () -> pdf, "changingInsuredProgramm.pdf");
    }

    @Override
    public StreamResource getChangeFamilyProgrammPdf(Long policyId) throws PdfException {
        ByteArrayInputStream pdf = printService.getPdf(PrintingForm.CHANGE_FAMILY_PROGRAMM, policyId);

        return new StreamResource((StreamResource.StreamSource) () -> pdf, "changingFamilyProgramm.pdf");
    }

    @Override
    public StreamResource getPdf(Long policyId, BigDecimal templateId) throws PdfException {
        ByteArrayInputStream pdf = printService.getPdf(templateId, policyId);

        return new StreamResource((StreamResource.StreamSource) () -> pdf, "print.pdf");
    }
}
