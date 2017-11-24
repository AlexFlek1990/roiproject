package ru.af.entity;

import dao.FileCSVReader;

import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {


        FileCSVReader reader = new FileCSVReader();
        Processor p = new Processor();
        List<Session> los= p.separateSessions(reader.read());

        Map<Long,List<Session>> m = p.groupByDate(los);

        for (Long date : m.keySet()) {
            System.out.println(p.convertUnixToDate(date));
            System.out.println();
            m.get(date);
            for (Session session : m.get(date)) {
                System.out.println(session.toString());

            }
        }





    }
}
