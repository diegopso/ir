package ir;


import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Diego
 */
public class BrazilianStopWord {
    private static final String stopWordFile = "stopwords.txt";
    private static ArrayList<String> stopWordList = new ArrayList<String>();
    private static boolean isCreated = false;
    
    public static void factory(){
        try{
            FileReader reader = new FileReader(stopWordFile);
            BufferedReader read = new BufferedReader(reader);
            
            String line;            
            while ((line = read.readLine()) != null) {
                stopWordList.add(line);
            }
            
            isCreated = true;
        }catch(Exception e){
            System.out.println("ERROR - Loading stopword file.");
        }
    }
    
    public static ArrayList<String> clean(ArrayList<String> terms){
        //be safe
        if(!isCreated)
            factory();
        
        for (int i = 0; i < terms.size(); i++) {
            if(isInStopList(terms.get(i))){
                terms.remove(i);
                i--;
            }
        }
        return terms;
    }
    
    protected static boolean isInStopList(String term){
        return stopWordList.contains(term.toLowerCase());
    }
    
    public static String[] instance(){
        String[] stopWords = new String[stopWordList.size()];
        stopWords = stopWordList.toArray(stopWords);
        
        return stopWords;
    }
}
