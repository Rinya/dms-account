package ru.alfastrah.account.sber.view.component.greeting;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.constants.Greeting;
import ru.alfastrah.account.sber.helper.UITexts;

import javax.annotation.PostConstruct;

@SpringComponent
@VaadinSessionScope
public class GreetingFranchiseWindow extends GreetingWindow {
    @Autowired
    public GreetingFranchiseWindow(UITexts uiTexts) {
        super(uiTexts);
    }

    @PostConstruct
    public void init() {
        mountData(Greeting.FRANCHISE_PROGRAMM);
    }
}
