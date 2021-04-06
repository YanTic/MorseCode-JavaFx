package main.java;

import java.util.Timer;
import java.util.TimerTask;

// This is a type of thread, meanwhile another function in the code is working,
// this code is working at the sime time 

public class Tip<FXMLDocumentController> {
    FXMLDocumentController controller ;
    Timer timer;

    //I can't call the method showTip(); because the tipPane doesn't appear
    //So i have to get the controller to show the tip-->(the pane) from that controller;
    public Tip(FXMLDocumentController controller){
        this.controller = controller;

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                if(controller != null){
                    System.out.println("Hi this is a Tip");                    
                    ((MorseController) controller).showTip();
                }
            }        
        };
        
        timer = new Timer();
        timer.schedule(task, 8000);
    }

/*     public Tip(){
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                if(controller != null){
                    
                }
            }        
        };
        
        timer = new Timer();
        timer.schedule(task, 8000);
    } */

    public void updateTimer(){
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                if(controller != null){
                    System.out.println("Hi NEW NEW tip");
                    ((MorseController) controller).showTip();
                }
            }        
        };
        timer.cancel();
        timer = new Timer();
        timer.schedule(task, 8000);
    }

    public void stopTimer(){
        timer.cancel();
        timer.purge();
    }

}
