package ru.alfastrah.account.sber.constants;

public enum Franchise {
    NO,
    ONLY_INSURED,
    WITH_FAMILY;

    public static Franchise getByInteger(Integer value) {
        if (value == null || value == 0) {
            return NO;
        } else if (value == 1) {
            return ONLY_INSURED;
        } else {
            return WITH_FAMILY;
        }
    }
}
