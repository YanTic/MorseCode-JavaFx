package main.java;

import java.util.ArrayList;

public class Stats {
    protected String preferWord;
    protected int tipsShowed, correctLetters, correctWords, incorrectLetters;
    protected int wordsTyped, lettersTyped, timesTyped;
    protected int corLtByWord, incorLtByWord, tipByWord;
//    protected char letterMostTyped;
    protected ArrayList<Integer> correctLettersByWord;
    protected ArrayList<Integer> incorrectLettersByWord;
    protected ArrayList<Integer> tipsByWord;
    
    public Stats(){
        preferWord = "";
//        letterMostTyped = 'a';
        tipsShowed = correctLetters = correctWords = incorrectLetters = 0; 
        timesTyped = wordsTyped = lettersTyped = 0;
        incorLtByWord = tipByWord = 0;
        corLtByWord =  1; 
        correctLettersByWord = new ArrayList<>();
        incorrectLettersByWord = new ArrayList<>();
        tipsByWord = new ArrayList<>();
    }

    //When i test the values, the code doesn't put the correct values, so the values
    //of the corrects letters start in 1 (not in 0). I don't change the values of the
    //tips and the incorrect words because is involved 'threads'. And is almost the
    //same values

    //      Real        Wrong
    //  tip : 2, 1, 2 |  4, 2, 4
    //  cor : 4, 4, 5 |  3, 3, 4
    //  inc : 2, 2, 3 |  3, 2, 3

}
