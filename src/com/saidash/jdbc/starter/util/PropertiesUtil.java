package com.saidash.jdbc.starter.util;

import java.io.IOException;
import java.util.Properties;


/*
* Загрузка файла application.properties в переменную PROPERTIES
*/


public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }


    private PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);

    }


    private static void loadProperties() {
        try( var inputstream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")){
            PROPERTIES.load(inputstream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
