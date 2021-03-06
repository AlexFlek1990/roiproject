package ru.af.entity;

/**
 * Сеанс
 */

public class Session {

    private String userId;
    //дата сеанса в формате timestamp
    private Long date;
    private String url;
    //длительность сеанса
    private int duration;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Session{" +
                "userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", duration=" + duration +
                '}';
    }
}
