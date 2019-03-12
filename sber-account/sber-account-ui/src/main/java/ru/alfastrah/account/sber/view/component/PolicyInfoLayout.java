package ru.alfastrah.account.sber.view.component;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.Insured;
import ru.alfastrah.account.sber.pay.HRHtml;
import ru.alfastrah.account.sber.service.PrintService;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.util.DateUtils;

import java.io.Serializable;
import java.text.MessageFormat;

@SpringComponent
@VaadinSessionScope
public class PolicyInfoLayout extends VerticalLayout implements Serializable, HasLogger {
    private static final String UNKNOWN = "<Не известно>";
    public static final int POLICY_TEXT = 9;
    private Insured insured;
    private PrintService printService;
    private UITexts uiTexts;

    public PolicyInfoLayout(Insured insured, PrintService printService, UITexts uiTexts) {
        this.insured = insured;
        this.printService = printService;
        this.uiTexts = uiTexts;

        buildLayout();
    }

    private void buildLayout() {
        setId("policy-info-layout");
        setWidth(100, Unit.PERCENTAGE);
        addStyleName(ContractStyle.POLICY_CARD);

        HRHtml hrHtml = new HRHtml();
        hrHtml.setWidth(100, Unit.PERCENTAGE);

        addComponents(policyInfo(), hrHtml);
        addComponent(policyDescription());
    }

    private Component policyDescription() {
        Component label = uiTexts.getTextComponent(POLICY_TEXT);
        label.setWidth(100, Unit.PERCENTAGE);
        return label;
    }

    private Component policyInfo() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("info-layout");
        layout.setMargin(false);
        layout.setWidth(100, Unit.PERCENTAGE);

        Component pdfLayout = pdfLayout();
        Component infoLayout = infoLayout();
        layout.addComponents(infoLayout, pdfLayout);
        layout.setComponentAlignment(pdfLayout, Alignment.MIDDLE_RIGHT);

        return layout;
    }

    private Component infoLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("data-layout");
        layout.setMargin(false);

        layout.addComponent(new Label(StringUtils.defaultString(insured.getFullName())));
        layout.addComponent(new Label("Полис № " + StringUtils.defaultString(insured.getPolicyNumber())));

        String beginDate = insured.getBeginDate() != null ? DateUtils.localDateToString(insured.getBeginDate()) : UNKNOWN;
        String endDate = insured.getEndDate() != null ? DateUtils.localDateToString(insured.getEndDate()) : UNKNOWN;
        layout.addComponent(new Label(MessageFormat.format("Действует с {0} по {1}", beginDate, endDate)));

        layout.addComponent(new Label("Таб. номер: " + StringUtils.defaultString(insured.getTableNumber())));

        return layout;
    }

    private Component pdfLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("pdf-layout");
        layout.setMargin(false);

        Image policyImage = new Image("Полис");
        policyImage.setId("policy-image");
        policyImage.addStyleName(ContractStyle.POLICY_BUTTON);
        try {
            StreamResource policyPdf = printService.getPdf(insured.getPolicyId(), insured.getPolicyTemplateId());
            BrowserWindowOpener policyOpener = new BrowserWindowOpener(policyPdf);
            policyOpener.extend(policyImage);
        } catch (Exception e) {
            getLogger().error("printService.getPolicyPdf on policyId {} exception", insured.getPolicyId(), e);
        }
        layout.addComponent(policyImage);

        Image bookletImage = new Image("Памятка");
        bookletImage.setId("booklet-image");
        bookletImage.addStyleName(ContractStyle.POLICY_BUTTON);
        try {
            StreamResource bookletPdf = printService.getPdf(insured.getPolicyId(), insured.getBookletTemplateId());
            BrowserWindowOpener bookletOpener = new BrowserWindowOpener(bookletPdf);
            bookletOpener.extend(bookletImage);
        } catch (Exception e) {
            getLogger().error("printService.getBookletPdf on policyId {} exception", insured.getPolicyId(), e);
        }
        layout.addComponent(bookletImage);

        return layout;
    }
}
