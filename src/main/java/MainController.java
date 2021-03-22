package main.java;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController {
    @FXML private StackPane stackPane;
    @FXML private AnchorPane githubPane = new AnchorPane();
    @FXML private AnchorPane flagPane = new AnchorPane();
    @FXML private AnchorPane mainPane;
    @FXML private JFXButton playButton;
    @FXML private JFXButton flagButton;
    @FXML private JFXButton settingsButton;
    @FXML private JFXButton githubButton;
    @FXML private Button dontPush;
    @FXML private ImageView gifGithub;
    @FXML private Label morseLabel;    

    @FXML
    void playBttEvent(ActionEvent e) throws Exception{
        //This change the scene to morse scene
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MorseView.fxml"));
        Stage morseView = (Stage) playButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/styles/MorseView.css").toExternalForm());
        morseView.setScene(scene);
    }

    @FXML
    void dontPushBttEvent(ActionEvent e){
        PlaySound.playSounds("src/resources/sounds/DontPush.wav");
    }

    @FXML
    void SettingsAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/SettingsView.fxml"));
        //        Stage settingsView = (Stage) playButton.getScene().getWindow();
        //        Scene scene = new Scene(root);
        //        scene.getStylesheets().add(getClass().getResource("/resources/styles/SettingsView.css").toExternalForm());
        //        settingsView.setScene(scene);
        Scene scene = settingsButton.getScene();
        root.translateYProperty().set(scene.getHeight());
        stackPane.getChildren().add(root);
        
        Timeline timeLine = new Timeline();
        //Key Value for the fxml load from south to north, with Interpolator.EASE_IN this means slow-->fast
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        //Key Frame is the time that the KeyValue takes time to move        
        KeyFrame kf = new KeyFrame(Duration.seconds(1.3), kv);
        timeLine.getKeyFrames().add(kf);
        timeLine.setOnFinished(eventS->{
            stackPane.getChildren().remove(mainPane);
        });
        settingsButton.setDisable(true);
        timeLine.play();
    
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

            transition.setOnFinished(evnt ->{
                button.setDisable(false);
            });
        }

        /*Errors during code creation
    -----Look, now i understand, when the transition is call play(); keep run, i mean
         The code calls githubPane.setVisible(true); as well, if you want to see, put inside
         the lambda expression githubPane.setVisible(true); and the panel doesn't appear 
         until the animation finishes. So, that was the problem when the Pane dissapears in
         "if(githubPane.isVisible()){"

    -----For some reason, when i use this automatically or quickly the githubpane isn't visible
         And the transition doesn't load, that's why i'm using lambda;
            transition.setOnFinished(evnt ->{
                githubPane.setVisible(false);
                githubButton.setDisable(false);
            });
    -----
        */
    }

}
