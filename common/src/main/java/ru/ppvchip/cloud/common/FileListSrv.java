package ru.ppvchip.cloud.common;

import java.util.ArrayList;

public class FileListSrv extends AbstractMessage {
    private ArrayList<String> list;

    public FileListSrv(ArrayList<String> list) {
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }
}
