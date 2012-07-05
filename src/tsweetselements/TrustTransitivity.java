package tsweetselements;

import connection.*;
import java.util.ArrayList;
import wbsn.Network;
import wbsn.Path;

/**
 *
 * @author Diego
 */
public class TrustTransitivity {
    public static double trust_between(Integer source_id, Integer sink_id){
        ModelViewTrustRelationships relationships = new ModelViewTrustRelationships();
        Network net = new Network(relationships.relationship_matrix);
        net.mapPaths(source_id, sink_id);
        
        ArrayList<Path> paths = net.paths;
        double sum = 0.0;
        for (Path path : paths) {
            sum += trust_by_path(path, relationships.trust_info);
        }
        
        return sum / paths.size();
    }
    
    protected static double trust_by_path(Path path, ArrayList<ModelTrust> trust_info){
        Integer[] nodes = new Integer[path.nodes.size()];
        path.nodes.toArray(nodes);
        
        ModelTrust[] trust = new ModelTrust[trust_info.size()];
        trust_info.toArray(trust);
        
        double t = 1;
        for (int i = 0; i < nodes.length - 1; i++) {
            int source_id = nodes[i];
            int sink_id = nodes[i + 1];
            
            for (int j = 0; j < trust.length; j++) {
                if(trust[j].sink_id == sink_id && trust[j].source_id == source_id){
                    t *= ((double)trust[j].trust_source_sink / 10);
                }else if(trust[j].sink_id == source_id && trust[j].source_id == sink_id){
                    t *= ((double)trust[j].trust_sink_source / 10);
                }
            }
        }
        
        return t;
    }
}
