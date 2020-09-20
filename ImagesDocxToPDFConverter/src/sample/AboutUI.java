//Author: Tafadzwa Brian Motsi

package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutUI{
    private static final String ABOUT_AUTHOR_FXML_PATH = "fxml_images/about_author_scene.fxml";
    private static final String ABOUT_APP_FXML_PATH = "fxml_images/about_app_scene.fxml";
    private static final String AUTHOR_FACEBOOK_LINK = "https://www.facebook.com/tafadzwabmotsi";
    private static final String AUTHOR_TWITTER_LINK = "https://twitter.com/tafadzwabmotsi";

    private static final String ABOUT_APP_ICON = "fxml_images/about_app_icon.png";
    private static final String ABOUT_AUTHOR_ICON = "fxml_images/about_author_icon.png";


    public Hyperlink authorFacebookHyperLink;
    public Hyperlink authorTwitterHyperLink;

    private final FXMLLoader aboutAppFxmlLoader;
    private final FXMLLoader aboutAuthorFxmlLoader;

    private static Stage aboutStage;
    public Pane aboutAuthorPane;

    public AboutUI(){
        this.aboutAppFxmlLoader = new FXMLLoader();
        this.aboutAuthorFxmlLoader = new FXMLLoader();

        this.aboutAppFxmlLoader.setLocation(AboutUI.class.getResource(ABOUT_APP_FXML_PATH));
        this.aboutAuthorFxmlLoader.setLocation(AboutUI.class.getResource(ABOUT_AUTHOR_FXML_PATH));

        this.aboutAuthorPane = new Pane();
    }

    public void setAboutStageIcon(Stage aboutStage, String iconName){
        aboutStage.getIcons().setAll(new Image(String.valueOf(getClass().getResource(iconName))));
    }

    private void initAboutStage(String title, Scene aboutScene){
        Stage authorStage = new Stage();
        authorStage.initModality(Modality.APPLICATION_MODAL);

        authorStage.setTitle(title);
        authorStage.setResizable(false);
        authorStage.centerOnScreen();
        authorStage.setScene(aboutScene);
        authorStage.show();

        AboutUI.aboutStage = authorStage;
    }

    public void aboutAuthorStage() throws Exception {
        this.initAboutStage("About the author", this.aboutAuthorScene());
        this.setAboutStageIcon(AboutUI.aboutStage, ABOUT_AUTHOR_ICON);
    }

    public void aboutAppStage() throws Exception {
        this.initAboutStage("About the app", this.aboutAppScene());
        this.setAboutStageIcon(AboutUI.aboutStage, ABOUT_APP_ICON);

    }

    private Scene aboutAppScene() throws Exception{
        return new Scene(this.aboutAppFxmlLoader.load(), 650, 300);
    }

    private Scene aboutAuthorScene() throws Exception{
        return new Scene(this.aboutAuthorFxmlLoader.load(), 800, 300);
    }

    public void authorFacebookHyperLinkOnActionHandler() {
        this.authorFacebookHyperLink = new Hyperlink(AUTHOR_FACEBOOK_LINK);
        new WelcomeUI().openBrowser(this.authorFacebookHyperLink);
    }

    public void authorTwitterHyperLinkOnActionHandler() {
        this.authorTwitterHyperLink = new Hyperlink(AUTHOR_TWITTER_LINK);
        new WelcomeUI().openBrowser(this.authorTwitterHyperLink);
    }

    public void aboutAuthorFacebookImageViewOnMouseClickedHandler(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().toString().toLowerCase().equals("primary")){
            this.authorFacebookHyperLinkOnActionHandler();
        }
    }

    public void aboutAuthorTwitterImageViewOnMouseClickedHandler(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().toString().toLowerCase().equals("primary")){
            this.authorTwitterHyperLinkOnActionHandler();
        }
    }

    public void aboutAuthorFacebookImageViewOnMouseEnteredHandler() {
        this.aboutAuthorPane.setCursor(Cursor.HAND);
    }

    public void aboutAuthorTwitterImageViewOnMouseEnteredHandler() {
        this.aboutAuthorPane.setCursor(Cursor.HAND);

    }

    public void aboutAuthorFacebookImageViewOnMouseExitedHandler() {
        this.aboutAuthorPane.setCursor(Cursor.DEFAULT);

    }

    public void aboutAuthorTwitterImageViewOnMouseExitedHandler() {
        this.aboutAuthorPane.setCursor(Cursor.DEFAULT);
    }
}
