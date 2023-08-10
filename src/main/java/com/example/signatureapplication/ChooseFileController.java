package com.example.signatureapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static sun.font.CreatedFontTracker.MAX_FILE_SIZE;

public class ChooseFileController {
    private Stage firstStage; // Variable to store the reference to the first stage

    // Method to receive the reference to the first stage from the main application class
    public void setFirstStage(Stage stage) {
        this.firstStage = stage;
    }

    public String OpenPdf(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File selectedFile = fileChooser.showOpenDialog(null);
        System.out.println(selectedFile.getAbsolutePath());
        if (selectedFile != null & selectedFile.length() < MAX_FILE_SIZE) {
            try (PDDocument document = PDDocument.load(selectedFile)) {

                PDFRenderer pdfRenderer = new PDFRenderer(document);
                int pageCount = document.getNumberOfPages();
                System.out.println(pageCount);
                StringBuilder htmlContent = new StringBuilder();
                htmlContent.append("<html><head>");
                htmlContent.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                htmlContent.append("</head><body>");
                for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                    BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 96);
                    String imageSource = imageToDataUri(image);
                    htmlContent.append("<div style='border: 1px solid black;'>");
                    htmlContent.append("<img src='").append(imageSource).append("' />");
                    htmlContent.append("</div>");
                }
                htmlContent.append("</body></html>");
                String htmlContent1 = htmlContent.toString(); // Assuming StringBuilder variable name is "htmlContent"
                // Create the shared model and set the HTML content
                if(firstStage != null){
                    firstStage.close();
                }
                // Open the second FXML page with the WebView
                openDisplayHTMLPage(htmlContent1,selectedFile.getAbsolutePath());
            }

        }
        return selectedFile.getAbsolutePath();
    }

    private String imageToDataUri(BufferedImage image) {
        String imageFormat = "png";
        String dataUriPrefix = "data:image/" + imageFormat + ";base64,";
        String base64Image = imageToBase64(image, imageFormat);
        System.out.println(base64Image);
        return dataUriPrefix + base64Image;


    }

    private String imageToBase64(BufferedImage image, String format) {
        String base64Image = null;
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write(image, format, baos);
            byte[] imageBytes = baos.toByteArray();
            base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }

    private void openDisplayHTMLPage(String htmlContent,String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayPdf.fxml"));
            Parent root = loader.load();

            // Pass the shared model to the DisplayHTMLController
            DisplayPdf displayHTMLController = loader.getController();
            displayHTMLController.setHtmlContentModel(htmlContent);
            displayHTMLController.setfilepath(path);

            Stage secondstage = new Stage();

            secondstage.setResizable(false);
            secondstage.setTitle("UnSignedPDF");

            secondstage.setScene(new Scene(root));
displayHTMLController.setFirstStage(secondstage);
            secondstage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
