package dao;

import com.csvreader.CsvWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Writer {
    String path=PropertyHolder.getInstance().OUTPUT_FOLDER;

    /**
     * @param block - ключ - дата, значение - стороки
     */
    public void write(Map<String, List<String>> block, String fileName) {
        CsvWriter csvWriter=null;
        try {
            String outputFile = "avg_" + fileName;
            csvWriter = new CsvWriter(new FileWriter(path+outputFile), ',');
            for (String date : block.keySet()) {
                csvWriter.write(date);
                csvWriter.endRecord();
                List<String> lines = block.get(date);
                for (String line : lines) {
                    csvWriter.write(line);
                }
                csvWriter.endRecord();
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csvWriter.close();
        }
    }
}
