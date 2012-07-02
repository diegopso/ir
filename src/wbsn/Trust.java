package wbsn;

import connection.ModelTrust;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class Trust {
    public static double trust_by_path(Path path, ArrayList<ModelTrust> trust_info){
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
                    t *= trust[j].trust_source_sink;
                }else if(trust[j].sink_id == source_id && trust[j].source_id == sink_id){
                    t *= trust[j].trust_sink_source;
                }
            }
        }
        
        return t;
    }
}
