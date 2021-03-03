package main.java;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlaySound {
    public static void playSounds(String soundLocation){
        try{
            File musicPath = new File(soundLocation);
                if(musicPath.exists()){
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                }
                else{
                    System.out.println("We can't found the file");
                }
        }catch(Exception e){
            System.out.println("Error with the file");
        }
    }
}
