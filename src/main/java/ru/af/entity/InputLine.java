package ru.af.entity;

/**
 *Входная строка
 */

public class InputLine {
    private String userId;
    //длительность сеаанса
    private int duration;
    private String url;
    //время наала сенаса в формате timestamp
    private long time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
