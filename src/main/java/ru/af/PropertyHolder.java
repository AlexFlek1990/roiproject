package ru.af;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Хранилище инфрмации о входной.выходной дирректории
 */
public class PropertyHolder {

    public final String INPUT_FOLDER;
    public final String OUTPUT_FOLDER;
    private static PropertyHolder instance;

    private PropertyHolder() {
        Properties properties = loadProperties();
        INPUT_FOLDER = properties.getProperty("inputfolder");
        OUTPUT_FOLDER = properties.getProperty("outputfolder");
    }

    /**
     * загружает данные из application.properties, который находится вне jar файла
     *
     * https://stackoverflow.com/questions/8775303/read-properties-file-outside-jar-file
     *
     * @return prop
     */
    private static Properties loadProperties() {

        Properties prop = new Properties();
        try {
            File jarPath = new File(
                    PropertyHolder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath = jarPath.getParentFile().getAbsolutePath();
//            System.out.println(" propertiesPath-" + propertiesPath);
            prop.load(new FileInputStream(propertiesPath + "/application.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    /**
     * Дает создание единственнго экзамляра класса PropertyHolder
     *
     * @return PropertyHolder
     */
    public static PropertyHolder getInstance() {
        if (instance == null) {
            instance = new PropertyHolder();
        }
        return instance;
    }
}

