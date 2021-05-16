package main.java;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class TutorialController implements Initializable {
    @FXML private StackPane stackPane;
    @FXML private AnchorPane generalPane;
    @FXML private JFXButton backBtt;
    Settings settings;
    Stats stats;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Platform.runLater(()->{
            settings.getMusicBg().stopMediaPlayer();        
            if(settings.getMusic())
                settings.getMusicBg().setSongTrack(4);
        });
    }

    @FXML
    void backEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/MainView.fxml"));
        Parent root = loader.load();

        MainController mainCt = loader.getController();
        mainCt.setValues(settings, stats);

        Scene scene = backBtt.getScene();
        StackPane mainView = (StackPane) scene.getRoot();
        scene.getStylesheets().add(getClass().getResource("/resources/styles/Main.css").toExternalForm()); 
        root.translateXProperty().set(scene.getHeight());
        mainView.getChildren().add(root);

        Timeline timeLine = new Timeline(
            new KeyFrame(
                Duration.seconds(1.3), 
                new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN)
            )
        );
        timeLine.play();
    }
    
    public void setValues(Settings settings, Stats stats){
        this.settings = settings;
        this.stats = stats;
        generalPane.setOpacity(settings.getBrightness());
    }
}
