package ru.pvvchip.cloud.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.pvvchip.cloud.common.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static ru.pvvchip.cloud.client.Client.lg;
import static ru.pvvchip.cloud.client.Client.pw;

public class MainController implements Initializable {

    @FXML
    Label lbServer;

    @FXML
    TextField tfFileName;

    @FXML
    TextField sdFileName;

    @FXML
    ListView<String> filesListClient;

    @FXML
    ListView<String> filesListServer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.start();

        lbServer.setText("Server id: " + lg + " pw: " + pw);

        Thread t = new Thread(() -> {
            try {
                while (true) {
                    AbstractMessage am = Network.readObject();
                    if (am instanceof FileSend) {
                        FileSend fs = (FileSend) am;
                        Files.write(Paths.get("storage_client/" + fs.getFilename()),
                                fs.getData(), StandardOpenOption.CREATE);
                        refreshLocalFilesList();
                    }
                    if (am instanceof FileListSrv) {
                        FileListSrv fl = (FileListSrv) am;
                        Platform.runLater(() -> {
                            try {
                                filesListServer.getItems().clear();
                                fl.getList().forEach(o -> filesListServer.getItems().add(o));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            } finally {
                Network.stop();
            }
        });
        t.setDaemon(true);
        t.start();
        filesListClient.setItems(FXCollections.observableArrayList());
        refreshLocalFilesList();
        refreshServerFilesList();
    }

    private void refreshServerFilesList() {
        // FIX
        FileListSrv fls = new FileListSrv(null, lg, pw);
        System.out.println(fls.getLg());
        System.out.println(fls.getPw());
        Network.sendMsg(fls);
    }

    public void pressOnDownloadBtn(ActionEvent actionEvent) {

        if (tfFileName.getLength() > 0) {
            Network.sendMsg(new FileSend(tfFileName.getText(), lg, pw));
            tfFileName.clear();
        }
    }

    public void pressOnDownsendBtn(ActionEvent actionEvent) {
        String path = "storage_client/" + sdFileName.getText();
        if (sdFileName.getLength() > 0 && Files.exists(Paths.get(path))) {
            FileSend fileSend = null;
            try {
                fileSend = new FileSend(Paths.get(path), lg, pw);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Network.sendMsg(fileSend);
            sdFileName.clear();
            refreshServerFilesList();
        }
    }

    public void refreshLocalFilesList() {
        Platform.runLater(() -> {
            try {
                filesListClient.getItems().clear();
                Files.list(Paths.get("storage_client")).map(p -> p.getFileName().toString()).forEach(o ->
                        filesListClient.getItems().add(o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void pressDelBtnServer(ActionEvent actionEvent) {
        boolean st = filesListServer
                .getItems()
                .stream()
                .anyMatch(s -> s.equals(tfFileName.getText()));
        if (tfFileName.getLength() > 0 && st) {
            FileDel fileDel = new FileDel(tfFileName.getText(), lg, pw);
            Network.sendMsg(fileDel);
            tfFileName.clear();
            refreshServerFilesList();
        }
    }

    public void pressDelBtnClient(ActionEvent actionEvent) {
        String path = "storage_client/" + sdFileName.getText();
        if (sdFileName.getLength() > 0 && Files.exists(Paths.get(path))) {
            try {
                Files.delete(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdFileName.clear();
            refreshLocalFilesList();
        }
    }

    public void clickListServer(MouseEvent mouseEvent) {
        tfFileName.setText(filesListServer.getFocusModel().getFocusedItem());
    }

    public void clickListClient(MouseEvent mouseEvent) {
        sdFileName.setText(filesListClient.getFocusModel().getFocusedItem());
    }
}
