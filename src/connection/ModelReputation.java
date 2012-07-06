/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ModelReputation {
    public ArrayList<Double> values;
    public Integer user_id;

    public ModelReputation() {
        values = new ArrayList<Double>();
        user_id = 0;
    }
    
    public ModelReputation(Integer user_id){
        values = new ArrayList<Double>();
        
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            
            ResultSet rs = db.exec("SELECT trust_source_sink AS value FROM user_relationships WHERE (user_sink_id = " + user_id + " AND trust_source_sink IS NOT NULL) UNION SELECT trust_sink_source AS value FROM user_relationships WHERE (user_source_id = " + user_id + " AND trust_sink_source IS NOT NULL);");            
            
            this.user_id = user_id;
            
            while (rs.next()) {
                this.values.add((double) rs.getInt("value") / 10.0);
            }
            
            db.close();
            
        } catch (SQLException ex) {
            //be safe
            
        }
    }
}
