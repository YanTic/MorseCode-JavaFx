package main.java;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController {
    @FXML private StackPane stackPane;
    @FXML private AnchorPane mainPane;
    @FXML private Button playButton;
    @FXML private Button flagButton;
    @FXML private Button settingsButton;
    @FXML private Button githubButton;
    @FXML private Button dontPush;
    @FXML private Label morseLabel;    

    @FXML
    void playBttEvent(ActionEvent e) throws Exception{
        //This change the scene to morse scene
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MorseView.fxml"));
        Stage morseView = (Stage) playButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/styles/MorseView.css").toExternalForm());
/*lambda
MorseController keys = new MorseController();
        scene.setOnKeyPressed(event ->{
            System.out.println("Key pressed: "+event.getText());
            if(event.getText().equals(".")){
                System.out.println("WORKS");
            }
            if(event.getText().equals("-")){
                System.out.println("WORKS");
                keys.dashEvent(e);
            }
        });     This worked, but actually not. In the TextField doesn't write anything*/
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
        timeLine.play();
    }

}
