package main.java;

import java.util.Timer;
import java.util.TimerTask;

// This is a type of thread, meanwhile another function in the code is working,
// this code is working at the sime time 

public class Tip<FXMLDocumentController> {
//    MorseController tipWindow = new MorseController();
    FXMLDocumentController controller ;
    Timer timer;

    //I can't call the method showTip(); because the tipPane doesn't appear
    //So i have to get the controller to show the tip-->(the pane) from that controller;
    //Actually this method (setController) is not working right now. The constructor does it
    public void setController(FXMLDocumentController controller){
        this.controller = controller;
    }

    public Tip(FXMLDocumentController controller){
        this.controller = controller;

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                if(controller != null){
                    System.out.println("Hi this is a Tip");                    
                    ((MorseController) controller).showTip();
                }

//                System.out.println("Hi this is a Tip");
//                tipWindow.showTip();
//                updateTimer();
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
                    System.out.println("Hi NEW NEW tip");
                    ((MorseController) controller).showTip();
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
