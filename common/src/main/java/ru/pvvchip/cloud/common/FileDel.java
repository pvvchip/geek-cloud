package ru.pvvchip.cloud.common;

public class FileDel extends AbstractMessage{
    private String name;

    public FileDel(String name, String lg, String pw) {
        super(lg, pw);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
