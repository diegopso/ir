/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tsweetselements;

import connection.ModelReputation;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class Reputation {
    public static Double get_reputation(Integer user_id){
        ArrayList<Double> values = new ModelReputation(user_id).values;
        
        int size = values.size();
        
        if(size == 0)
            return 0.0;
        
        Double[] values_avg = new Double[size];
        values.toArray(values_avg);
        
        return statistics.Statistics.average(values_avg);
    }
}
