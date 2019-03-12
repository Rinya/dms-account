package ru.alfastrah.account.sber.backend.constant;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChatTableTest {

    @Test
    public void getChatTableWithNullValue() {
        assertNull("Должен вернуть null", ChatTable.getChatTable(null));
    }

    @Test
    public void getChatTableWithValue1() {
        assertEquals("Должен соответствовать INCORRECT_PERSONAL_DATA", ChatTable.INCORRECT_PERSONAL_DATA, ChatTable.getChatTable(1));
    }
}