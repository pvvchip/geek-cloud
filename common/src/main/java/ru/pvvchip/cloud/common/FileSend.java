package ru.pvvchip.cloud.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSend extends AbstractMessage {
    private String filename;
    private byte[] data;

    public FileSend(Path path, String lg, String pw) throws IOException {
        super(lg, pw);
        filename = path.getFileName().toString();
        data = Files.readAllBytes(path);
    }

    public FileSend(String filename, String lg, String pw) {
        super(lg, pw);
        this.filename = filename;
        data = null;
    }


    public String getFilename() {
        return filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(Path path) {
        try {
            this.data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}