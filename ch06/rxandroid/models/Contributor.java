package io.github.jesterz91.rxandroid.models;

public class Contributor {
    private String login;
    private String url;
    private int id;

    public String getLogin() {
        return login;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "login=" + login + ", url=" + url + ", id=" + id;
    }
}
