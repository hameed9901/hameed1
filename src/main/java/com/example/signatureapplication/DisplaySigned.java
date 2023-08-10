package com.example.signatureapplication;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class DisplaySigned {
    private String htmlContent;
    @FXML
    private WebView webView;
    private String path;
    public void setHtmlContentModel(String htmlContent) {
        this.htmlContent = htmlContent;

        webView.getEngine().loadContent(htmlContent);
    }
    private Stage firstStage; // Variable to store the reference to the first stage

    // Method to receive the reference to the first stage from the main application class
    public void setFirstStage(Stage stage) {
        this.firstStage = stage;
    }
    public void setfilepath(String path) {
        this.path=path;
    }


    public void delete( String directoryPath) {
        // Call the method to delete the directory
        try {
            DisplaySigned.deleteDirectory(directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void deleteDirectory(String directoryPath) throws IOException {
        Path directory = Paths.get(directoryPath);
        if (Files.exists(directory)) {
            Files.walk(directory)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

}
