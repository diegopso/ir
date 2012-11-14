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
        Network net = new Network(ModelViewTrustRelationships.getRelationship_matrix());
        net.mapPaths(source_id, sink_id);
        
        ArrayList<Path> paths = net.paths;
        double sum = 0.0;
        for (Path path : paths) {
            sum += trust_by_path(path);
        }
        
        return sum / paths.size();
    }
    
    protected static double trust_by_path(Path path){
        int source_id, sink_id, i;
        Integer[] nodes = new Integer[path.nodes.size()];
        double t = 1.0;
        int[][] trust = ModelViewTrustRelationships.getTrust_info();
        
        path.nodes.toArray(nodes);
        
        for (i = 0; i < nodes.length - 1; i++) {
            source_id = nodes[i];
            sink_id = nodes[i + 1];
            t *= trust[source_id][sink_id];
        }
        
        return t / Math.pow(10, nodes.length - 1);
    }
}
