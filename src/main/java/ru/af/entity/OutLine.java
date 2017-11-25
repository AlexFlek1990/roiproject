package ru.af.entity;

import com.davekoelle.alphanum.AlphanumComparator;

/**
 * Выводимая строка
 */

public class OutLine implements Comparable<OutLine> {

    private static AlphanumComparator comparator = new AlphanumComparator();

    private String userId;
    private String url;
     // среднее время сеанса
    private int avgDuration;

    public OutLine(String userId, String url, int avgDuration) {
        this.userId = userId;
        this.url = url;
        this.avgDuration = avgDuration;
    }

    public String getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }

    public int getAvgDuration() {
        return avgDuration;
    }

    @Override
    public int compareTo(OutLine o) {
        return comparator.compare(this.getUserId(), o.getUserId());
    }

    @Override
    public String toString() {
        return userId + ',' + url + ',' + avgDuration;
    }
}
