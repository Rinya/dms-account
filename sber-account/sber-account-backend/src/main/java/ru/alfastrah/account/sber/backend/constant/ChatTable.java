package ru.alfastrah.account.sber.backend.constant;

import java.util.HashMap;
import java.util.Map;

public enum ChatTable {
    INCORRECT_PERSONAL_DATA,
    INCORRECT_RELATIVE_DATA;

    private static Map<Integer, ChatTable> store;

    static {
        store = new HashMap<>();
        store.put(1, INCORRECT_PERSONAL_DATA);
        store.put(2, INCORRECT_RELATIVE_DATA);
    }

    public static ChatTable getChatTable(Integer code) {
        return store.get(code);
    }
}
