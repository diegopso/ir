/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tsweetselements;

/**
 *
 * @author Diego
 */
public class Inference {
    public static Double infer_trust_between(Integer source_id, Integer sink_id){
        return statistics.Statistics.weighted_average(new Double[]{
            MaturityLevel.get_level(sink_id),
            OpinionCorrelation.correlation_between(source_id, sink_id),
            Reputation.get_reputation(sink_id),
            TrustTransitivity.trust_between(source_id, sink_id)
        }, new Double[]{
            1.0,
            1.0,
            1.0,
            1.0
        });
    }
}
