package main.java;

import main.java.morseCode.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MorseController {
    @FXML private Button returnButton;
    @FXML private Button dashButton;
    @FXML private Button dotButton;
    @FXML private Button RunButton;
    @FXML private TextField morseText;
    @FXML private Label wordLabel = new Label();
    @FXML private Label letterLabel = new Label();
    Words word = new Words();
    String textLabel, letter, letterToMorse;

    @FXML
    void dotEvent(ActionEvent event) {
        morseText.setText(morseText.getText() + ".");
        PlaySound.playSounds("src/resources/sounds/dot.wav");
    }

    @FXML
    void dashEvent(ActionEvent event) {
        morseText.setText(morseText.getText() + "-");
        PlaySound.playSounds("src/resources/sounds/dash.wav");
    }

    @FXML
    void returnBttEvent(ActionEvent event) throws IOException {
        // This change the scene, return to the main scene
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MainView.fxml"));
        Stage mainView = (Stage) returnButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/styles/Main.css").toExternalForm());
        mainView.setScene(scene);
    }

    @FXML
    void RunBttEvent(ActionEvent event) {
        textLabel = word.words[(int)(Math.random()*336)];
        System.out.println("Word: "+textLabel);
        System.out.println("letters: "+textLabel.length());
            for(int i=0; i<textLabel.length(); i++){
                System.out.print(" "+textLabel.charAt(i));
            }
        System.out.print("\n");
        letter = ""+textLabel.charAt(0);
        wordLabel.setText(textLabel);
        letterLabel.setText(letter);

        MorseLanguage p1 = new MorseLanguage();
            System.out.println(""+letter+" to morse is: "+p1.translate(letter)); 
    }

}

