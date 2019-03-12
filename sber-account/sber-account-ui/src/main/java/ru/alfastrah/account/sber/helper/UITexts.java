package ru.alfastrah.account.sber.helper;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.exception.PdfException;
import ru.alfastrah.account.sber.model.TextLinkData;
import ru.alfastrah.account.sber.model.UIText;
import ru.alfastrah.account.sber.service.PrintService;
import ru.alfastrah.account.sber.service.UIService;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.view.ContractStyle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringComponent
@VaadinSessionScope
public class UITexts implements Serializable, HasLogger {
    private Map<Integer, UIText> textMap;
    private String login;
    private UIText defaultUIText;
    private UIService uiService;
    private PrintService printService;

    @Autowired
    public UITexts(UIService uiService, PrintService printService) {
        this.uiService = uiService;
        this.printService = printService;
        defaultUIText = new UIText();
    }

    public void setLogin(String login) {
        this.login = login;

        refresh();
    }

    public void refresh() {
        this.textMap = uiService.getTextMap(login);
    }

    public String getTitle(int id) {
        return getUiText(id).getTitle();
    }

    public String getText(int id) {
        return getUiText(id).getMessage();
    }

    public Component getTextComponent(int id) {
        UIText uiText = getUiText(id);
        if (uiText.getFormId() != null) {
            return getCustomlayout(uiText);
        } else {
            return createLabel(uiText);
        }
    }

    private Label createLabel(UIText uiText) {
        Label label = new Label(uiText.getMessage(), ContentMode.HTML);
        label.addStyleName(ContractStyle.WRAPED_LABEL);
        return label;
    }

    public UIText getUiText(int id) {
        UIText uiText = textMap != null ? textMap.get(id) : null;
        return uiText != null ? uiText : defaultUIText;
    }

    private CustomLayout getCustomlayout(UIText uiText) {
        String message = uiText.getMessage();
        List<TextLinkData> list = getLinkDataList(message);

        for (TextLinkData item : list) {
            message = message.replace(item.getSource(), "<div data-location=\"button" + item.getId() + "\" style=\"display: inherit;\"></div>");
        }

        try {
            CustomLayout layout = new CustomLayout(new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8)));
            for (TextLinkData item : list) {
                String location = "button" + item.getId();
                getLogger().info("button item {}", item);
                layout.addComponent(createLinkButton(item, uiText.getFormId()), location.trim());
            }
            return layout;
        } catch (IOException e) {
            getLogger().trace("create customlayout exception {}", e);
        }

        return null;
    }

    private Button createLinkButton(TextLinkData data, BigDecimal formId) {
        Button button = new Button(data.getText());
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addStyleName(CommonStyles.IN_TEXT_LINK);

        try {
            StreamResource pdf = printService.getPdf(data.getId(), formId);
            BrowserWindowOpener bookletOpener = new BrowserWindowOpener(pdf);
            bookletOpener.extend(button);
        } catch (PdfException e) {
            getLogger().error("getting pdf for link id {} text {} formId {} exception {}", data.getId(), data.getText(), formId, e);
            NotificationHelper.showErrorNotification("Ошибка получения документа", e);
        }

        return button;
    }

    private List<TextLinkData> getLinkDataList(String test) {
        Pattern pattern = Pattern.compile("<#(.|\\n)+?#>");
        Matcher matcher = pattern.matcher(test);

        List<TextLinkData> list = new LinkedList<>();
        while (matcher.find()) {
            String tag = matcher.group(0);
            String value = StringUtils.removePattern(tag, "((<#)|(#>))");
            String[] split = value.split("#");
            list.add(new TextLinkData(NumberUtils.createLong(split[0]), split[1], tag));
        }
        return list;
    }
}
