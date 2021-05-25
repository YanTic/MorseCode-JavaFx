package main.java;

import java.io.File;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class TutorialController implements Initializable {
    @FXML private StackPane stackPane;
    @FXML private AnchorPane generalPane;
    @FXML private JFXButton dashButton;
    @FXML private JFXButton dotButton;
    @FXML private JFXButton backBtt;
    @FXML private JFXButton repeatBtt;
    @FXML private ScrollPane helpScrollPane;
    @FXML private MediaView videoTutorial;
    Settings settings;
    Stats stats;
    File file;
    Media media;
    MediaPlayer mediaPlayer;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Platform.runLater(()->{
            settings.getMusicBg().stopMediaPlayer();        
            if(settings.getMusic())
                settings.getMusicBg().setSongTrack(4);
        });

        file = new File("src/main/resources/images/tutorial.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        videoTutorial.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }

    @FXML
    void backEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlViews/MainView.fxml"));
        Parent root = loader.load();

        MainController mainCt = loader.getController();
        mainCt.setValues(settings, stats);

        Scene scene = backBtt.getScene();
        StackPane mainView = (StackPane) scene.getRoot();
        scene.getStylesheets().add(getClass().getResource("/styles/Main.css").toExternalForm()); 
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

    @FXML
    void dotEvent(ActionEvent event) {
        PlaySound.stopSounds();
        if(settings.getMusicEffects())
            PlaySound.playSounds("src/main/resources/sounds/dot.wav");
    }

    @FXML
    void dashEvent(ActionEvent event) {
        PlaySound.stopSounds();
        if(settings.getMusicEffects())
            PlaySound.playSounds("src/main/resources/sounds/dash.wav");
    }

    @FXML
    void repeatVideo(ActionEvent event) {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.play();
    }

}

