package main.java;

import main.java.morseCode.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MorseController implements Initializable {
    @FXML private StackPane stackPane;
    @FXML private AnchorPane morsePane;
    @FXML private AnchorPane tipPane = new AnchorPane();
    @FXML private JFXButton returnButton;
    @FXML private JFXButton dashButton;
    @FXML private JFXButton dotButton;
    @FXML private JFXButton helpButton;
    @FXML private JFXButton prefWordBtt;
    @FXML private ImageView tipImage;
    @FXML private ProgressBar line = new ProgressBar();
    @FXML private ScrollPane helpScrollPane = new ScrollPane();
    @FXML private TextField morseText = new TextField();
    @FXML private Label wordLabel = new Label();
    @FXML private Label letterLabel = new Label();
    MorseLanguage morseLanguage = new MorseLanguage();
    Words word = new Words();
    String textLabel, letter, letterToMorse;
    Tip<Object> tip;
    Check<Object> check;
    Settings settings;
    boolean music, musicEffects, doAssistance;
    Stats stats;

/* If use extends Thread: Process1 thread1 = new Procces1();
   If use implements Runnable: Thread thread2 = new Thread(new Process2()) */

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        morseText.setFocusTraversable(false); //When i started the program the handle of the buttons doesn't work
        morseText.setFont(new Font("Open Sans Extranegrita", 180)); //DON'T PIXEL MY DOTS/DASHES
        PlaySound.playSounds("src/resources/sounds/dot.wav");

        Platform.runLater(()->{
            settings.getMusicBg().stopMediaPlayer();        
            if(settings.getMusic())
                settings.getMusicBg().setSongTrack(1);
        });
        
        System.out.println("Music: "+music + "\nEffects: "+musicEffects + "\nAssistance: "+doAssistance);


//        doFadeTransition(morsePane);   
        doFadeTransition(stackPane);   
        runWords();
        tip = new Tip<>(this);
        check = new Check<>(this);
    }

    public void setValues(Settings settings, Stats stats){
        this.settings = settings;
        this.stats = stats;
        morsePane.setOpacity(settings.getBrightness());
    }

    @FXML
	void dotEvent() {
        morseText.setText(morseText.getText() + ".");

    /* Now i don't have to use ThreadSleep for the program don't plays a sound over other
    It's just stop the previously sound and play another one */

        PlaySound.stopSounds();
        if(settings.getMusicEffects()){
            PlaySound.playSounds("src/resources/sounds/dot.wav");
        }

        //When the user don't type anything, the program shows a tip.
        //In this case if press dot, the timer stop and restart its count;
        tip.stopTimer();

        check.stopTimer();
        check.updateTimer();

        //Test

        //Stats
        stats.timesTyped++;

//      The tipPane only shows once when the user is typing the word in morse.
//      So, the tip only disappear if the word is correct;
        if(tipPane.isVisible() == false)
            tip.updateTimer();
    }

    @FXML
    void dashEvent() {
        morseText.setText(morseText.getText() + "-");
        
        PlaySound.stopSounds();
        if(settings.getMusicEffects()){
            PlaySound.playSounds("src/resources/sounds/dash.wav");
        }

        tip.stopTimer();
        check.stopTimer();
        check.updateTimer();
        stats.timesTyped++;

        if(tipPane.isVisible() == false)
            tip.updateTimer();
    }

    @FXML
    void returnBttEvent(ActionEvent event) throws IOException {
        // This change the scene, return to the main scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/MainView.fxml"));
        Parent root = loader.load();

        //Well, you can think that, this is not necessary, but when i press the return
        //button, the settings rebbot, so i have to send the values again
        MainController mainCt = loader.getController();
        mainCt.setValues(settings, stats);

        Scene scene = returnButton.getScene();
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

    @FXML
    void prefWordEvent(ActionEvent event) {
//        prefWordBtt.setStyle("-fx-graphic: url(\"resources/images/star.png\");");
        stats.preferWord = textLabel;
    }

    public void runWords() {
        morseLanguage.setCounterLetters(0);
        morseText.clear();
        textLabel = word.words[(int)(Math.random()*336)];
//        wordLabel.setText(textLabel);
//      I want to separate the words like a spacing in word
        String wordLabelText = "";
        System.out.println("\n|------Word: "+textLabel+" ------|");
        System.out.println("|------letters: "+textLabel.length()+" ------|");
        System.out.print("|------");
            for(int i=0; i<textLabel.length(); i++){
                wordLabelText += " "+textLabel.charAt(i);
                System.out.print("  "+textLabel.charAt(i));
            }
        System.out.print(" ------| \n\n");

        wordLabel.setText(wordLabelText);
        letter = ""+textLabel.charAt(0);
        letterLabel.setText(letter.toUpperCase());

        //Restart line progress
        lowLim = higLim; 
        higLim = 0;
        progressTask();
        task.playFromStart();
        lowLim = -0.13; 

//        System.out.println(""+letter+" to morse is: "+morseLanguage.translate(letter)); 
//        checkTranslation(null);
    }

    public void checkTranslation(ActionEvent event) {
        String morse;
        int i = morseLanguage.getCounterLetters();

        letter = ""+textLabel.charAt(i);
        morse = morseLanguage.translate(letter);
        stats.lettersTyped++;

        if(morse.equals(morseText.getText())){
            try{
                if(tipPane.isVisible()){
                    showTip();
                }

                System.out.println(""+morseText.getText()+" is: "+morse+"  GOOD JOB!");
                morseLanguage.setCounterLetters(++i);
                i = morseLanguage.getCounterLetters();
                letter = ""+textLabel.charAt(i);
                stats.correctLetters++;
                stats.corLtByWord++;

                //When the transition stop, change letter 
                doScaleTransition(letterLabel);  

                //Move the line progress
                lowLim += 0.13;
                higLim += 0.13;
                progressTask();
                task.playFromStart();

            }catch(Exception e){
                //if(morseLanguage.getCounterLetters() > textLabel.length());
                System.out.println("Correct Word");                
                stats.correctWords++;
                stats.wordsTyped++;

                stats.correctLettersByWord.add(stats.corLtByWord);
                stats.incorrectLettersByWord.add(stats.incorLtByWord);
                stats.tipsByWord.add(stats.tipByWord);

                stats.corLtByWord = 1;
                stats.incorLtByWord = 0;
                stats.tipByWord = 0;

                runWords();
            }
        }
        else{
            System.out.println(""+morseText.getText()+" is not: "+morse+"  Try again");  
            stats.incorrectLetters++;
            stats.incorLtByWord++;
            morseText.clear();
        } 
    }
    
    @FXML
    void helpBttEvent(ActionEvent event) throws IOException {
        if(helpScrollPane.isVisible()){            
            helpScrollPane.translateYProperty().set(0);
            Timeline timeLine = animationInY(helpScrollPane, morsePane.getHeight());
            timeLine.setOnFinished(e->{helpScrollPane.setVisible(false);});
            timeLine.play();
        }
        else{
            helpScrollPane.translateYProperty().set(morsePane.getHeight());
            helpScrollPane.setVisible(true);
            Timeline timeLine = animationInY(helpScrollPane, 0);            
            timeLine.play(); 
        }
    }

    public Timeline animationInY(Node node, double to){
        Timeline timeLine = new Timeline(
                new KeyFrame(
                    Duration.seconds(1.3), 
                    new KeyValue(node.translateYProperty(), to, Interpolator.EASE_IN)
                )
            );
        return timeLine;
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

    public void doFadeTransition(Node node) {
        FadeTransition transition = new FadeTransition();
        transition.setNode(node);
        transition.setDuration(Duration.seconds(3));
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    public void doScaleTransition(Node node) {
        ScaleTransition transition = new ScaleTransition();
        transition.setNode(node);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(1.35);
        transition.setToY(1.35);
        System.out.println("The scale transition works");
        transition.play();
        transition.setOnFinished(evnt->{
            transition.setDuration(Duration.seconds(0.6));
            transition.setToX(1);
            transition.setToY(1);
            transition.play();
            transition.setOnFinished(evt->{
                Platform.runLater(()->{
                    morseText.clear();                        
                    letterLabel.setText(letter.toUpperCase());
                }); 
            });
        });
    }

    Timeline task;
    double lowLim = -0.13, higLim = 0;
    public void progressTask(){
        task = new Timeline(
            new KeyFrame(
                Duration.ZERO, 
                new KeyValue(line.progressProperty(), lowLim)
            ),
            new KeyFrame(
                Duration.seconds(1), 
                new KeyValue(line.progressProperty(), higLim))
        );
        
    }

    public void showTip(){
        if(settings.getDoAssistance()){
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(1.5));
            transition.setNode(tipPane);
    
            System.out.println("Hi this works");
            stats.tipsShowed++;
            stats.tipByWord++;
    
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

}