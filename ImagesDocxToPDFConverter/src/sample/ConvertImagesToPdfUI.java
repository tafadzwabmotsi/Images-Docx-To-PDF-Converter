//Author: Tafadzwa Brian Motsi

package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.modules.Alerting;
import sample.modules.ConvertImagesToPdf;
import sample.modules.ProcessingProgressBar;
import sample.modules.RestartApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ConvertImagesToPdfUI extends Application {

    private static final String IMAGE_PICKER_FXML_PATH = "fxml_images/drag_images_scene.fxml";
    public static final String DROP_ICON_PATH = "fxml_images/drop3.jpg";

    public FlowPane imagesFlowPane;
    public Button generatePdfButton;
    public ImageView processingImageView;
    public ImageView dropImageFilesImageView;
    public StackPane dragImagesStackPane;

    private final FXMLLoader imagePickerFxmlLoader;
    private final FXMLLoader processingCompletedFxmlLoader;

    private List<File> imageList;

    private static Stage primaryStage;

    public ConvertImagesToPdfUI(){
        this.imagePickerFxmlLoader = new FXMLLoader();
        this.processingCompletedFxmlLoader = new FXMLLoader();

        this.imagePickerFxmlLoader.setLocation(ConvertImagesToPdfUI.class.getResource(IMAGE_PICKER_FXML_PATH));

        this.imagesFlowPane = new FlowPane();

        this.generatePdfButton = new Button();
        this.processingImageView = new ImageView();
        this.dropImageFilesImageView = new ImageView();

        this.imageList = new ArrayList<>();
        this.dragImagesStackPane = new StackPane();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Convert Images To Pdf");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setScene(this.convertImagesToPdfScene());
        primaryStage.show();

        ConvertImagesToPdfUI.primaryStage = primaryStage;

    }

    //set the imageList
    public void setImageList(List<File> imageList) {
        this.imageList = imageList;
    }

    public List<File> getImageList(){
        return this.imageList;
    }

    Scene convertImagesToPdfScene() throws Exception{
        return new Scene(this.imagePickerFxmlLoader.load(), 1300, 400);
    }

    private Scene processCompletedGeneratingScene() throws Exception{
        return new Scene(this.processingCompletedFxmlLoader.load(), 1300, 400);
    }

    private Stage getPrimaryStage(){
        return ConvertImagesToPdfUI.primaryStage;
    }

    public void handleDragExited(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasFiles()){
            this.dragImagesStackPane.getChildren().add(this.dropImageFilesImageView);
        }
    }

    public void handleDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasFiles()){
            this.dragImagesStackPane.getChildren().remove(this.dropImageFilesImageView);
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    //add each dragged image in the flow pane
    public void addImagesToPane(File file, FlowPane imagesFlowPane){
        boolean value
                = String.valueOf(file).contains(".png")
                || String.valueOf(file).contains(".jpg")
                || String.valueOf(file).contains(".jpeg");

        if(value){

            try {
                ImageView imageView = new ImageView();
                Image image = new Image(new FileInputStream(file));
                imageView.setImage(image);

                imageView.setFitWidth(300);
                imageView.setFitHeight(400);

                imagesFlowPane.getChildren().add(imageView);

                imagesFlowPane.setVgap(10);
                imagesFlowPane.setHgap(10);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDragDropped(DragEvent dragEvent){
        List<File> fileList = dragEvent.getDragboard().getFiles();
        this.dropImageFilesImageView.setImage(null);
        this.imageList.addAll(fileList);

        this.imageList.forEach(file -> this.addImagesToPane(file, this.imagesFlowPane));
    }

    public void generateButtonHandler() {
        if(!this.imageList.isEmpty()){

            List<File> fileList = this.imageList;

            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Save Files");

            File saveFile = directoryChooser.showDialog(ConvertImagesToPdfUI.primaryStage);

            if(saveFile != null){
                Task<Void> convertingDocumentsTask  = new Task<Void>() {
                    @Override
                    protected Void call() {
                        double percentage = 0.0;
                        int documentCounter = 0;

                        for (File file : fileList) {
                            ConvertImagesToPdf.convertImageToPdf(file.getAbsolutePath(), saveFile.getAbsolutePath().concat("\\").concat(file.getName().split("\\.")[0]).concat(".pdf"));

                            documentCounter++;
                            percentage += Double.parseDouble(String.format("%.2f", ((double) documentCounter / fileList.size())));

                            updateProgress(0, percentage);

                        }

                        return null;
                    }
                };

                ProcessingProgressBar.progressBarThread(convertingDocumentsTask, this.dragImagesStackPane);
            }
        }

        else{
            new Alerting(Alert.AlertType.INFORMATION,
                    "Information",
                    "No images to convert").getAlert();
        }
    }

    public void convertButtonHandler(ActionEvent actionEvent) throws Exception {
        this.start(ConvertImagesToPdfUI.primaryStage);
    }

    public void backButtonHandler() throws Exception{
        RestartApp.restartApp();
    }

    //add images to flow pane by manual selection
    public void putImagesToFlowPane(List<File> pngJpgImagesFiles, FlowPane imagesFlowPane) throws Exception {
        pngJpgImagesFiles.forEach(file -> this.addImagesToPane(file, imagesFlowPane));
    }

    public void fileOpenMenuItemOnActionHandler() throws Exception {
        new WelcomeUI().pngJpgMenuItemHandler();
    }

    public void fileCloseOnActionMenuItem() throws Exception {
        RestartApp.restartApp();
    }

    public void editClearMenuItemOnActionHandler() throws Exception {
        RestartApp.restartApp();
    }

    public void aboutAppMenuItemOnActionHandler() throws Exception {
        new WelcomeUI().aboutAppMenuItemOnActionHandler();
    }

    public void aboutAuthorMenuItemOnActionHandler() throws Exception {
        new WelcomeUI().aboutAuthorMenuItemOnActionHandler();
    }
}
