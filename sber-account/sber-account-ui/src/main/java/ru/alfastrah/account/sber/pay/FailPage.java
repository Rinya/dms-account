package ru.alfastrah.account.sber.pay;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.helper.Version;
import ru.alfastrah.account.sber.pay.view.FailView;

@Title("Личный кабинет ДМС")
@Theme("SuccessPayment")
@SpringUI(path = "/FailPayment")
public class FailPage extends UI implements HasLogger {
    private static final String ERROR_CODE = "pg_failure_code";
    private static final String ERROR_MESSAGE = "pg_failure_description";

    @Override
    protected void init(VaadinRequest request) {
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);
        getPage().setTitle(Version.getVersionInfo());
        getNavigator().addView("", FailView.class);

        setErrorHandler(event -> {
            Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
            getLogger().error("Error during request", t);
        });

        String errorCode = request.getParameter(ERROR_CODE);
        String errorMessage = request.getParameter(ERROR_MESSAGE);

        setContent(new FailView(errorCode, errorMessage));
    }
}
