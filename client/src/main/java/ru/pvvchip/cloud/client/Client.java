package ru.pvvchip.cloud.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.pvvchip.cloud.common.AbstractMessage;

import java.util.Random;

public class Client extends Application {
    public static String lg, pw;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Box Client");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        int rnd = new Random().nextInt(3) + 1;
        lg = String.valueOf(rnd);
        pw = "1" + lg;

        launch(args);
    }
}
