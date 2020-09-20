package sample.modules;

import javafx.stage.Stage;
import sample.WelcomeUI;

public class RestartApp {
    public static void restartApp() throws Exception{
        Stage stage = WelcomeUI.welcomeScreenStage();
        stage.setTitle("Start Converting");
        stage.setScene(new WelcomeUI().welcomeScreenScene());
        stage.show();
    }
}
