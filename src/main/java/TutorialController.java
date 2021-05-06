package main.java;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TutorialController implements Initializable {
    @FXML private StackPane stackPane;
    @FXML private AnchorPane generalPane;
    @FXML private JFXButton backBtt;
    Settings settings;
    Stats stats;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

    @FXML
    void backEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/MainView.fxml"));
        Parent root = loader.load();

        MainController mainCt = loader.getController();
        mainCt.setValues(settings, stats);

        Stage mainView = (Stage) backBtt.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/styles/Main.css").toExternalForm());
        mainView.setScene(scene);
    }
    
    public void setValues(Settings settings, Stats stats){
        this.settings = settings;
        this.stats = stats;
        generalPane.setOpacity(settings.getBrightness());
    }
}
