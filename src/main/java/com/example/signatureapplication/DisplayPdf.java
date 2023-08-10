package com.example.signatureapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.util.Base64;

public class DisplayPdf {
    private static final String API_URL = "http://164.52.201.95:8181/SignatureWebServiceMicro/api/digital/signature/post/sign";
    private static final String JSON_PAYLOAD1 = "{\n" +
            "  \"documentType\": \"PADES\",\n" +
            "\"payerId\":\"8f708abe-da57-4f16-89a7-f302c910dd05\",\n" +
            "\"accountType\":\"organization\",\n" +
            "\"organizationUid\": \"8f708abe-da57-4f16-89a7-f302c910dd05\",\n" +
            "  \"subscriberUniqueId\": \"4bee4a56-0f02-4b2f-a5b5-544dcd39b53d\",\n" +
            "  \"placeHolderCoordinates\": {\n" +
            "    \"pageNumber\": 1,\n" +
            "    \"signatureXaxis\": \"300\",\n" +
            "    \"signatureYaxis\": \"300.0\"\n" +
            "  },\n" +
            "  \"esealPlaceHolderCoordinates\":null\n" +
            "}";
    private Stage firstStage;
    public void setFirstStage(Stage stage) {
        this.firstStage = stage;
    }
    @FXML
    private WebView webView;

    private String htmlContent;

    private String path;

    public void setHtmlContentModel(String htmlContent) {
        this.htmlContent = htmlContent;

        webView.getEngine().loadContent(htmlContent);
    }
    public String SignPdf(ActionEvent actionEvent) throws IOException {
        PlaceHolderCoordinates placeHolderCoordinates= new PlaceHolderCoordinates();
        placeHolderCoordinates.setPageNumber(1);
        placeHolderCoordinates.setSignatureXaxis("300");
        placeHolderCoordinates.setSignatureYaxis("300.0");
        RequestBodyDTO requestBodyDTO=new RequestBodyDTO();
        requestBodyDTO.setAccountType("self");
        requestBodyDTO.setDocumentType("PADES");
        requestBodyDTO.setSubscriberUniqueId("8ac88bf0-5f98-4b81-8387-d990193bcaf6");
//        requestBodyDTO.setOrganizationUid("a66c27ca-d0e3-496b-9635-71931db1a269");
        requestBodyDTO.setPlaceHolderCoordinates(placeHolderCoordinates);
        requestBodyDTO.setPayerId("8ac88bf0-5f98-4b81-8387-d990193bcaf6");
        String req=requestBodyDTO.toString();
        System.out.println(req);
        System.out.println(JSON_PAYLOAD1);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(API_URL);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();


            builder.addTextBody("model", requestBodyDTO.toString(), ContentType.APPLICATION_JSON);


            File file = new File(path);

            builder.addBinaryBody(
                    "file",
                    file,
                    ContentType.create("application/pdf"),
                    file.getName()
            );
            HttpEntity entity = builder.build();
            uploadFile.setEntity(entity);
            uploadFile.setHeader(HttpHeaders.AUTHORIZATION, "Bearer zXImpRKjJ9ul4wJZ9JvCm4UHSDSWkNvmUWRqKgBYLwCLW5bYbdUGg2Po7OHO5O5CNQei3gJtWChhvkXTqz6EcK5rZKJrku9hgq1J45yks33EQ2c51Fa9InzVhpLcmbtDGftRYYOz2kgIPQ0Y");
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            APIResponse apiResponse = objectMapper.readValue(response.getEntity().getContent(), APIResponse.class);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(response);
            System.out.println("Response Code: " + statusCode);
            System.out.println(apiResponse.getResult());
            String base64EncodedPDF = apiResponse.getResult();
            byte[] pdfData = Base64.getDecoder().decode(base64EncodedPDF);
            String tempDir = System.getProperty("java.io.tmpdir");
            String tempFileName = "temp_pdf_" + System.currentTimeMillis() + ".pdf";
            String tempFilePath = tempDir + tempFileName;
            try {
                FileOutputStream fos = new FileOutputStream(tempFilePath);
                fos.write(pdfData);
                fos.close();
                System.out.println("PDF saved to temporary file: " + tempFilePath);
                try (PDDocument document = PDDocument.load(new File(tempFilePath))) {

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
                    String htmlContent1 = htmlContent.toString();
                    if(firstStage != null){
                        firstStage.close();
                    }
                    openDisplayHTMLPage(htmlContent1,tempFilePath);
                    return tempDir;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error saving PDF to temporary file.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
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
            javax.imageio.ImageIO.write(image, format,baos);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displaySigned.fxml"));
            Parent root = loader.load();
            Stage secondstage = new Stage();
            secondstage.setResizable(false);
            secondstage.setScene(new Scene(root));
            secondstage.setTitle("SignedPDF");
            DisplaySigned displayHTMLController = loader.getController();
            displayHTMLController.setHtmlContentModel(htmlContent);
            displayHTMLController.setfilepath(path);
displayHTMLController.setFirstStage(secondstage);
            secondstage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setfilepath(String path) {
        this.path=path;
    }
}