/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tsweetselements;

import connection.ModelReputation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class Reputation {
    private static Map<Integer, Double> reputations = new HashMap<Integer, Double>();
    
    public static Double get_reputation(Integer user_id){
        if(reputations.containsKey(user_id)){
            return reputations.get(user_id);
        }
        
        double reputation;
        int size;
        Double[] values_avg;
        ArrayList<Double> values;
        
        values = ModelReputation.getValues(user_id);
        size = values.size();
        
        values_avg = new Double[size];
        values.toArray(values_avg);
        
        reputation = statistics.Statistics.average(values_avg);
        reputations.put(user_id, reputation);
        return reputation;
    }
}
