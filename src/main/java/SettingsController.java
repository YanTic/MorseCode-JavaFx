package main.java;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SettingsController {
    @FXML private AnchorPane SettingsPane;
    @FXML private AnchorPane panelUp;
    @FXML private AnchorPane panelDown;
    @FXML private JFXButton saveButton;
    @FXML private JFXButton exitBtt;
    @FXML private JFXToggleButton musicToggBtt;
    @FXML private JFXToggleButton effectsToggBtt;
    @FXML private JFXToggleButton assistanceToggleBtt;
    @FXML private JFXSlider sliderBrightness;
    @FXML private Label assistanceLb;
    @FXML private Label musicLb;
    @FXML private Label effectsLb;
    @FXML private Label brightnessLb;
    @FXML private Label informationLb;

    @FXML
    void exitEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MainView.fxml"));
        Scene scene = exitBtt.getScene();
        root.translateYProperty().set(-scene.getHeight());

        StackPane stackPane = (StackPane) scene.getRoot();
        stackPane.getChildren().add(root);
        
        Timeline timeLine = new Timeline();
        timeLine.setOnFinished(eventS->{
            stackPane.getChildren().remove(SettingsPane);
        });
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1.3), kv);
        timeLine.getKeyFrames().add(kf);
        timeLine.play();
    }

    @FXML
    void assistanceEvent(ActionEvent event) {

    }

    @FXML
    void effectsEvent(ActionEvent event) {

    }

    @FXML
    void musicEvent(ActionEvent event) {

    }

    @FXML
    void saveEvent(ActionEvent event) {

    }

    @FXML
    void sliderEvent(MouseEvent event) {

    }

}
