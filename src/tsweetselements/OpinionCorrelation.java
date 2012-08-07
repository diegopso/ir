/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tsweetselements;

import connection.ModelContent;
import connection.ModelViewEvaluations;
import ir.Centroid;
import ir.Content;
import java.util.ArrayList;
import statistics.Statistics;

/**
 *
 * @author Diego
 */
public class OpinionCorrelation {
    public static double correlation_between(Integer source_id, Integer sink_id){
        return Statistics.average(new Double[]{
            evaluation_similarity(source_id, sink_id), 
            knowledge_profile_similarity(source_id, sink_id)
        });
    }
    
    public static double evaluation_similarity(Integer source_id, Integer sink_id){
        ModelViewEvaluations values = ModelViewEvaluations.get_evaluations_lists(source_id, sink_id);
        
        int aSize = values.aValues.size();
        
        Integer[] A = new Integer[aSize];
        Integer[] B = new Integer[values.bValues.size()];
        
        values.aValues.toArray(A);
        values.bValues.toArray(B);
        
        return Statistics.pearson_coeficient(A, B);
    }
    
    public static double knowledge_profile_similarity(Integer source_id, Integer sink_id){
        ArrayList<ModelContent> aContents = ModelContent.getContents(source_id);
        ArrayList<ModelContent> bContents = ModelContent.getContents(sink_id);
        
        ArrayList<Content> alpha = ModelContent.toContents(aContents);
        ArrayList<Content> beta = ModelContent.toContents(bContents);
        
        return Content.contentSimilarity(Centroid.getCentroid(alpha), Centroid.getCentroid(beta));
    }
}
