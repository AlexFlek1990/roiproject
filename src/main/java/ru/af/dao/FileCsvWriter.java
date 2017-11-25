package ru.af.dao;

import com.csvreader.CsvWriter;
import ru.af.PropertyHolder;
import ru.af.entity.OutLine;
import ru.af.Processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileCsvWriter {
    private static final String[] HEADERS = {"ID user", "URL", "avgTime"};
    private String path = PropertyHolder.getInstance().OUTPUT_FOLDER;

    /**
     * записывает в
     *
     * @param block - ключ - дата, значение - стороки
     */
    public void write(Map<Long, List<OutLine>> block, String fileName) {
        CsvWriter csvWriter = null;
        Processor p = new Processor();
        try {
            String outputFile = "avg_" + fileName;
            csvWriter = new CsvWriter(new FileWriter(path + File.separator + outputFile), ',');
            for (String HEADER : HEADERS) {
                csvWriter.write(HEADER);

            }
            csvWriter.endRecord();
            for (Long date : block.keySet()) {
                csvWriter.write(p.convertUnixToDate(date));
                csvWriter.endRecord();
                List<OutLine> lines = block.get(date);
                for (OutLine line : lines) {
                    csvWriter.write(line.getUserId());
                    csvWriter.write(line.getUrl());
                    csvWriter.write(line.getAvgDuration() + "");
                    csvWriter.endRecord();
                }
                csvWriter.endRecord();
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }
}
