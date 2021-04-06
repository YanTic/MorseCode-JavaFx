package main.java;

import main.java.morseCode.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    Check<Object> check;
    int lettersWrite;
//    Timer timer;
    Timer timer2;
    boolean isRunning = false;

/* If use extends Thread: Process1 thread1 = new Procces1();
   If use implements Runnable: Thread thread2 = new Thread(new Process2()) */

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        doFadeTransition(morsePane);            
        runWords();
        tip = new Tip<>(this);
        check = new Check<>(this);
    }

    @FXML
	void dotEvent() {
        morseText.setText(morseText.getText() + ".");
        PlaySound.playSounds("src/resources/sounds/dot.wav");

        //When the user don't type anything, the program shows a tip.
        //In this case if press dot, the timer stop and restart its count;
        tip.stopTimer();

        /* if(morseText.getText().length() > 1){
            stopCheck();
            startCheck();
        }
        else
            startCheck(); */

/*         if(isRunning == true){
            stopCheck();
            startCheck();
        }else
            startCheck(); */


        check.stopTimer();
        check.updateTimer();





//        checkLetter();
//      The tipPane only shows once when the user is typing the word in morse.
//      So, the tip only disappear if the word is correct;
        if(tipPane.isVisible() == false)
            tip.updateTimer();
    }

    @FXML
    void dashEvent() {
        morseText.setText(morseText.getText() + "-");
        PlaySound.playSounds("src/resources/sounds/dash.wav");
        tip.stopTimer();
//        checkLetter();
        /* if(morseText.getText().length() > 1){
            stopCheck();
            startCheck();
        }
        else
            startCheck(); */

/*         if(isRunning == true){
            stopCheck();
            startCheck();
        }else
            startCheck(); */


        check.stopTimer();
        check.updateTimer();



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

    public void runWords() {
        morseLanguage.setCounterLetters(0);
        morseText.clear();
        lettersWrite = 0;
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
        checkBttEvent(null);
    }

    @FXML
    void checkBttEvent(ActionEvent event) {
        String morse;
        int i = morseLanguage.getCounterLetters();

        letter = ""+textLabel.charAt(i);
        morse = morseLanguage.translate(letter);

        if(morse.equals(morseText.getText())){
            try{
                if(tipPane.isVisible()){
                    showTip();
                }

                System.out.println(""+morseText.getText()+" is: "+morse+"  GOOD JOB!");
                morseLanguage.setCounterLetters(++i);
                i = morseLanguage.getCounterLetters();
                morseText.clear();
                lettersWrite = 0;
                letter = ""+textLabel.charAt(i);
                letterLabel.setText(letter.toUpperCase());

            }catch(Exception e){
                //if(morseLanguage.getCounterLetters() > textLabel.length());
                System.out.println("Correct Word");                
                runWords();
            }
        }
        else{
            System.out.println(""+morseText.getText()+" is not: "+morse+"  Try again");  
            morseText.clear();
            lettersWrite = 0;
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

    /* public void checkLetter(){
        //Always when the user types something, check the translate every 3-4 seconds constantly
        boolean isNew = false;
        lettersWrite++;
        timer = new Timer();
        System.out.println("morseText: "+morseText.getText().length());
        System.out.println("lettersWrite: "+lettersWrite);

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                checkBttEvent(null);
            }    
        };

        timer.schedule(task, 3500);

        if(lettersWrite == morseText.getText().length()){
//            timer.cancel();
            System.out.println("Hey this task is cancel, creating one more....");
            timer.purge();
            timer = new Timer();
            timer.schedule(task, 3500);
        }

    } */

// TRASH CODE   -----------------
    public void startCheck(){
        TimerTask task = new TimerTask(){
            @Override
            public void run() {                
                checkBttEvent(null);
                isRunning = false;
            }    
        };
        Timer timer2 = new Timer();
        timer2.schedule(task, 3500);
        isRunning = true;
    }

    public void stopCheck(){
//        if(timer2 != null){
            timer2.cancel();
            timer2.purge();
        //}
    }

}