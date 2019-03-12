package ru.alfastrah.account.sber.helper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceDetail;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.InsuredRelative;
import ru.alfastrah.account.sber.model.VzrItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExcelHelper {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private ExcelHelper() { }

    public static void createExcelForBonusVzr(File file, List<VzrItem> vzrItemList) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet();

        createBonusVzrHeader(sheet);
        AtomicInteger rowCount = new AtomicInteger(1);
        vzrItemList.forEach(item -> createRow(sheet, rowCount.getAndIncrement(), item));

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            book.write(fileOutputStream);
        } finally {
            book.close();
        }
    }

    public static void createExcelForInsuredRelative(File file, List<InsuredRelative> relativeList, InsuredProfile profile) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet();

        createRelativeHeader(sheet);
        AtomicInteger rowCount = new AtomicInteger(1);
        relativeList.forEach(item -> createRow(sheet, rowCount.getAndIncrement(), item, profile));

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            book.write(fileOutputStream);
        } finally {
            book.close();
        }
    }

    public static ByteArrayOutputStream createExcelForInvoice(List<InvoiceDetail> invoiceList) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet();
        sheet.setAutobreaks(true);

        CellStyle headerCellStyle = createHeaderCellStyle(book);

        createInvoiceHeader(sheet, headerCellStyle);
        AtomicInteger rowCount = new AtomicInteger(1);
        invoiceList.forEach(item -> createRow(sheet, rowCount.getAndIncrement(), item, book));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            book.write(outputStream);
            return outputStream;
        } finally {
            book.close();
        }
    }

    private static CellStyle createDataCellStyle(Workbook book) {
        CellStyle cellStyle = book.createCellStyle();
        //cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return cellStyle;
    }

    private static CellStyle createHeaderCellStyle(Workbook book) {
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return cellStyle;
    }

    private static void createInvoiceHeader(Sheet sheet, CellStyle cellStyle) {
        Row header = sheet.createRow(0);

        createRowCell(header, 0, "Номер п/п", cellStyle);
        createRowCell(header, 1, "Лечебное учреждение (клиника)", cellStyle);
        createRowCell(header, 2, "Дата услуги \n (обращения)", cellStyle);
        createRowCell(header, 3, "Название услуги", cellStyle);
        createRowCell(header, 4, "Кол-во\nуслуг", cellStyle);
        createRowCell(header, 5, "Стоимость,\nруб", cellStyle);

    }

    private static void createRow(Sheet sheet, int id, InvoiceDetail item, Workbook workbook) {
        Row row = sheet.createRow(id);
        row.setHeightInPoints(34f);

        CellStyle centerCellStyle = createDataCellStyle(workbook);
        createRowCell(row, 0, String.valueOf(item.getId()), centerCellStyle);

        CellStyle leftCellStyle = createDataCellStyle(workbook);
        leftCellStyle.setAlignment(HorizontalAlignment.LEFT);
        createRowCell(row, 1, item.getHospital(), leftCellStyle);

        LocalDate requestDate = item.getRequestDate();
        if (requestDate != null) {
            createRowCell(row, 2, requestDate.format(formatter), centerCellStyle);
        }

        createRowCell(row, 3, item.getMedicalService(), leftCellStyle);
        createRowCell(row, 4, String.valueOf(item.getServiceCount()), centerCellStyle);

        CellStyle rightCellStyle = createDataCellStyle(workbook);
        rightCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        createRowCell(row, 5, item.getPremium() != null ? item.getPremium().toString() : "0", rightCellStyle);

        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
    }

    private static void createRow(Sheet sheet, int id, InsuredRelative relative, InsuredProfile profile) {
        Row row = sheet.createRow(id);

        createRowCell(row, 0, relative.getSurname());
        createRowCell(row, 1, relative.getName());
        createRowCell(row, 2, relative.getPatronymic());
        createRowCell(row, 3, relative.getGender().getExcel());
        createRowCell(row, 4, relative.getAddress().getArea());
        createRowCell(row, 5, relative.getAddress().getCity());
        createRowCell(row, 6, relative.getAddress().getStreet());
        createRowCell(row, 7, relative.getAddress().getHouse());
        createRowCell(row, 8, relative.getAddress().getAppartment());
        createRowCell(row, 9, relative.getAddress().getBuilding());
        createRowCell(row, 10, relative.getPassport().getSeria());
        createRowCell(row, 11, relative.getPassport().getNumber());

        LocalDate dateIssue = relative.getPassport().getDateIssue();
        if (dateIssue != null) {
            createRowCell(row, 12, dateIssue.format(formatter));
        }

        createRowCell(row, 13, relative.getPassport().getPlaceIssue());

        LocalDate birthDate = relative.getBirthDate();
        createRowCell(row, 14, birthDate.format(formatter));

        createRowCell(row, 15, profile.getTableNumber());
        createRowCell(row, 16, relative.getPhone());
        createRowCell(row, 17, "0");
        createRowCell(row, 18, relative.getAttachCity());
        createRowCell(row, 19, relative.getDegree());
    }

    private static void createRelativeHeader(Sheet sheet) {
        Row header = sheet.createRow(0);

        createRowCell(header, 0, "per_last_name");
        createRowCell(header, 1, "per_first_name");
        createRowCell(header, 2, "per_middle_name");
        createRowCell(header, 3, "per_sex");
        createRowCell(header, 4, "adr_region");
        createRowCell(header, 5, "adr_city");
        createRowCell(header, 6, "adr_street");
        createRowCell(header, 7, "adr_house");
        createRowCell(header, 8, "adr_appartment");
        createRowCell(header, 9, "adr_building");
        createRowCell(header, 10, "pdoc_ser");
        createRowCell(header, 11, "pdoc_no");
        createRowCell(header, 12, "pdoc_issue_date");
        createRowCell(header, 13, "pdoc_issue_place");
        createRowCell(header, 14, "per_date_of_birth");
        createRowCell(header, 15, "tab_number");
        createRowCell(header, 16, "per_mobile_phone");
        createRowCell(header, 17, "rel_emp");
        createRowCell(header, 18, "per_notes");
        createRowCell(header, 19, "degree_of_relationship");
    }

    private static void createBonusVzrHeader(Sheet sheet) {
        Row header = sheet.createRow(0);

        createRowCell(header, 0, "full_name");
        createRowCell(header, 1, "latin_full_name");
        createRowCell(header, 2, "birth_date");
        createRowCell(header, 3, "policy_duration");
        createRowCell(header, 4, "country");
    }

    private static void createRow(Sheet sheet, int id, VzrItem item) {
        Row row = sheet.createRow(id);

        createRowCell(row, 0, item.getFullname());
        createRowCell(row, 1, item.getLatinFullName());

        LocalDate birthDate = item.getBirthDate();
        createRowCell(row, 2, birthDate.format(formatter));

        createRowCell(row, 3, item.getPolicyDuration());
        createRowCell(row, 4, item.getCountry());
    }

    private static void createRowCell(Row header, int id, String value) {
        createRowCell(header, id, value, null);
    }

    private static void createRowCell(Row header, int id, String value, CellStyle cellStyle) {
        Cell cell = header.createCell(id);
        cell.setCellValue(value);

        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }
}
