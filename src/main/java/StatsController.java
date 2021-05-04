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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StatsController implements Initializable{
    @FXML private StackPane stackPane;
    @FXML private AnchorPane generalPane;
    @FXML private JFXButton backBtt;
    @FXML private LineChart<String, Number> lineChart;
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
//        @SuppressWarnings("rawtypes")
        Platform.runLater(()->{
            setLineChartValues();
        });
        /* XYChart.Series series = new XYChart.Series<>();

        
        series.getData().add(new XYChart.Data("1", 23));
        series.getData().add(new XYChart.Data("3", 13));
        series.getData().add(new XYChart.Data("4", 43));
        series.getData().add(new XYChart.Data("5", 33));

        lineChart.getData().addAll(series); */
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

    public void setLineChartValues(){
        //For each = for(Integer i: stats.correctLettersByWord)
        //      Real        Wrong
        //tip : 2, 1, 2 |  4, 2, 4
        //cor : 4, 4, 5 |  3, 3, 4
        //inc : 2, 2, 3 |  3, 2, 3


        XYChart.Series<String, Number> corLettByWord = new XYChart.Series<>();
        corLettByWord.setName("CorLt");
         for(int i=0; i<stats.correctLettersByWord.size(); i++){
            System.out.println(""+i +" "+ stats.correctLettersByWord.get(i));
            corLettByWord.getData().add(new XYChart.Data<>(""+i, stats.correctLettersByWord.get(i)));
        } 
        System.out.println("\n");

        XYChart.Series<String, Number> incorLettByWord = new XYChart.Series<>();
        incorLettByWord.setName("IncLt");
        for(int i=0; i<stats.incorrectLettersByWord.size(); i++){
            System.out.println(""+i +" "+ stats.incorrectLettersByWord.get(i));
            incorLettByWord.getData().add(new XYChart.Data<>(""+i, stats.incorrectLettersByWord.get(i)));
        }
        System.out.println("\n");

        XYChart.Series<String, Number> tipsByWord = new XYChart.Series<>();
        tipsByWord.setName("Tip");
        for(int i=0; i<stats.tipsByWord.size(); i++){
            System.out.println(""+i +" "+ stats.tipsByWord.get(i));
            tipsByWord.getData().add(new XYChart.Data<>(""+i, stats.tipsByWord.get(i)));
        } 


        //FOR TESTES
/*        XYChart.Series<String, Number> corLettByWord = new XYChart.Series<String, Number>();
        corLettByWord.getData().add(new XYChart.Data<String, Number>("Jan", 4));
        corLettByWord.getData().add(new XYChart.Data<String, Number>("Feb", 29));
        corLettByWord.getData().add(new XYChart.Data<String, Number>("Mar", 21));
        corLettByWord.setName("CorLett");

        XYChart.Series tipsByWord = new XYChart.Series<>();
        tipsByWord.getData().add(new XYChart.Data("1", 14));
        tipsByWord.getData().add(new XYChart.Data("2", 4));
        tipsByWord.getData().add(new XYChart.Data("3", 24));

        XYChart.Series incorLettByWord = new XYChart.Series<>();
        incorLettByWord.getData().add(new XYChart.Data("1", 9));
        incorLettByWord.getData().add(new XYChart.Data("2", 19));
        incorLettByWord.getData().add(new XYChart.Data("3", 11)); */

//        series.getData().add(new XYChart.Data("3", 13));
  
        lineChart.getData().addAll(corLettByWord, incorLettByWord, tipsByWord);
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
