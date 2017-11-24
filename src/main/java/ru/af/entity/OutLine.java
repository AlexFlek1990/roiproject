package ru.af.entity;

import com.davekoelle.alphanum.AlphanumComparator;

public class OutLine implements Comparable<OutLine> {

    private static AlphanumComparator comparator = new AlphanumComparator();

    private String userId;
    private String url;
    /**
     * Среднее время
     */
    private int avgDuration;

    public OutLine(String userId, String url, int avgDuration) {
        this.userId = userId;
        this.url = url;
        this.avgDuration = avgDuration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(int avgDuration) {
        this.avgDuration = avgDuration;
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
