package main.java;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MainController implements Initializable{
    @FXML private StackPane stackPane;
    @FXML private AnchorPane githubPane = new AnchorPane();
    @FXML private AnchorPane flagPane = new AnchorPane();
    @FXML private AnchorPane mainPane;
    @FXML private AnchorPane menuPane;
    @FXML private JFXButton menuBtt;
    @FXML private JFXButton flagButton;
    @FXML private JFXButton settingsButton;
    @FXML private JFXButton githubButton;
    @FXML private JFXButton exitBtt;
    @FXML private JFXButton playBtt;
    @FXML private JFXButton statsBtt;
    @FXML private JFXButton tutorialBtt;
    @FXML private Button dontPush;
    @FXML private ImageView gifGithub;
    @FXML private Label morseLabel;    
    Settings settings = new Settings();
    Stats stats = new Stats();
    boolean music, musicEffects, doAssistance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackPane.setOpacity(0);
        doFadeTransition(stackPane);

        Platform.runLater(()->{
            settings.getMusicBg().stopMediaPlayer();        
            if(settings.getMusic())
                settings.getMusicBg().setSongTrack(0);
        }); 
    }

    public void setValues(Settings settings, Stats stats){
        this.settings = settings;
        this.stats = stats;
        mainPane.setOpacity(settings.getBrightness());
    }

    @FXML
    void menuEvent(ActionEvent e) {
        doTranslateTransition(menuPane, menuBtt, 0, -menuPane.getPrefWidth());
    }

    @FXML
    void dontPushBttEvent(ActionEvent e){
        PlaySound.playSounds("src/main/resources/sounds/DontPush.wav");
    }

    @FXML
    void githubPage(MouseEvent event) {
        try{
            Desktop.getDesktop().browse(new URI("https://github.com/YanTic"));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    @FXML
    void githubEvent(ActionEvent event) {
        doTranslateTransition(githubPane, githubButton, 0, -githubPane.getPrefWidth());
    }

    @FXML
    void errorEvent(ActionEvent event) {
        settings.getMusicBg().stopMediaPlayer();
        doTranslateTransition(flagPane, flagButton, 0, flagPane.getPrefWidth());
    }

    private void doTranslateTransition(Node node, JFXButton button, double fromX, double toX){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1.5));
        transition.setNode(node);

        if(node.isVisible()){
            button.setDisable(true);
            transition.setFromX(fromX);
            transition.setToX(toX);
            transition.play();

            transition.setOnFinished(evnt ->{
                node.setVisible(false);
                button.setDisable(false);
            });
        }
        else{
            button.setDisable(true);
            transition.setFromX(toX);
            transition.setToX(fromX);
            transition.play();
            node.setVisible(true);

            transition.setOnFinished(evnt -> {button.setDisable(false);});
        }
    }

    public static void doFadeTransition(Node node) {
        FadeTransition transition = new FadeTransition();
        transition.setNode(node);
        transition.setDuration(Duration.seconds(3));
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    @FXML
    void menuExitEvent(ActionEvent event) {
        menuEvent(event);
    }

    @FXML
    void SettingsAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlViews/SettingsView.fxml"));
        Parent root = loader.load();

        //Set Values
        SettingsController ssCt = loader.getController();
        ssCt.setValues(settings, stats);

        Scene scene = statsBtt.getScene();
        StackPane settingsView = (StackPane) scene.getRoot();
        scene.getStylesheets().add(getClass().getResource("/styles/SettingsView.css").toExternalForm());
        root.translateYProperty().set(scene.getHeight());
        settingsView.getChildren().add(root);

        Timeline timeLine = transitionScene(root, 'Y');
        timeLine.play();
    }

    @FXML
    void playEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlViews/MorseView.fxml"));
        Parent root = loader.load();

        MorseController morCt = loader.getController();
        morCt.setValues(settings, stats);

        Scene scene = playBtt.getScene();
        StackPane morseView = (StackPane) scene.getRoot();
        scene.getStylesheets().add(getClass().getResource("/styles/MorseView.css").toExternalForm());
        root.translateXProperty().set(-scene.getWidth());
        morseView.getChildren().add(root);

        Timeline timeLine = transitionScene(root, 'X');
        timeLine.play();
    }

    @FXML
    void statsEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlViews/StatsView.fxml"));
        Parent root = loader.load();

        StatsController staCt = loader.getController();
        staCt.setValues(settings, stats);
        
        Scene scene = statsBtt.getScene();
        StackPane statsView = (StackPane) scene.getRoot();
        scene.getStylesheets().add(getClass().getResource("/styles/StatsView.css").toExternalForm());
        root.translateXProperty().set(-scene.getWidth());
        statsView.getChildren().add(root);

        Timeline timeLine = transitionScene(root, 'X');
        timeLine.play();        
    }

    @FXML
    void tutorialEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlViews/TutorialView.fxml"));
        Parent root = loader.load();

        TutorialController tutoCt = loader.getController();
        tutoCt.setValues(settings, stats);
        
        Scene scene = tutorialBtt.getScene();
        StackPane tutorialView = (StackPane) scene.getRoot();
//        scene.getStylesheets().add(getClass().getResource("/resources/styles/TutorialView.css").toExternalForm());
        root.translateXProperty().set(-scene.getWidth());
        tutorialView.getChildren().add(root);

        Timeline timeLine = transitionScene(root, 'X');
        timeLine.play();
    }

    public Timeline transitionScene(Parent root, char translateProperty){
        Timeline timeLine = null;

        if(translateProperty == 'X'){
            timeLine = new Timeline(
                new KeyFrame(
                    Duration.seconds(1.3), 
                    new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN)
                )
            );
        }
        else if(translateProperty == 'Y'){
            timeLine = new Timeline(
                new KeyFrame(
                    Duration.seconds(1.3), 
                    new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN)
                )
            );
        }

        return timeLine;
    }

}
