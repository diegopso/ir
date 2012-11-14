/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Diego
 */
public class BrazilianWordBreaker {
    public static final String eliminate = "[^\\s\u0030-\u0039\u0041-\u005A\u0061-\u007A\u00C0-\u00C3\u00C7-\u00CA\u00CC-\u00CE\u00D2-\u00D5\u00D9-\u00DC\u00E0-\u00E3\u00E7-\u00EA\u00EC-\u00EE\u00F2-\u00F5\u00F9-\u00FC^]";
    
    protected static String clean(String text){
        Pattern pattern = Pattern.compile(eliminate);

        Matcher matcher = pattern.matcher(text);
        text = matcher.replaceAll("");
        return text;
    }
    
    public static ArrayList<String> breakIt(String text){
        ArrayList<String> wordList = new ArrayList<>();
        String[] words = clean(text).split(" ");
        Collections.addAll(wordList, words);
        return wordList;
    }
}
