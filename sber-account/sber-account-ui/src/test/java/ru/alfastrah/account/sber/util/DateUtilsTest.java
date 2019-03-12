package ru.alfastrah.account.sber.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DateUtilsTest {


    @Test
    public void parseDate() {
        LocalDate currentDate = LocalDate.of(2018, 1, 1);

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01.01.2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1.01.2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01.1.2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1.1.2018"));

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01.01.18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1.01.18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01.1.18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1.1.18"));

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01-01-2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1-01-2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01-1-2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1-1-2018"));

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01-01-18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1-01-18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01-1-18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1-1-18"));

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01/01/2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1/01/2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01/1/2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1/1/2018"));

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01/01/18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1/01/18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01/1/18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1/1/18"));

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01 01 2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1 01 2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01 1 2018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1 1 2018"));

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01 01 18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1 01 18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01 1 18"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1 1 18"));

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01012018"));
/*assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1012018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("0112018"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("112018"));*/

        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("010118"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("10118"));
        assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("01118"));
//assertEquals("Должна быть дата " + currentDate, currentDate, DateUtils.parseDate("1118"));

    }
}