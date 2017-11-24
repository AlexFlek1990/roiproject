package ru.af;

import com.davekoelle.alphanum.AlphanumComparator;
import ru.af.entity.InputLine;
import ru.af.entity.OutLine;
import ru.af.entity.Session;
import ru.af.entity.UserURLKey;

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
     *
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
     *
     * @param unixTime время timestamp [c]
     * @return начало текущего дня
     */
    private long trancateToMidnight(long unixTime) {
        return unixTime - unixTime % SECONDS_PER_DAY;
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
     * группирует сеансы по дате
     *
     * @param separatedSessions список всех сеансов
     * @return ключ-дата в формате timestamp, значение-список сансов даты
     */
    public SortedMap<Long, List<Session>> groupByDate(List<Session> separatedSessions) {
        SortedMap<Long, List<Session>> groupedByDate = new TreeMap<>();

        for (Session separatedSession : separatedSessions) {
            if (!groupedByDate.containsKey(separatedSession.getDate())) {
                groupedByDate.put(separatedSession.getDate(), new ArrayList<Session>());
            }
            groupedByDate.get(separatedSession.getDate()).add(separatedSession);
        }
        return groupedByDate;
    }

    /**
     * формирует мапу для записи
     * @param groupedByDayMap сгруппированый по дате список
     * @return сключ-дата в формате timestamp, значение список подготовленых к записе строк
     */
    public Map<Long, List<OutLine>> formStatistics(Map<Long, List<Session>> groupedByDayMap) {
        SortedMap<Long,List<OutLine>> result = new TreeMap<>();

        for (Long date : groupedByDayMap.keySet()) {

            List<Session> sessionList = groupedByDayMap.get(date);
            result.put(date,groupedByUserAndURL(sessionList));
        }
        return result;
    }

    /**
     * группирует и  вычесляет среднее значение сенаса в рамках одного дня
     * @param sessionList несгруппированый список
     * @return подготовленный список объектов для записи
     */
    private List<OutLine> groupedByUserAndURL(List<Session> sessionList) {
        List<OutLine> result = new ArrayList<>();
        Map<UserURLKey, List<Integer>> groupedByUserAndURL = new HashMap<>();

        for (Session session : sessionList) {
            UserURLKey key = new UserURLKey(session.getUserId(), session.getUrl());

            if (!groupedByUserAndURL.containsKey(key)) {
                groupedByUserAndURL.put(key, new ArrayList<>());
            }

            groupedByUserAndURL.get(key).add(session.getDuration());
        }

        for (UserURLKey userURLKey : groupedByUserAndURL.keySet()) {
            List<Integer> listOfDuration = groupedByUserAndURL.get(userURLKey);
            Integer sum = 0;
            for (Integer duration : listOfDuration) {
                sum = sum + duration;
            }
            Integer avarage = sum / listOfDuration.size();
            OutLine out = new OutLine(userURLKey.getUserId(), userURLKey.getUrl(), avarage);
            result.add(out);
        }

        Collections.sort(result);
        return result;
    }

}