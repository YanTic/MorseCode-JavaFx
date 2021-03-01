package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    private Button playButton;

    @FXML
    void playBttEvent(ActionEvent e){
        System.out.println("Hi i'm Mr Button");
    }
}
