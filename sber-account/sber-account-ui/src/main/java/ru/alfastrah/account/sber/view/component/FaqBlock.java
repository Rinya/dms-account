package ru.alfastrah.account.sber.view.component;

import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import ru.alfastrah.account.sber.backend.model.FaqData;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.styles.view.FaqStyle;

import java.io.Serializable;

public class FaqBlock extends VerticalLayout {
    private Panel content;
    private Button title;
    private ClickTitleListener clickTitleListener;

    public FaqBlock(FaqData data) {
        buildLayout();

        mountData(data);
    }

    public void setClickTitleListener(ClickTitleListener clickTitleListener) {
        this.clickTitleListener = clickTitleListener;
    }

    public interface ClickTitleListener extends Serializable {
        void clickTitle();
    }

    public void hideContent() {
        content.setVisible(false);
    }

    private void buildLayout() {
        setId("faq-block");
        setMargin(false);
        setSizeFull();
        Responsive.makeResponsive(this);
        addStyleName(FaqStyle.FAQ_CONTENT);

        content = createContent();
        title = createTitle();

        addComponents(title, content);
    }

    private void mountData(FaqData data) {
        title.setCaption(data.getTitle());
        content.setContent(createMessageLabel(data));
    }

    private Label createMessageLabel(FaqData data) {
        Label message = new Label();
        message.setId("content-message");
        message.addStyleName(ContractStyle.WRAPED_LABEL);
        message.setValue(data.getMessage());
        message.setContentMode(ContentMode.HTML);

        return message;
    }

    private Button createTitle() {
        Button button = new Button();
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener((Button.ClickListener) clickEvent -> {
            boolean visible = content.isVisible();
            if (clickTitleListener != null) {
                clickTitleListener.clickTitle();
            }

            content.setVisible(!visible);
        });

        return button;
    }

    private Panel createContent() {
        Panel panel = new Panel();
        panel.setId("faq-block-content");
        panel.setWidth(100, Unit.PERCENTAGE);
        panel.setVisible(false);

        return panel;
    }
}
