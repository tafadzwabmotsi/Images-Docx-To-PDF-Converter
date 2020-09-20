//Author: Tafadzwa Brian Motsi

package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageConversionChoiceUI extends Application {

    private static final String CHOICE_WINDOW_PATH = "fxml_images/conversion_options_scene.fxml";
    public VBox closeVBox;

    private int choice;
    private VBox choiceWindowVBox;

    private double xCoordinate;
    private double yCoordinate;

    @Override
    public void init() throws Exception {
        super.init();

        this.closeVBox = new VBox();

        this.choiceWindowVBox = FXMLLoader.load(this.getClass().getResource(CHOICE_WINDOW_PATH));

        this.xCoordinate = 0.0;
        this.yCoordinate = 0.0;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(this.choiceWindowVBox));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void cancelButtonOnMouseClickedEventHandler(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void vBoxOnMouseDraggedEventHandler(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - this.xCoordinate);
        stage.setY(mouseEvent.getScreenY() - this.yCoordinate);
    }

    public void vBoxOnMousePressedEventHandler(MouseEvent mouseEvent) {
        this.xCoordinate = mouseEvent.getSceneX();
        this.yCoordinate = mouseEvent.getSceneY();
    }



    public void cancelButtonOnMouseEnteredEventHandler(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        Scene scene = node.getScene();

        VBox vBox = (VBox) scene.lookup("#closeVBox");

        vBox.setStyle("-fx-background-color: #932f2f;");
    }

    public void cancelButtonOnMouseExitedEventHandler(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        Scene scene = node.getScene();

        VBox vBox = (VBox) scene.lookup("#closeVBox");

        vBox.setStyle(null);
    }

    public void singleFileHyperLinkOnActionEventHandler(ActionEvent actionEvent) {
        this.choice = 1;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void seperateFilesHyperLinkOnActionEventHandler(ActionEvent actionEvent) {
        this.choice = 2;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public int getChoice(){
        return this.choice;
    }
}
