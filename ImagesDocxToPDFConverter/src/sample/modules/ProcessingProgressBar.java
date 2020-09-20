//Author: Tafadzwa Brian Motsi

package sample.modules;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class ProcessingProgressBar {

    private static final String STYLE_PATH = "fxml_images/style.css";

    public static void progressBarThread(Task<Void> convertingDocumentsTask, StackPane stackPane){

        ProgressBar convertingDocumentsProgressBar = new ProgressBar();
        convertingDocumentsProgressBar.setPrefWidth(200);
        convertingDocumentsProgressBar.getStylesheets().add(ProcessingProgressBar.class.getResource(STYLE_PATH).toExternalForm());
        convertingDocumentsProgressBar.progressProperty().bind(convertingDocumentsTask.progressProperty());

        stackPane.getChildren().add(convertingDocumentsProgressBar);

        convertingDocumentsTask.setOnSucceeded(event -> {
            stackPane.getChildren().remove(convertingDocumentsProgressBar);
            new Alerting(Alert.AlertType.INFORMATION,
                    "Information",
                    "Conversion completed successfully").getAlert();

            try {
                RestartApp.restartApp();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        new Thread(convertingDocumentsTask).start();
    }
}
