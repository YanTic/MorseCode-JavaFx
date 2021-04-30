package main.java;

public class Stats {
    protected String preferWord;
    protected int tipsShowed, correctLetters, correctWords, incorrectLetters;
    protected int wordsTyped, lettersTyped, timesTyped;
    protected char letterMostTyped;
    
    public Stats(){
        preferWord = "";
        tipsShowed = correctLetters = correctWords = incorrectLetters = 0; 
        timesTyped = wordsTyped = lettersTyped = 0;
        letterMostTyped = 'a';
    }

}
