package ru.alfastrah.account.sber.pay;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.exception.PaymentException;
import ru.alfastrah.account.sber.helper.Version;
import ru.alfastrah.account.sber.pay.view.SuccessView;

import java.util.Arrays;
import java.util.List;

@Theme("SuccessPayment")
@Title("Личный кабинет ДМС")
@SpringUI(path = "/SuccessPayment")
public class SuccessPage extends UI implements HasLogger {
    private static final String ORDER_ID = "pg_order_id";
    private static final String WRONG_INVOICE_ID_MESSAGE = "Передан некорректный идентификатор счета";

    private SuccessView successView;

    @Autowired
    public SuccessPage(SuccessView successView) {
        this.successView = successView;
    }

    @Override
    protected void init(VaadinRequest request) {
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);
        getPage().setTitle(Version.getVersionInfo());
        getNavigator().addView("", SuccessView.class);

        setErrorHandler(event -> {
            Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
            getLogger().error("Error during request", t);
        });

        successView.initCaptions();
        try {
            String orderId = request.getParameter(ORDER_ID);
            if (StringUtils.isNotBlank(orderId)) {
                successView.setData(getInvoiceId(orderId), orderId);
            }
        } catch (PaymentException e) {
            Notification.show(ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
        } catch (Exception e) {
            getLogger().error("setData to successView exception {}", e);
        }

        setContent(successView);
    }

    private Long getInvoiceId(String invoiceId) throws PaymentException {
        List<String> list = Arrays.asList(invoiceId.split("-"));
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                String id = list.get(list.size() - 1);
                getLogger().trace("id {}", id);
                return Long.valueOf(id);
            } catch (NumberFormatException e) {
                getLogger().error("parse invoiceId {} exception {}", invoiceId, e);
                throw new PaymentException(WRONG_INVOICE_ID_MESSAGE);
            }
        } else {
            throw new PaymentException(WRONG_INVOICE_ID_MESSAGE);
        }
    }
}
