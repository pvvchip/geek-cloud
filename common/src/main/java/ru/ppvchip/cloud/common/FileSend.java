package ru.ppvchip.cloud.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSend extends AbstractMessage {
    private String filename;
    private byte[] data;

    public FileSend(Path path) throws IOException {
        filename = path.getFileName().toString();
        data = Files.readAllBytes(path);
    }


    public String getFilename() {
        return filename;
    }

    public byte[] getData() {
        return data;
    }
}