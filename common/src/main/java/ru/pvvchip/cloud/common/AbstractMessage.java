package ru.pvvchip.cloud.common;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {

    String lg, pw;

    public AbstractMessage(String lg, String pw) {
        this.lg = lg;
        this.pw = pw;
    }

    public String getLg() {
        return lg;
    }

    public String getPw() {
        return pw;
    }
}
