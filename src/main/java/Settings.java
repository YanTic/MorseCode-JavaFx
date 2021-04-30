package main.java;

public class Settings {
    private boolean music, musicEffects, doAssistance;
    private double brightness;

    public Settings(){
        music = true; 
        musicEffects = true; 
        doAssistance = true;
        brightness = 0.85;
    }

    //  Setters

    public void setMusic(boolean music){
        this.music = music;
    }

    public void setMusicEffects(boolean musicEffects){
        this.musicEffects = musicEffects;
    }

    public void setDoAssistance(boolean doAssistance){
        this.doAssistance = doAssistance;
    }

    public void setBrightness(double brightness){
        this.brightness = brightness;
    }

    // Getters

    public boolean getMusic(){
        return music;
    }

    public boolean getMusicEffects(){
        return musicEffects;
    }

    public boolean getDoAssistance(){
        return doAssistance;
    }
    
    public double getBrightness(){
        return brightness;
    }

}
