//Author: Tafadzwa Brian Motsi

package sample;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import sample.modules.Alerting;
import sample.modules.ConvertWordDocumentsToPdf;
import sample.modules.ProcessingProgressBar;
import sample.modules.RestartApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ConvertWordDocumentsToPdfUI{
    private static final String WORD_DOCUMENTS_SCENE_FXML_PATH = "fxml_images/drag_word_documents_scene.fxml";
    public static final String FILES_ADDED_PATH = "fxml_images/files_added_image.png";
    public static final String DROP_ICON_PATH = "fxml_images/drop3.jpg";

    public FlowPane wordDocumentsFlowPane;
    public ImageView dragDocxFilesImageView;
    public Text dragWordDocumentsText;
    public MenuItem aboutAppMenuItem;
    public MenuItem aboutAuthorMenuItem;
    public MenuBar wordDocumentsMenuBar;
    private final FXMLLoader wordDocumentsFxmlLoader;
    public StackPane stackPane;
    private final List<File> wordDocumentsList;

    public ConvertWordDocumentsToPdfUI(){
        this.wordDocumentsFxmlLoader = new FXMLLoader();
        this.wordDocumentsFxmlLoader.setLocation(ConvertWordDocumentsToPdfUI.class.getResource(WORD_DOCUMENTS_SCENE_FXML_PATH));
        this.wordDocumentsFlowPane = new FlowPane();
        this.wordDocumentsList = new ArrayList<>();

        this.dragDocxFilesImageView = new ImageView();
        this.dragWordDocumentsText = new Text();
        this.stackPane = new StackPane();
    }

    public Scene wordDocumentsScene() throws Exception{
        return new Scene(this.wordDocumentsFxmlLoader.load(), 1300, 400);
    }

    private void addWordDocumentsToPane(File file){
        if(String.valueOf(file).contains(".docx") || String.valueOf(file).contains(".doc")){
            if(!this.wordDocumentsList.contains(file)){
                this.wordDocumentsList.add(file);
            }
        }
    }

    public void backButtonHandler() throws Exception {
        new WelcomeUI().setPrimaryStageIcon(WelcomeUI.welcomeScreenStage(), WelcomeUI.START_ICON);
        RestartApp.restartApp();
    }

    public void saveDocxFiles(List<File> wordDocumentsList) throws Exception {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Save Files");

        File saveFile = directoryChooser.showDialog(WelcomeUI.welcomeScreenStage());

        if(saveFile != null) {
            Task<Void> convertingDocumentsTask  = new Task<Void>() {
                @Override
                protected Void call() {

                    int documentCounter = 1;

                    for (File file : wordDocumentsList) {
                        ConvertWordDocumentsToPdf.convertDocxToPdf(file.getAbsolutePath(), saveFile.getAbsolutePath().concat("\\").concat(file.getName().split("\\.")[0]).concat(".pdf"));

                        updateProgress(documentCounter++, wordDocumentsList.size());
                    }
                    return null;
                }
            };

            ProcessingProgressBar.progressBarThread(convertingDocumentsTask, this.stackPane);
        }
    }

    public void generateButtonHandler() throws Exception {
        if(!this.wordDocumentsList.isEmpty()){
            this.saveDocxFiles(this.wordDocumentsList);
        }

        else{
            new Alerting(Alert.AlertType.INFORMATION,
                    "Information",
                    "No documents to convert").getAlert();
        }
    }

    public void dragWordDocumentsImageViewMouseClickHandler(MouseEvent mouseEvent) throws Exception {
        if(mouseEvent.getButton().toString().toLowerCase().equals("primary")){
            this.generateButtonHandler();
        }
    }

    public void wordDocumentsPaneDragOverHandler(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasFiles()){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void wordDocumentsPaneDragDroppedHandler(DragEvent dragEvent) throws Exception {
        List<File> fileList = dragEvent.getDragboard().getFiles();

        fileList.forEach(this::addWordDocumentsToPane);

        if(!this.wordDocumentsList.isEmpty()){
            String docValue;
            if(this.wordDocumentsList.size() == 1){
                docValue = "document";
            }

            else{
                docValue = "documents";
            }

            this.dragWordDocumentsText.setText(this.wordDocumentsList.size() + String.format(" word %s added", docValue));
            this.dragDocxFilesImageView.resize(480, 512);
            this.dragDocxFilesImageView.setImage(new Image(getClass().getResourceAsStream(FILES_ADDED_PATH)));
        }
    }

    public void wordDocumentsTextMouseClickHandler(MouseEvent mouseEvent) throws Exception {
        this.dragWordDocumentsImageViewMouseClickHandler(mouseEvent);
    }

    public void putWordDocumentToStackPane(List<File> wordDocumentsList, Text dragWordDocumentsText, ImageView dragDocxFilesImageView) {
        if (!wordDocumentsList.isEmpty()) {
            String docValue;
            if (wordDocumentsList.size() == 1) {
                docValue = "document";
            } else {
                docValue = "documents";
            }

            dragWordDocumentsText.setText(wordDocumentsList.size() + String.format(" word %s added", docValue));
            dragDocxFilesImageView.resize(480, 512);
            dragDocxFilesImageView.setImage(new Image(getClass().getResourceAsStream(FILES_ADDED_PATH)));
        }
    }

    public void fileOpenMenuItemOnActionHandler() throws Exception {
        new WelcomeUI().docxFileMenuItemHandler();
    }

    public void fileCloseMenuItemOnActionHandler() throws Exception {
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
