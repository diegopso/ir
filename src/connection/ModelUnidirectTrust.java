package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ModelUnidirectTrust {
    public Integer source_id, sink_id, trust;

    public ModelUnidirectTrust() {
    }

    public ModelUnidirectTrust(Integer source_id, Integer sink_id, Integer trust) {
        this.source_id = source_id;
        this.sink_id = sink_id;
        this.trust = trust;
    }
    
    public static ArrayList<ModelUnidirectTrust> get_relationships_to_inference(){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            ArrayList<ModelUnidirectTrust> results = new ArrayList<ModelUnidirectTrust>();
            
            ResultSet rs = db.exec("SELECT u1.id AS source_id, u2.id AS sink_id, IF(u1.id = ur.user_source_id, ur.trust_source_sink, ur.trust_sink_source) AS trust FROM users u1 JOIN users u2 ON true LEFT JOIN user_relationships ur ON (user_source_id = u1.id AND user_sink_id = u2.id) OR (user_source_id = u2.id AND user_sink_id = u1.id) WHERE IF(u1.id = ur.user_source_id, ur.trust_source_sink, ur.trust_sink_source) IS NULL AND u1.id <> u2.id");
            
            if(rs == null) return results;            
            
            while (rs.next()) {
                results.add(new ModelUnidirectTrust(rs.getInt("source_id"), rs.getInt("sink_id"), rs.getInt("trust")));
            }
            
            db.close();
            return results;
        } catch (SQLException ex) {
            //be safe
            return new ArrayList<ModelUnidirectTrust>();
        }
    }
}
