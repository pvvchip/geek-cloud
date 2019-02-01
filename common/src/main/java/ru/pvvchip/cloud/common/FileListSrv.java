package ru.pvvchip.cloud.common;

import java.util.ArrayList;

public class FileListSrv extends AbstractMessage {
    private ArrayList<String> list;

    public FileListSrv(ArrayList<String> list, String lg, String pw) {
        this.list = list;
        this.lg = lg;
        this.pw = pw;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getLg() {
        return lg;
    }

    public String getPw() {
        return pw;
    }
}
