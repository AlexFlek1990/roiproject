package ru.af;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
     * получить данные из application.properties
     *
     * @return prop
     */
    public static Properties loadProperties() {
/**
 * https://stackoverflow.com/questions/8775303/read-properties-file-outside-jar-file
 */
        Properties prop = new Properties();
        try {
            File jarPath = new File(PropertyHolder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath = jarPath.getParentFile().getAbsolutePath();
            System.out.println(" propertiesPath-" + propertiesPath);
            prop.load(new FileInputStream(propertiesPath + "/application.properties"));
        } catch (IOException e1) {
            e1.printStackTrace();
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

