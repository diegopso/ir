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
            terms.retainAll(t);
            t.addAll(terms);
            terms = t;
        }
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
}


