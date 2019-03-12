package ru.alfastrah.account.sber.helper;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

public final class Version {

    private static Pattern compile;

    static {
        compile = Pattern.compile("(....)(..)(..)-(..)(..)");
    }

    public static String getVersionInfo() {

        try {
            Properties properties = new Properties();
            properties.load(Version.class.getResourceAsStream("/version.properties"));

            return "Личный кабинет, версия " + properties.getProperty("VERSION")+" от "+properties.getProperty("BUILD_DATE");
        } catch (IOException e) {
        }


        return "Личный кабинет";
    }

}
