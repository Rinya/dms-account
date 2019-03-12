package ru.alfastrah.account.sber.pay.view;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.styles.PayStyles;

import java.text.MessageFormat;

public class FailView extends VerticalLayout implements View, HasLogger {
    private static final String PAGE_ID = "FailPaymentPage";
    private static final String PAGE_CODE = "<center><span style=\"font-size: 100px;\">{0}</span></center>";

    private PageContainer container;
    private String code;
    private String message;

    public FailView() {
    }

    public FailView(String code, String message) {
        this.code = StringUtils.defaultString(code);
        this.message = StringUtils.defaultString(message);

        container = new PageContainer();
        buildLayout();
    }

    private void buildLayout() {
        setId(PAGE_ID);
        setStyleName(PayStyles.FAULT_LAYOUT_BACKGROUND);
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);

        addComponent(container);
        setComponentAlignment(container, Alignment.MIDDLE_CENTER);
    }

    private class PageContainer extends VerticalLayout {
        private static final String PAGE_CONTAINER = "PageContainer";
        private static final String IMG_LOGO_PNG = "img/logo.png";
        private Image logo;
        private Label codeLabel;
        private Label wrongLabel;

        PageContainer() {
            initElements();
            buildLayout();
        }

        private void initElements() {
            logo = new Image();
            logo.setSource(new ThemeResource(IMG_LOGO_PNG));

            codeLabel = new Label(MessageFormat.format(PAGE_CODE, code), ContentMode.HTML);
            codeLabel.addStyleName(PayStyles.THANKS_LABEL);

            wrongLabel = new Label("<center>" + message + "</center>", ContentMode.HTML);
            wrongLabel.addStyleName(PayStyles.THANKS_LABEL);
        }

        private void buildLayout() {
            setId(PAGE_CONTAINER);
            setStyleName(PayStyles.BLOCK_BACKGROUND);
            setSizeUndefined();

            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            addComponents(logo, codeLabel, wrongLabel);
        }
    }
}
