package main.java;

import main.java.morseCode.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MorseController implements Initializable {
    @FXML private AnchorPane morsePane;
    @FXML private AnchorPane tipPane = new AnchorPane();
    @FXML private JFXButton returnButton;
    @FXML private JFXButton dashButton;
    @FXML private JFXButton dotButton;
    @FXML private JFXButton helpButton;
    @FXML private Button RunButton;
    @FXML private Button checkButton;
    @FXML private ImageView tipImage;
    @FXML private ScrollPane helpScrollPane = new ScrollPane();
    @FXML private TextField morseText = new TextField();
    @FXML private Label wordLabel = new Label();
    @FXML private Label letterLabel = new Label();
    MorseLanguage morseLanguage = new MorseLanguage();
    Words word = new Words();
    String textLabel, letter, letterToMorse;
    Tip<Object> tip;

/* If use extends Thread: Process1 thread1 = new Procces1();
   If use implements Runnable: Thread thread2 = new Thread(new Process2()) */

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        doFadeTransition(morsePane);            
        RunBttEvent(null);
        tip = new Tip<>(this);
    }

    @FXML
	void dotEvent() {
        morseText.setText(morseText.getText() + ".");
        PlaySound.playSounds("src/resources/sounds/dot.wav");

        //When the user don't type anything, the program shows a tip.
        //In this case if press dot, the timer stop and restart its count;
        tip.stopTimer();

//      The tipPane only shows once when the user is typing the word in morse.
//      So, the tip only disappear if the word is checked (Later this have to change for: when the
//      user type the correct tranlation, because checked means correct or incorrect);
        if(tipPane.isVisible() == false)
            tip.updateTimer();
    }

    @FXML
    void dashEvent() {
        morseText.setText(morseText.getText() + "-");
        PlaySound.playSounds("src/resources/sounds/dash.wav");
        tip.stopTimer();
        if(tipPane.isVisible() == false)
            tip.updateTimer();
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

        if(tipPane.isVisible()){
            showTip();
        }

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

    public void showTip(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1.5));
        transition.setNode(tipPane);

        System.out.println("Hi this works");

        if(tipPane.isVisible()){
            transition.setFromX(0);
            transition.setToX(-tipPane.getPrefWidth());
            transition.play();

            transition.setOnFinished(evnt ->{
                tipPane.setVisible(false);
            });
        }
        else{   
            Image image = new Image("resources/images/Tips/"+letter+".jpg");
            tipImage.setImage(image);         
            transition.setFromX(-tipPane.getPrefWidth());
            transition.setToX(0);
            transition.play();
            tipPane.setVisible(true);
        }
    }

}