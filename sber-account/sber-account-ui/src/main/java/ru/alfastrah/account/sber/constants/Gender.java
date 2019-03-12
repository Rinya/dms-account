package ru.alfastrah.account.sber.constants;

public enum Gender {
    MALE("Мужской", "m"),
    FEMAILE("Женский", "f");

    private String name;
    private String excel;

    Gender(String name, String excel) {
        this.name = name;
        this.excel = excel;
    }

    public String getName() {
        return name;
    }

    public String getExcel() {
        return excel;
    }
}
