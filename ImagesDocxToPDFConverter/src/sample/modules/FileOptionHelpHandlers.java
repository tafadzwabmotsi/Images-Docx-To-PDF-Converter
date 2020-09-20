//Author: Tafadzwa Brian Motsi

package sample.modules;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.WelcomeUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class FileOptionHelpHandlers {

    //get the image files from the file chooser
    public static List<File> getSelectedPngJpgImageFiles(Stage stage){
        FileChooser pngJpgFileChooser = new FileChooser();

        FileChooser.ExtensionFilter pngImageFilesExtensionFilter = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg");
        pngJpgFileChooser.getExtensionFilters().addAll(pngImageFilesExtensionFilter);

        return  pngJpgFileChooser.showOpenMultipleDialog(stage);
    }

    //get the word files from the file chooser
    public static List<File> getSelectedDocxFiles(Stage stage){
        FileChooser wordFileChooser = new FileChooser();

        FileChooser.ExtensionFilter docxFilesExtensionFilter =  new FileChooser.ExtensionFilter("Word document files", "*.docx", "*.doc");

        wordFileChooser.getExtensionFilters().addAll(docxFilesExtensionFilter);

        return  wordFileChooser.showOpenMultipleDialog(stage);
    }

    //add images to the flow pane
    public static void addImagesToPane(File file, FlowPane flowPane){
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

                flowPane.getChildren().add(imageView);

                flowPane.setVgap(10);
                flowPane.setHgap(10);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //convert images to pdf generate button
    public static void generateButtonHandler(List<File> fileList, Stage stage) throws Exception {
        if(!fileList.isEmpty()){

            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Save Files");

            File saveFile = directoryChooser.showDialog(stage);

            if(saveFile != null){
                for(File file: fileList){
                    ConvertImagesToPdf.convertImageToPdf(
                            file.getAbsolutePath(),
                            saveFile.getAbsolutePath().concat("\\").concat(file.getName().split("\\.")[0]).concat(".pdf"));
                }

                new Alerting(Alert.AlertType.INFORMATION,
                        "Information",
                        "Conversion completed successfully :)").getAlert();

                WelcomeUI.welcomeScreenStage().close();
            }
        }

        else{
            new Alerting(Alert.AlertType.INFORMATION,
                    "Information",
                    "No images were dragged on to the window\nDrag images to proceed.").getAlert();
        }
    }
}
