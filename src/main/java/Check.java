package main.java;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class Check<FXMLDocumentController> {
    FXMLDocumentController controller ;
    Timer timer;

    public Check(FXMLDocumentController controller){
        this.controller = controller;

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                if(controller != null){
                    System.out.println("Checking Translation. . .");                    
                    
                //This method is from JavaFx, when you try to use 'Threads' with something from
                //the controllers or methods that 'JavaFx' is involved. If i don't use Platform.runLater;
                //the program don't execute the method checkBttEvent() and show exceptions
                //This saved my whole life
                    Platform.runLater(()->{
                        ((MorseController) controller).checkBttEvent(null);
                    });
                        
                }

//                    ((MorseController) controller).run();
//                    ((MorseController) controller).checkBttEvent(null);

            }        
        };
        
        timer = new Timer();
        timer.schedule(task, 6000);
    }

    public void updateTimer(){
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                if(controller != null){
                    System.out.println("Checking a NEW Translation. . .");   
                    Platform.runLater(()->{
                        ((MorseController) controller).checkBttEvent(null);
                    });
                }
            }        
        };
        timer.cancel();
        timer = new Timer();
        timer.schedule(task, 6000);
    }

    public void stopTimer(){
        timer.cancel();
        timer.purge();
    }
}
