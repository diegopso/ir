package ir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class WordCount {
    public static Map<String, Integer> count(ArrayList<String> terms){
        Map<String, Integer> freq = new HashMap<String, Integer>();
        
        for(String term : terms){
            if(freq.containsKey(term)){
                freq.put(term, freq.get(term) + 1);
            }else{
                freq.put(term, 1);
            }
        }
        
        return freq;
    }
}
