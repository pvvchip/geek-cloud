package ru.ppvchip.cloud.common;

public class FileRequest extends AbstractMessage {
    private String filename;

    public FileRequest(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
