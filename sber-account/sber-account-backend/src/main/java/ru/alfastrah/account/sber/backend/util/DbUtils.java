package ru.alfastrah.account.sber.backend.util;

import org.apache.commons.lang3.StringUtils;

public class DbUtils {
    private static final String IS_SUCCESS = "Y";

    public static boolean isSuccess(String value) {
        return StringUtils.equalsIgnoreCase(IS_SUCCESS, value);
    }

    public static boolean isNotSuccess(String value) {
        return !isSuccess(value);
    }
}
