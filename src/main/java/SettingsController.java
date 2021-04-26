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
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
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
    FXMLLoader loader;
    Parent root;
    boolean music, musicEffects, doAssistance;
    double brightness;
    Settings settings;

/*  DELETE THIIIIIIIS
    DELETE THE USSELES COMMENTS
    AND THE SLIDER DO SOMETHING    
*/


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
//        musicToggBtt.selectedProperty().set(true);
//        effectsToggBtt.selectedProperty().set(true);
//        assistanceToggleBtt.selectedProperty().set(true);
        sliderBrightness.valueProperty().set(85);
//        SettingsPane.setVisible(false);

        sliderBrightness.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                panelUp.setOpacity(sliderBrightness.getValue());
                panelDown.setOpacity(sliderBrightness.getValue());
            }
        });



        show(/* SettingsPane, exitBtt, */ SettingsPane.getPrefHeight(), 0);

        try {
            loader = new FXMLLoader(getClass().getResource("/resources/view/MainView.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exitEvent(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/MainView.fxml"));
//        Parent root = loader.load();

/*         //Set Values
        MainController ssCt = loader.getController();
        ssCt.setValues(doAssistance, music, musicEffects); */

        //Show controller
        Stage mainView = (Stage) exitBtt.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
//        mainView.setScene(scene);

        Timeline timeLine = new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new KeyValue(SettingsPane.translateYProperty(), 0)
            ),
            new KeyFrame(
                Duration.seconds(1.5), 
                new KeyValue(SettingsPane.translateYProperty(), SettingsPane.getPrefHeight(), Interpolator.EASE_IN))
        );
        timeLine.setOnFinished(evnt->{
            mainView.setScene(scene);
        });
        timeLine.play();





        /* Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MainView.fxml"));
        Scene scene = exitBtt.getScene();
        root.translateYProperty().set(-scene.getHeight());

        StackPane stackPane = (StackPane) scene.getRoot();
        stackPane.getChildren().add(root);
        
        Timeline timeLine = new Timeline(
            new KeyFrame(
                Duration.seconds(1.3),
                new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN))
        );
        timeLine.setOnFinished(eventS->{
            stackPane.getChildren().remove(SettingsPane);
        });
        timeLine.play(); */
    }

    @FXML
    void assistanceEvent(ActionEvent event) {
        System.out.println("Assitance Value: "+assistanceToggleBtt.selectedProperty().getValue());
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

//        rectBrightness.setOpacity(brightness / 100);

        settings.setMusic(music);
        settings.setMusicEffects(musicEffects);
        settings.setDoAssistance(doAssistance);
        settings.setBrightness(brightness);

        setValues(settings/* , doAssistance, music, musicEffects */);

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/MainView.fxml"));
//        Parent root = loader.load();
        MainController mainCt = loader.getController();
        mainCt.setValues(settings/* doAssistance, music, musicEffects */);
        
        Alert alert = new Alert(AlertType.INFORMATION, "Saved"/* , javafx.scene.control.ButtonType.OK */);
//        alert.getDialogPane().setMinHeight(SettingsPane.USE_PREF_SIZE);
        alert.show();

/*         assistanceToggleBtt.selectedProperty().set(assistanceToggleBtt.selectedProperty().getValue());
        effectsToggBtt.selectedProperty().set(effectsToggBtt.selectedProperty().getValue());
        musicToggBtt.selectedProperty().set(musicToggBtt.selectedProperty().getValue()); */

//        sliderBrightness.valueProperty().set();
    }

    public void setValues(Settings settings){
        this.settings = settings;

        assistanceToggleBtt.selectedProperty().set(settings.getDoAssistance());
        effectsToggBtt.selectedProperty().set(settings.getMusicEffects());
        musicToggBtt.selectedProperty().set(settings.getMusic());
        sliderBrightness.valueProperty().set(settings.getBrightness());
        panelUp.setOpacity(settings.getBrightness());
        panelDown.setOpacity(settings.getBrightness());

/*         assistanceToggleBtt.selectedProperty().set(doAssistance);
        effectsToggBtt.selectedProperty().set(musicEffects);
        musicToggBtt.selectedProperty().set(music);  */
    }

    /* public void setValues(boolean doAssistance, boolean music, boolean musicEffects){
        this.doAssistance = doAssistance;
        this.music = music;
        this.musicEffects = musicEffects;
    } */


    private void show(/* Node node, JFXButton button ,*/ double fromY, double toY){
        /* root.translateYProperty().set(SettingsPane.getHeight());
        Timeline timeLine = new Timeline(
            new KeyFrame(
                Duration.seconds(1.3), 
                new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN))
        );
//        settingsButton.setDisable(true);
        timeLine.play(); */


        Timeline timeLine = new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new KeyValue(SettingsPane.translateYProperty(), fromY)
            ),
            new KeyFrame(
                Duration.seconds(1.3), 
                new KeyValue(SettingsPane.translateYProperty(), toY, Interpolator.EASE_IN)
            )
        );
        timeLine.play();

//These two animations are the same, not now :(


//THIS TRANSITION WORKS
        /* TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1.3));
        transition.setNode(node);

        if(node.isVisible()){
            button.setDisable(true);
            transition.setFromY(toY);
            transition.setToY(fromY);
            transition.play();

            transition.setOnFinished(evnt ->{
                node.setVisible(false);
                button.setDisable(false);
            });
        }
        else{
            button.setDisable(true);
            transition.setFromY(fromY);
            transition.setToY(toY);
            transition.play();
            node.setVisible(true);

            transition.setOnFinished(evnt ->{
                button.setDisable(false);
            });
        } */

    }

}
