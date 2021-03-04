package main.java.morseCode;

import java.lang.reflect.Field;

public class MorseLanguage {
    int counterLetters = 0;
    String  a = ".-",
            b = "-...",
            c = "-.-.",
            d = "-..",
            e = ".",
            f = "..-.",
            g = "--.",
            h = "....",
            i = "..",
            j = ".---",
            k = "-.-",
            l = ".-..",
            m = "--",
            n = "-.",
            o = "---",
            p = ".--.",
            q = "--.-",
            r = ".-.",
            s = "...",
            t = "-",
            u = "..-",
            v = "...-",
            w = ".--",
            x = "-..-",
            y = "-.--",
            z = "--..";

    public String translate(String letter){
        String morse = "Error";
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for(int i=0; i<fields.length; i++){
            //The object is created because there stores the value of the field (field = variable);
            //So, field = namesVariables & object = valueVariables;
                Object object = fields[i].get(this);
            //Equals compare the names. Example: letter is " a" and f[i].getName is " a" (With spaces);
                if(fields[i].getName().equals(letter)){
                    morse = (String) object;
//                    System.out.println(object); 
//                    System.out.println("WORKS");
                    break;
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        
        return morse;
    }

    public int getCounterLetters(){
        return counterLetters;
    }

    public void setCounterLetters(int counterLetters){
        this.counterLetters = counterLetters;
    }
}
