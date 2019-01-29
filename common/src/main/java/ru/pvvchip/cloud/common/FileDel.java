package ru.pvvchip.cloud.common;

public class FileDel extends AbstractMessage{
    private String name;

    public FileDel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
