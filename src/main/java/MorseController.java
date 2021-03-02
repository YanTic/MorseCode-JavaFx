package main.java;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MorseController {
    @FXML private Button returnButton;
    @FXML private Button dashButton;
    @FXML private Button dotButton;
    @FXML private TextField morseText;

    @FXML
    void dotEvent(ActionEvent event) {
        morseText.setText(morseText.getText() + ".");
    }

    @FXML
    void dashEvent(ActionEvent event) {
        morseText.setText(morseText.getText() + "-");
    }

    @FXML
    void returnBttEvent(ActionEvent event) throws IOException {
        // This change the scene, return to the main scene
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MainView.fxml"));
        Stage mainView = (Stage) returnButton.getScene().getWindow();
        mainView.setScene(new Scene(root, 800, 600));
    }

}

