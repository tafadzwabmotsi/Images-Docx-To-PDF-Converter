//Author: Tafadzwa Brian Motsi

package sample.modules;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class ProcessingScene {

    private static final String PROCESSING_SCENE_FXML_PATH = "fxml_images/processing_scene.fxml";

    private final FXMLLoader processingSceneFxmlLoader;

    public ProcessingScene(){
        this.processingSceneFxmlLoader = new FXMLLoader();
        this.processingSceneFxmlLoader.setLocation(ProcessingScene.class.getResource(PROCESSING_SCENE_FXML_PATH));
    }

    //get the processing scene
    public Scene processingScene() throws Exception{
        return new Scene(this.processingSceneFxmlLoader.load(), 500, 200);
    }

    public void processingOKButtonHandler(ActionEvent actionEvent) {
    }
}
