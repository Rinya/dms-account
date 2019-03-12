package ru.alfastrah.account.sber.view.board;

import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;

import static ru.alfastrah.account.sber.styles.MainStyles.VIEW_IMAGE;

public abstract class MainView extends HorizontalLayout implements View, HasLogger {
    private static final String MAIN_LAYOUT_ID = "main_view";

    private BoardMenuView menu;
    private VerticalLayout content;

    @Autowired
    public MainView(BoardMenuView menu) {
        this.menu = menu;
    }

    protected AbstractOrderedLayout getContent() {
        return content;
    }

    @PostConstruct
    private void init() {
        setId(MAIN_LAYOUT_ID);
        Responsive.makeResponsive(this);
        setSpacing(false);

        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);

        addComponent(menu);
        addComponentsAndExpand(createMainContent());
    }

    private Panel createMainContent() {
        Panel mainContent = new Panel();
        mainContent.setId("main-content");
        mainContent.setSizeFull();
        mainContent.addStyleName(VIEW_IMAGE);

        content = createContent();
        mainContent.setContent(content);

        return mainContent;
    }

    private VerticalLayout createContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setMargin(true);
        Responsive.makeResponsive(layout);

        layout.addComponent(createViewHeader());

        return layout;
    }

    private HorizontalLayout createViewHeader() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);
        layout.addStyleName(CommonStyles.VIEW_HEADER);

        Label iconLabel = new Label();
        iconLabel.setId("view-header-icon");
        iconLabel.setIcon(getViewIcon());
        layout.addComponent(iconLabel);

        Label descriptionlabel = new Label();
        descriptionlabel.setId("view-header-description");
        descriptionlabel.setValue(StringUtils.defaultString(getViewDescription()));
        layout.addComponent(descriptionlabel);

        return layout;
    }

    protected abstract String getViewDescription();

    protected abstract Resource getViewIcon();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MainView)) return false;

        MainView that = (MainView) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(menu, that.menu)
                .append(getContent(), that.getContent())
                .isEquals();
    }
}
