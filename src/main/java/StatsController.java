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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StatsController implements Initializable{
    @FXML private StackPane stackPane;
    @FXML private AnchorPane generalPane;
    @FXML private JFXButton backBtt;
    @FXML private LineChart<?, ?> lineChart;
    @FXML private CategoryAxis lineX;
    @FXML private NumberAxis lineY;
    @FXML private PieChart pieChart;
    @FXML private Label tipsShowed;
    @FXML private Label tipsShowed2;
    @FXML private Label correctLetters;
    @FXML private Label correctWords;
    @FXML private Label incorrectLetters;
    @FXML private Label preferWord;
    @FXML private Label timesTyped;
    @FXML private Label wordsTyped;
    @FXML private Label lettersTyped;
    Stats stats;
    Settings settings;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
    public void setValues(Settings settings, Stats stats){
        this.settings = settings;
        this.stats = stats;

        generalPane.setOpacity(settings.getBrightness());

        tipsShowed.setText(""+stats.tipsShowed);
        tipsShowed2.setText(""+stats.tipsShowed);
        correctLetters.setText(""+stats.correctLetters);
        correctWords.setText(""+stats.correctWords);
        incorrectLetters.setText(""+stats.incorrectLetters);
        preferWord.setText(stats.preferWord);
        timesTyped.setText(""+stats.timesTyped);
        wordsTyped.setText(""+stats.wordsTyped);
        lettersTyped.setText(""+stats.lettersTyped);
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
}
