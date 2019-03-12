package ru.alfastrah.account.sber.view.board;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class DefaultView extends MainView {
    public static final String VIEW_NAME = "default";

    @Autowired
    public DefaultView(BoardMenuView menu) {
        super(menu);
    }

    @Override
    protected String getViewDescription() {
        return null;
    }

    @Override
    protected Resource getViewIcon() {
        return VaadinIcons.REFRESH;
    }

    @PostConstruct
    protected void buildLayout() {
        getContent().setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        getContent().addComponent(new Label("<span style=\"font-size: 72px\">Извините, пока еще не успели сделать.</span>", ContentMode.HTML));
    }
}
