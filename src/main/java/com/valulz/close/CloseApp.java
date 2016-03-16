package com.valulz.close;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CloseApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Interface.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Close");
        stage.setScene(scene);
        stage.show();
    }
}
