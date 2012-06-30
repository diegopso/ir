package ir;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class Centroid {
    public static Content getCentroid(ArrayList<Content> contents){
        ArrayList<String> terms = new ArrayList<String>();
        
        for (Content c : contents) {
            ArrayList<String> t = c.getTerms();
            t.removeAll(terms);
            terms.addAll(t);
        }
        
        ArrayList<Integer> frequency = new ArrayList<Integer>(terms.size());
        
        for (String t : terms) {
            int freq = 0, maxFreq = 1;
            for (Content c : contents) {
                int f = c.get(t).frequency;
                if(f > maxFreq)
                    maxFreq = f;
                freq += f;
            }
            frequency.add(Math.round((float) freq / maxFreq));
        }
        
        return new Content(terms, frequency);
    }
}


