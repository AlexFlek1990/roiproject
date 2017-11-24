package ru.af.dao;

import com.csvreader.CsvReader;
import ru.af.entity.InputLine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileCsvReader {

    public ArrayList<InputLine> read(String path) {
        ArrayList<InputLine> listOfSessions = new ArrayList<>();
        CsvReader sessions=null;
        try {
            sessions = new CsvReader(path);
            sessions.readHeaders();

            while (sessions.readRecord()) {
                InputLine line = new InputLine();
                line.setTime(Long.valueOf(sessions.get("time")));
                line.setUserId(sessions.get("ID user"));
                line.setUrl(sessions.get("URL"));
                line.setDuration(Integer.valueOf(sessions.get("number of seconds")));
                listOfSessions.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sessions.close();
        }
        return listOfSessions;
    }
}

