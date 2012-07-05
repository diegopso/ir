/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import connection.ModelViewEvaluations;
import statistics.Statistics;

/**
 *
 * @author Diego
 */
public class OpinionCorrelation {
    public static double correlation_between(Integer source_id, Integer sink_id){
        ModelViewEvaluations values = ModelViewEvaluations.get_evaluations_lists(source_id, sink_id);
        
        Integer[] A = new Integer[values.aValues.size()];
        Integer[] B = new Integer[values.bValues.size()];
        
        values.aValues.toArray(A);
        values.bValues.toArray(B);
        
        return Statistics.pearson_coeficient(A, B);
    }
}
