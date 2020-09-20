//Author: Tafadzwa Brian Motsi

package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.modules.Alerting;
import sample.modules.ConvertImagesToPdf;
import sample.modules.FileOptionHelpHandlers;
import sample.modules.RestartApp;

import java.io.File;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class WelcomeUI extends Application {

    private static final String WELCOME_SCREEN_FXML_PATH = "fxml_images/welcome_scene.fxml";

    //icon names
    public static final String START_ICON = "fxml_images/start_icon.png";
    public static final String IMAGE_ICON = "fxml_images/image_icon.png";
    public static final String WORD_ICON = "fxml_images/word_icon.png";

    private static Stage primaryStage;
    public Pane chooseOptionPane;
    public GridPane welcomeSceneGridPane;
    public MenuBar welcomeSceneMenuBar;
    public Hyperlink convertImagesHyperLink;
    public Hyperlink convertWordDocxHyperLink;
    public Hyperlink scanImagesHyperLink;
    public Menu fileMenu;
    public MenuItem pngJpgMenuItem;
    public MenuItem docxMenuItem;
    public MenuItem welcomeCloseMenuItem;
    public Menu helpMenu;
    public Menu optionMenu;
    private final FXMLLoader welcomeScreenFxmlLoader;

    public WelcomeUI(){

        this.welcomeScreenFxmlLoader = new FXMLLoader();
        this.welcomeScreenFxmlLoader.setLocation(WelcomeUI.class.getResource(WELCOME_SCREEN_FXML_PATH));
        this.chooseOptionPane = new Pane();
        this.welcomeSceneGridPane = new GridPane();
        this.welcomeSceneMenuBar = new MenuBar();

        this.fileMenu = new Menu();
        this.optionMenu = new Menu();
        this.helpMenu = new Menu();
    }

    public void setPrimaryStageIcon(Stage primaryStage, String iconName){
        primaryStage.getIcons().setAll(new Image(String.valueOf(getClass().getResource(iconName))));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Start Converting");
        this.setPrimaryStageIcon(primaryStage, START_ICON);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        Scene welcomeScene = this.welcomeScreenScene();

        primaryStage.setScene(welcomeScene);
        primaryStage.show();

        WelcomeUI.primaryStage = primaryStage;
    }

    //get welcome screen scene
    public Scene welcomeScreenScene() throws Exception{
        return new Scene(this.welcomeScreenFxmlLoader.load(), 1300, 400);
    }


    //get welcome screen stage
    public static Stage welcomeScreenStage(){
        return primaryStage;
    }

    //get the option pane
    public Pane getChooseOptionPane() {
        return this.chooseOptionPane;
    }

    public void convertImagesHyperLinkHandler() throws Exception{

        ConvertImagesToPdfUI convertImagesToPdfUI = new ConvertImagesToPdfUI();
        Scene imagesScene = convertImagesToPdfUI.convertImagesToPdfScene();
        primaryStage.setTitle("Convert Images To PDF Files");
        primaryStage.setScene(imagesScene);


        ImageView dropImageFilesImageView = (ImageView)imagesScene.lookup("#dropImageFilesImageView");
        dropImageFilesImageView.setImage(new Image(getClass().getResourceAsStream(ConvertImagesToPdfUI.DROP_ICON_PATH)));

    }

    public void convertWordDocxHyperLinkHandler(ActionEvent actionEvent) throws Exception{
        ConvertWordDocumentsToPdfUI convertWordDocumentsToPdfUI = new ConvertWordDocumentsToPdfUI();
        Scene wordDocumentsScene = convertWordDocumentsToPdfUI.wordDocumentsScene();

        primaryStage.setTitle("Convert Word Documents To PDF Files");
        primaryStage.setScene(wordDocumentsScene);

        ImageView dragWordDocxImageView = (ImageView)wordDocumentsScene.lookup("#dragDocxFilesImageView");
        dragWordDocxImageView.setImage(new Image(getClass().getResourceAsStream(String.format("%s", ConvertWordDocumentsToPdfUI.DROP_ICON_PATH))));

    }

    public void scanImagesHyperLinkHandler(ActionEvent actionEvent) {
        new Alerting(Alert.AlertType.INFORMATION, "Information", "The option is not yet available").getAlert();
    }

    public void welcomeCloseMenuItemHandler() {
        WelcomeUI.primaryStage.close();
        System.exit(0);
    }

    //handle the images menu item
    public void pngJpgMenuItemHandler() throws Exception {

        List<File> pngJpgImgesFiles = FileOptionHelpHandlers.getSelectedPngJpgImageFiles(WelcomeUI.primaryStage);

        if(pngJpgImgesFiles != null){
            ConvertImagesToPdfUI convertImagesToPdfUI = new ConvertImagesToPdfUI();
            Scene convertImagesToPdfUIScene = convertImagesToPdfUI.convertImagesToPdfScene();

            WelcomeUI.primaryStage.setTitle("Convert Images To PDF Files");
            WelcomeUI.primaryStage.setScene(convertImagesToPdfUIScene);

            convertImagesToPdfUI.putImagesToFlowPane(
                    pngJpgImgesFiles,
                    (FlowPane)convertImagesToPdfUIScene.lookup("#imagesFlowPane")
            );

            Button generateButton = (Button)convertImagesToPdfUIScene.lookup("#generatePdfButton");
            generateButton.setOnAction(
                    event -> {
                        if(!pngJpgImgesFiles.isEmpty()){
                            DirectoryChooser directoryChooser = new DirectoryChooser();
                            directoryChooser.setTitle("Save Files");

                            File saveFile = directoryChooser.showDialog(WelcomeUI.primaryStage);

                            if(saveFile != null){
                                for(File file: pngJpgImgesFiles){
                                    ConvertImagesToPdf.convertImageToPdf(
                                            file.getAbsolutePath(),
                                            saveFile.getAbsolutePath().concat("\\").concat(file.getName().split("\\.")[0]).concat(".pdf"));
                                }

                                new Alerting(Alert.AlertType.INFORMATION,
                                        "Information",
                                        "Conversion completed successfully").getAlert();

                                try {
                                    RestartApp.restartApp();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );
        }
    }

    public void docxFileMenuItemHandler() throws Exception {

        List<File> docxFiles = FileOptionHelpHandlers.getSelectedDocxFiles(WelcomeUI.primaryStage);

        if(docxFiles!= null){
            ConvertWordDocumentsToPdfUI convertWordDocumentsToPdfUI = new ConvertWordDocumentsToPdfUI();
            Scene wordDocumentsScene = convertWordDocumentsToPdfUI.wordDocumentsScene();

            WelcomeUI.primaryStage.setTitle("Convert Word Documents To PDF Files");
            WelcomeUI.primaryStage.setScene(wordDocumentsScene);

            Text dragWordDocumentsText = (Text)wordDocumentsScene.lookup("#dragWordDocumentsText");
            ImageView dragDocxFilesImageView = (ImageView)wordDocumentsScene.lookup("#dragDocxFilesImageView");

            convertWordDocumentsToPdfUI.putWordDocumentToStackPane(
                    docxFiles,
                    dragWordDocumentsText,
                    dragDocxFilesImageView
            );


            Button generatePdfButton = (Button)wordDocumentsScene.lookup("#generatePdfButton");
            generatePdfButton.setOnAction(
                    event -> {
                        if(!docxFiles.isEmpty()){
                            try {
                                convertWordDocumentsToPdfUI.saveDocxFiles(docxFiles);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );

            dragDocxFilesImageView.setOnMouseClicked(
                    event -> {
                        if(event.getButton().toString().toLowerCase().equals("primary")){
                            if(!docxFiles.isEmpty()){
                                try {
                                    convertWordDocumentsToPdfUI.saveDocxFiles(docxFiles);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );
        }
    }

    public void imagesMenuItemOnActionHandler(ActionEvent actionEvent) throws Exception {
        this.convertImagesHyperLinkHandler();
    }

    public void docxOptionMenuItemHandler(ActionEvent actionEvent) throws Exception {
        this.convertWordDocxHyperLinkHandler(actionEvent);
    }

    public void aboutAppMenuItemOnActionHandler() throws Exception {
        new AboutUI().aboutAppStage();
    }

    public void aboutAuthorMenuItemOnActionHandler() throws Exception {
        new AboutUI().aboutAuthorStage();
    }

    public void openBrowser(Hyperlink hyperlink) {
        getHostServices().showDocument(hyperlink.getText());
    }
}
