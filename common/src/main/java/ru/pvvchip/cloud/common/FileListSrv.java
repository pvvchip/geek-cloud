package ru.pvvchip.cloud.common;

import java.util.ArrayList;

public class FileListSrv extends AbstractMessage {
    private ArrayList<String> list;

    public FileListSrv(ArrayList<String> list, String lg, String pw) {
        super(lg, pw);
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }
}
