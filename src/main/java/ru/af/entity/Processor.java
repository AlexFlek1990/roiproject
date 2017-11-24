package ru.af.entity;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Processor {
    private final static int SECONDS_PER_DAY = 86400; //кол-во секунд в сутках


    /**
     * конвертирует дату из timestamp в формат dd-MMМ-yyyy
     * (принимает/возвращает String!)
     *
     * @param unixTime timestamp
     * @return дата
     */
    public String convertUnixToDate(long unixTime) {

        Date date = new Date(1000 * unixTime);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UT0"));
        return sdf.format(date);
    }

    /**
     * конрветирует дату в timestamp
     * @param date дата
     * @return timestamp
     */
    public long convertDateToUnix(String date) {
        long unixTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("UT0"));
            unixTime = sdf.parse(date).getTime();
            unixTime = unixTime / 1000;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTime;
    }

    /**
     * обрезать до начала текущего дня (до получночи)
     * @param unixTime время timestamp [c]
     * @return начало текущего дня
     */
    private long trancateToMidnight(long unixTime){
        return unixTime-unixTime%SECONDS_PER_DAY;
    }
    /**
     * разделяет на отдельные сеансы по дням
     *
     * @param listOfSessions зачитанный лист сеансов
     * @return обработанный список всех сеансов
     */
    public List<Session> separateSessions(List<InputLine> listOfSessions) {
        List<Session> separatedSessions = new ArrayList<>();
        for (InputLine inputLine : listOfSessions) {
            long unixTime = inputLine.getTime();
            int duration = inputLine.getDuration();
            //начало след дня
            long currentMidnight = trancateToMidnight(unixTime);
            long nextMidnight = currentMidnight + SECONDS_PER_DAY;
            //остаток до конца дня
            int durationPastDay = (int) (nextMidnight - unixTime);

            do {
                if (duration < durationPastDay) {
                    Session session = new Session();
                    session.setDate(trancateToMidnight(unixTime));
                    session.setUserId(inputLine.getUserId());
                    session.setUrl(inputLine.getUrl());
                    session.setDuration(duration);
                    separatedSessions.add(session);
                    break;
                } else {
                    Session session = new Session();
                    session.setDate(trancateToMidnight(unixTime));
                    session.setUserId(inputLine.getUserId());
                    session.setUrl(inputLine.getUrl());
                    session.setDuration(durationPastDay);
                    separatedSessions.add(session);
                }
                duration = duration - durationPastDay;
                unixTime = nextMidnight;
                nextMidnight = nextMidnight + SECONDS_PER_DAY;
                durationPastDay = (int) (nextMidnight - unixTime);
            } while (true);
        }
        return separatedSessions;
    }

    /**
     * Определяет и сортирует даты в списке
     * @param listOfSessions список сеансов
     * @return сет дат в хронологическом порядке
     */

    public Set<Long> determDates(List<Session> listOfSessions) {
        Set<Long> dates = new TreeSet<>();

        for (Session session : listOfSessions) {

            dates.add(session.getDate());
        }
        return dates;
    }

    /**
     * считает среднее по посещениям и сортирует юзеров по имени
     * @param separatedSessions
     * @return
     */
    public SortedMap<Long, List<Session>> groupByDate(List<Session> separatedSessions) {
        SortedMap<Long,List<Session>> groupedByDate = new TreeMap<>();
        for (Session separatedSession : separatedSessions) {
            if(!groupedByDate.containsKey(separatedSession.getDate())){
                groupedByDate.put(separatedSession.getDate(),new ArrayList<Session>());
            }
            groupedByDate.get(separatedSession.getDate()).add(separatedSession);
        }
        return groupedByDate;
    }
    public Map<Long,List<Session>> groupedByUserAndURL(Map<Long,List<Session>> groupedByDayMap){
        SortedMap<UserURLKey,List<Integer>> groupedByUserAndURL = new TreeMap<>();

        for (Long date : groupedByDayMap.keySet()) {
            List<Session> sessionList = groupedByDayMap.get(date);

            for (Session session : sessionList) {

            }

        }

        return null;
    }

}
