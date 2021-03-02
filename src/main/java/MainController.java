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
    @FXML private Label morseLabel;

    @FXML
    void playBttEvent(ActionEvent e) throws Exception{
        //This change the scene to morse scene
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MorseView.fxml"));
        Stage morseView = (Stage) playButton.getScene().getWindow();
        morseView.setScene(new Scene(root, 800, 600));
    }
}
