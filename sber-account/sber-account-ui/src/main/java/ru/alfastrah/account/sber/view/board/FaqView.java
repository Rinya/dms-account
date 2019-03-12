package ru.alfastrah.account.sber.view.board;

import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.backend.model.FaqData;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.service.ApplicationService;
import ru.alfastrah.account.sber.view.component.FaqBlock;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.alfastrah.account.sber.constants.BoardMenu.FAQ;

@SpringView(name = FaqView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class FaqView extends MainView {
    public static final String VIEW_NAME = "faq";
    private ApplicationService applicationService;
    private InsuredProfile profile;

    @Autowired
    public FaqView(BoardMenuView menu, ApplicationService applicationService, InsuredProfile profile) {
        super(menu);
        this.applicationService = applicationService;
        this.profile = profile;
    }

    @PostConstruct
    private void buildLayout() {
        List<FaqData> faqList = applicationService.getFaqList(profile);

        faqList.forEach(item -> {
            FaqBlock faqBlock = new FaqBlock(item);
            faqBlock.setClickTitleListener(this::hideAllFaqBlocks);
            getContent().addComponent(faqBlock);
        });
    }

    private void hideAllFaqBlocks() {
        for (int i = 0; i < getContent().getComponentCount(); i++) {
            Component component = getContent().getComponent(i);
            if (component instanceof FaqBlock) {
                ((FaqBlock) component).hideContent();
            }
        }
    }

    @Override
    protected String getViewDescription() {
        return FAQ.getName();
    }

    @Override
    protected Resource getViewIcon() {
        return FAQ.getIcon();
    }
}
