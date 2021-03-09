package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {
    @FXML private Button playButton;
    @FXML private Button flagButton;
    @FXML private Button settingsButton;
    @FXML private Button coffeeButton;
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

}
