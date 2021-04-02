package main.java;

import main.java.morseCode.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MorseController implements Initializable {
    @FXML private AnchorPane morsePane;
    @FXML private JFXButton returnButton;
    @FXML private JFXButton dashButton;
    @FXML private JFXButton dotButton;
    @FXML private JFXButton helpButton;
    @FXML private Button RunButton;
    @FXML private Button checkButton;
    @FXML private ScrollPane helpScrollPane = new ScrollPane();
    @FXML private TextField morseText = new TextField();
    @FXML private Label wordLabel = new Label();
    @FXML private Label letterLabel = new Label();
    MorseLanguage morseLanguage = new MorseLanguage();
    Words word = new Words();
    String textLabel, letter, letterToMorse;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        morsePane.setOpacity(0);
        doFadeTransition(morsePane);            
    }

    @FXML
	void dotEvent() {
        morseText.setText(morseText.getText() + ".");
        PlaySound.playSounds("src/resources/sounds/dot.wav");
    }

    @FXML
    void dashEvent() {
        morseText.setText(morseText.getText() + "-");
        PlaySound.playSounds("src/resources/sounds/dash.wav");
    }

    @FXML
    void returnBttEvent(ActionEvent event) throws IOException {
        // This change the scene, return to the main scene
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MainView.fxml"));
        Stage mainView = (Stage) returnButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/styles/Main.css").toExternalForm());
        mainView.setScene(scene);
    }

    @FXML
    void RunBttEvent(ActionEvent event) {
        morseLanguage.setCounterLetters(0);
        morseText.clear();
        textLabel = word.words[(int)(Math.random()*336)];
        wordLabel.setText(textLabel);
        System.out.println("\n|------Word: "+textLabel+" ------|");
        System.out.println("|------letters: "+textLabel.length()+" ------|");
        System.out.print("|------");
            for(int i=0; i<textLabel.length(); i++){
                System.out.print("  "+textLabel.charAt(i));
            }
        System.out.print(" ------| \n\n");

        letter = ""+textLabel.charAt(0);
        letterLabel.setText(letter.toUpperCase());
//        System.out.println(""+letter+" to morse is: "+morseLanguage.translate(letter)); 
        checkBttEvent(event);
    }

    @FXML
    void checkBttEvent(ActionEvent event) {
        String morse;
        int i = morseLanguage.getCounterLetters();

        letter = ""+textLabel.charAt(i);
        morse = morseLanguage.translate(letter);

        if(morse.equals(morseText.getText())){
            try{
                System.out.println(""+morseText.getText()+" is: "+morse+"  GOOD JOB!");
                morseLanguage.setCounterLetters(++i);
                i = morseLanguage.getCounterLetters();
                morseText.clear();
                letter = ""+textLabel.charAt(i);
                letterLabel.setText(letter.toUpperCase());
            }catch(Exception e){
                //if(morseLanguage.getCounterLetters() > textLabel.length());
                System.out.println("Correct Word");
                RunBttEvent(event);
            }
        }
        else{
            System.out.println(""+morseText.getText()+" is not: "+morse+"  Try again");  
            morseText.clear();
        } 
    }

    
    @FXML
    void helpBttEvent(ActionEvent event) throws IOException {
        if(helpScrollPane.isVisible())
            helpScrollPane.setVisible(false);
        else
            helpScrollPane.setVisible(true);
    }

    @FXML   //This is from AnchorPane (morsePanel I call it);
    void handleOnKeyPressed(KeyEvent e){
        if(e.getText().equals(".")){
            dotEvent();
        }
        if(e.getText().equals("-")){
            dashEvent();
        }
    }

    public static void doFadeTransition(Node node) {
        FadeTransition transition = new FadeTransition();
        transition.setNode(node);
        transition.setDuration(Duration.seconds(3));
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

}

