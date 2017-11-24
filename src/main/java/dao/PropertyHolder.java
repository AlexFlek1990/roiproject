package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHolder {

    public  final String INPUT_FOLDER;
    public  final String OUTPUT_FOLDER;
    private static PropertyHolder instance;

    private PropertyHolder() {
        Properties properties = loadProperties();
        INPUT_FOLDER = properties.getProperty("inputfolder");
        OUTPUT_FOLDER = properties.getProperty("outputfolder");
    }

    /**
     * получить данные из application.properties
     * @return prop
     */
    public static Properties loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/application.properties");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    /**
     * Дает создание единственнго экзамляра класса PropertyHolder
     * @return PropertyHolder
     */
    public static PropertyHolder getInstance() {
        if (instance == null) {
            instance = new PropertyHolder();
        }
        return instance;
    }
}

