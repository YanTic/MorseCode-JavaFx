package main.java;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SettingsController implements Initializable{
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
    boolean music, musicEffects, doAssistance, saved;
    double brightness;
    Settings settings;
    Stats stats;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sliderBrightness.valueProperty().set(85);
        sliderBrightness.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                panelUp.setOpacity(sliderBrightness.getValue());
                panelDown.setOpacity(sliderBrightness.getValue());
            }
        });

        saved = false;
        Timeline timeline = show(SettingsPane.getPrefHeight(), 0);
        timeline.play();

        Platform.runLater(()->{            
            settings.getMusicBg().stopMediaPlayer();        
            if(settings.getMusic())
                settings.getMusicBg().setSongTrack(2);
        });
    }

    @FXML
    void exitEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/MainView.fxml"));
        Parent root = loader.load();   

        MainController mainCt = loader.getController();
        mainCt.setValues(settings, stats);                 

/*         Stage mainView = (Stage) exitBtt.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/styles/Main.css").toExternalForm()); */

        Timeline timeLine1 = show(0, SettingsPane.getPrefHeight());
//        timeLine2.setOnFinished(evnt->{mainView.setScene(scene);});
        timeLine1.play();
        
        Scene scene = exitBtt.getScene();
        StackPane mainView = (StackPane) scene.getRoot();
        scene.getStylesheets().add(getClass().getResource("/resources/styles/Main.css").toExternalForm()); 
        root.translateYProperty().set(scene.getHeight());
        mainView.getChildren().add(root);

        Timeline timeLine2 = new Timeline(
            new KeyFrame(
                Duration.seconds(1.3), 
                new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN)
            )
        );
        timeLine2.play();
    }

    @FXML
    void effectsEvent(ActionEvent event) {
        System.out.println("Effects Value: "+effectsToggBtt.selectedProperty().getValue());
    }

    @FXML
    void musicEvent(ActionEvent event) {
        System.out.println("Music Value: "+musicToggBtt.selectedProperty().getValue());
    }

    @FXML
    void saveEvent(ActionEvent event) throws IOException {
        music = musicToggBtt.selectedProperty().getValue();
        musicEffects = effectsToggBtt.selectedProperty().getValue();
        doAssistance = assistanceToggleBtt.selectedProperty().getValue();
        brightness = sliderBrightness.getValue();

        settings.setMusic(music);
        settings.setMusicEffects(musicEffects);
        settings.setDoAssistance(doAssistance);
        settings.setBrightness(brightness);

        setValues(settings, stats);
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Settings Saved");
        alert.setHeaderText(null);
        alert.show();
    }

    public void setValues(Settings settings, Stats stats){
        this.settings = settings;
        this.stats = stats;

        assistanceToggleBtt.selectedProperty().set(settings.getDoAssistance());
        effectsToggBtt.selectedProperty().set(settings.getMusicEffects());
        musicToggBtt.selectedProperty().set(settings.getMusic());
        sliderBrightness.valueProperty().set(settings.getBrightness());
        panelUp.setOpacity(settings.getBrightness());
        panelDown.setOpacity(settings.getBrightness());
    }

    private Timeline show(double fromY, double toY){
        Timeline timeLine = new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new KeyValue(panelUp.translateYProperty(), fromY),
                new KeyValue(panelDown.translateYProperty(), fromY)
            ),
            new KeyFrame(
                Duration.seconds(2.3), 
                new KeyValue(panelUp.translateYProperty(), toY, Interpolator.EASE_IN),
                new KeyValue(panelDown.translateYProperty(), toY, Interpolator.EASE_IN)
            )
        );
        return timeLine;
    }

}
