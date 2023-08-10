package com.example.signatureapplication;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ChooseFile.fxml"));
        Parent root= fxmlLoader.getRoot();
        Scene scene = new Scene(fxmlLoader.load());
ChooseFileController chooseFileController=fxmlLoader.getController();

        root.prefHeight(scene.heightProperty().getValue());
        root.prefWidth(scene.widthProperty().getValue());
        stage.setResizable(true);
        stage.setTitle("HOME");
        stage.setScene(scene);
        chooseFileController.setFirstStage(stage);
        stage.show();

    }

    public static void main(String[] args) {



        launch();
    }


}