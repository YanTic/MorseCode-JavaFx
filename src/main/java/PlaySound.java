package main.java;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*BACKGROUND MUSIC

Track 01 : Unknown

Track 02 : The cult of a piano: 
    https://www.youtube.com/watch?v=2x9fZxk2Ohk&ab_channel=PianoBarMusicSpecialists-Topic

Track 03 : Mood Music Cafè (n.11): 
    https://www.youtube.com/watch?v=JVoUTvIX2uI&ab_channel=PianoBarMusicSpecialists-Topic

Track 04 : Bend It Blues: 
    https://www.youtube.com/watch?v=ToC-bx3jObY&ab_channel=HarmonicaInstrumentals-Topic

Track 05: It's been a long, long time: 
    https://www.youtube.com/watch?v=I3hjZNt8dbw&ab_channel=ELPALANCA

*/

public class PlaySound {
    private static File musicPath;
    private static Clip clip;

    public static void playSounds(String soundLocation){
        try{
            musicPath = new File(soundLocation);
                if(musicPath.exists()){
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                    clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                }
                else System.out.println("We can't found the file");
        }catch(Exception e){
            System.out.println("Error with the file");
        }
    }

    public static void stopSounds(){
        if(clip != null)
            clip.stop();
    }

}
