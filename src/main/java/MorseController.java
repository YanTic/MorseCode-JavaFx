package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MorseController {

    @FXML
    private Button dotButton;

    @FXML
    private Button dashButton;

    @FXML
    private TextField morseText;

    @FXML
    void dashEvent(ActionEvent event) {
        System.out.println("Dash");
    }

    @FXML
    void dotEvent(ActionEvent event) {
        System.out.println("Dot");
    }

}

