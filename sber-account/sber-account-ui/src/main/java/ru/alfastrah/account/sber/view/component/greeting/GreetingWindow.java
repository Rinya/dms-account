package ru.alfastrah.account.sber.view.component.greeting;

import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.constants.Greeting;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.styles.CommonStyles;


abstract class GreetingWindow extends Window implements HasLogger {
    private Button aggree;
    private UITexts uiTexts;
    private Panel panel;

    GreetingWindow(UITexts uiTexts) {
        this.uiTexts = uiTexts;

        setId("greeting-window");
        setCaptionAsHtml(true);
        setResizable(false);
        setClosable(false);
        setModal(true);
        setWindowMode(WindowMode.NORMAL);
        addStyleName(CommonStyles.SBER_WINDOW);

        Responsive.makeResponsive(this);

        setHeight(50, Unit.PERCENTAGE);
        setWidth(50, Unit.PERCENTAGE);

        center();

        setContent(buildContent());
    }

    void mountData(Greeting greeting) {
        getLogger().trace("Enter to GreetingWindow mountData {}", greeting);
        setCaption(greeting.getTitle());
        panel.setContent(uiTexts.getTextComponent(greeting.getTextId()));

        aggree.setCaption(greeting.getAggreement());
    }

    private Component buildContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSizeFull();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        aggree = new Button("", (Button.ClickListener) clickEvent -> close());
        aggree.addStyleName(ValoTheme.BUTTON_PRIMARY);

        panel = new Panel();
        panel.setSizeFull();

        layout.addComponents(panel, aggree);
        layout.setExpandRatio(panel, 1f);

        return layout;
    }
}
