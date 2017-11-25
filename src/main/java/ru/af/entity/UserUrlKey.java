package ru.af.entity;


/**
 * Комплексный ключ
 */
public class UserUrlKey {
    private  String userId;
    private String url;

    public UserUrlKey(String userId, String url) {
        this.userId = userId;
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserUrlKey that = (UserUrlKey) o;

        return (userId != null ? userId.equals(that.userId) : that.userId == null) &&
                (url != null ? url.equals(that.url) : that.url == null);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
