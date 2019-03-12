package ru.alfastrah.account.sber.view.component;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceDetail;
import ru.alfastrah.account.sber.helper.ExcelHelper;
import ru.alfastrah.account.sber.helper.NotificationHelper;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.InvoiceStyle;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringComponent
@VaadinSessionScope
public class InvoiceDetailLayout extends Panel implements HasLogger {
    private Grid<InvoiceDetail> grid;
    private List<InvoiceDetail> detailList;
    private String invoice;

    InvoiceDetailLayout() {
        buildLayout();
        createGrid();

        setContent(createContent());
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    private VerticalLayout createContent() {
        VerticalLayout content = new VerticalLayout();
        content.setId("content");
        content.setMargin(false);
        content.setSizeFull();

        Image excel = createExcelExportButton();
        content.addComponents(grid, excel);
        content.setComponentAlignment(excel, Alignment.MIDDLE_RIGHT);

        return content;
    }

    private Image createExcelExportButton() {
        Image excel = new Image();
        excel.setId("excel-export");
        excel.addStyleName(CommonStyles.EXCEL_BUTTON);

        AdvancedFileDownloader downloader = new AdvancedFileDownloader();
        downloader.addAdvancedDownloaderListener((AdvancedFileDownloader.AdvancedDownloaderListener) downloaderEvent -> {
            String fileName = "invoice" + StringUtils.defaultString(invoice) + ".xls";
            downloader.setResource(exportToExcel(), fileName);
        });
        downloader.extend(excel);

        return excel;
    }

    private ByteArrayOutputStream exportToExcel() {
        try {
            return ExcelHelper.createExcelForInvoice(detailList);
        } catch (Exception e) {
            getLogger().error("Exporting to excel exception {}", e);
            NotificationHelper.showErrorNotification("Не удалось выгрузить данные в excel", e);
        }

        return null;
    }

    void setItems(List<InvoiceDetail> detailList) {
        this.detailList = detailList;
        grid.setItems(detailList);
        int size = CollectionUtils.size(detailList);
        grid.setHeightByRows(size == 0 ? 1 : size);
    }

    private void buildLayout() {
        setId("invoice-detail-layout");
        setSizeUndefined();
        setWidth(100, Unit.PERCENTAGE);
    }

    private void createGrid() {
        grid = new Grid<>();
        grid.setSizeUndefined();
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeightByRows(1);
        grid.addStyleName(InvoiceStyle.INVOICE_GRID);
        //grid.getDefaultHeaderRow().getCell("").setHtml();

        grid.addColumn(InvoiceDetail::getId).setCaption("Номер п/п").setSortable(false);
        grid.addColumn(InvoiceDetail::getHospital).setCaption("Лечебное учреждение (клиника)").setSortable(false);
        grid.addColumn(item -> item.getRequestDate()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).setCaption("Дата услуги (обращения)").setSortable(false);
        grid.addColumn(InvoiceDetail::getMedicalService).setCaption("Название услуги").setSortable(false);
        grid.addColumn(InvoiceDetail::getServiceCount).setCaption("Кол-во услуг").setSortable(false);
        grid.addColumn(InvoiceDetail::getPremium).setCaption("Стоимость, руб").setSortable(false);
    }
}
