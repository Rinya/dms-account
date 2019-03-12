package ru.alfastrah.account.sber.backend.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.avis.pdf.GetPdfPort;
import ru.alfastrah.account.sber.backend.avis.pdf.InPut;
import ru.alfastrah.account.sber.backend.avis.pdf.OutPut;
import ru.alfastrah.account.sber.backend.constant.PrintingForm;
import ru.alfastrah.account.sber.backend.exception.PdfException;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

@Service
public class BackendPrintServiceImpl implements BackendPrintService {
    private GetPdfPort pdfPort;

    @Autowired
    public BackendPrintServiceImpl(@Lazy GetPdfPort pdfPort) {
        this.pdfPort = pdfPort;
    }

    @Override
    public ByteArrayInputStream getPdf(PrintingForm form, Long policyId) throws PdfException {
        return getPdf(form.getFormId(), policyId);
    }

    @Override
    public ByteArrayInputStream getPdf(BigDecimal formid, Long policyId) throws PdfException {
        InPut inPut = new InPut();
        inPut.setSystemid(BigDecimal.ONE); //Потому что запрос делаем якобы из авис ДМС
        inPut.setTemplateid(formid);
        inPut.setFormatid(BigDecimal.valueOf(4)); //Это говорит, что хотим в виде pdf
        inPut.setObjectid(BigDecimal.valueOf(policyId));
        inPut.setActive(1);

        try {
            OutPut outPut = pdfPort.getPdf(inPut);
            return new ByteArrayInputStream(Base64.decodeBase64(outPut.getBlobpdf()));
        } catch (Exception e) {
            getLogger().error("getting pdf for policyId {} and formid {}", policyId, formid);
            throw new PdfException("Не удалось получить печатную форму " + formid + ExceptionUtils.getMessage(e));
        }
    }
}
