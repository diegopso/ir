/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tsweetselements;

import connection.ModelUnidirectTrust;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class Inference {
    public static Double infer_trust_between(Integer source_id, Integer sink_id){
        Double maturity = MaturityLevel.get_level(sink_id);
        Double opinion = OpinionCorrelation.correlation_between(source_id, sink_id);
        Double reputation = Reputation.get_reputation(sink_id);
        Double trust_transivity = TrustTransitivity.trust_between(source_id, sink_id);
        
        Double[] values = new Double[]{
            maturity,
            opinion,
            reputation,
            trust_transivity
        };
        
        Double[] weights = new Double[4];
        int count_nan = 0;
        
        for (int i = 0; i < 4; i++) {
            if(Double.isNaN(values[i])){
                weights[i] = 0.5; //verificar melhor isso...
                values[i] = 0.0;
                count_nan++;
            }
            else
                weights[i] = 1.0;
        }
        
        if(count_nan == 4)
            return Double.NaN;
        
        return statistics.Statistics.weighted_average(values, weights);
    }
    
    public static void trust_map(){
        ArrayList<ModelUnidirectTrust> relationships = ModelUnidirectTrust.get_relationships_to_inference();
        
        for (ModelUnidirectTrust model : relationships) {
            Double trust = infer_trust_between(model.source_id, model.sink_id);
            System.out.println(model.source_id + " => " + model.sink_id + " = " + trust);
        }
    }
}
