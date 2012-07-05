package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ModelViewTrustRelationships {
    public boolean[][] relationship_matrix;
    public ArrayList<ModelTrust> trust_info;

    public ModelViewTrustRelationships() {
        this.factory();
    }
    
    public void factory(){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            
            ResultSet rs = db.exec("SELECT user_source_id, user_sink_id, trust_source_sink, trust_sink_source FROM user_relationships WHERE trust_sink_source IS NOT NULL OR trust_source_sink IS NOT NULL;");
            
            ResultSet s = db.exec("SELECT MAX(id) AS max FROM users;");
            s.next();
            Integer size = s.getInt("max");
            relationship_matrix = new boolean[size + 1][size + 1];
            
            trust_info = new ArrayList<ModelTrust>();            
            
            while (rs.next()) {
                int source_id = rs.getInt("user_source_id");
                int sink_id = rs.getInt("user_sink_id");
                
                relationship_matrix[source_id][sink_id] = rs.getObject("trust_source_sink") != null;
                relationship_matrix[sink_id][source_id] = rs.getObject("trust_sink_source") != null;
                
                trust_info.add(new ModelTrust(source_id, sink_id, rs.getInt("trust_source_sink"), rs.getInt("trust_sink_source")));
            }
            
            db.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
