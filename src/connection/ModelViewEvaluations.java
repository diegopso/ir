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
public class ModelViewEvaluations {
    public ArrayList<Integer> aValues, bValues;
    public Integer aUser_id, bUser_id;

    public ModelViewEvaluations() {
        this.aValues = new ArrayList<Integer>();
        this.bValues = new ArrayList<Integer>();
        this.aUser_id = 0;
        this.bUser_id = 0;
    }

    public ModelViewEvaluations(ArrayList<Integer> aValues, ArrayList<Integer> bValues, Integer aUser_id, Integer bUser_id) {
        this.aValues = aValues;
        this.bValues = bValues;
        this.aUser_id = aUser_id;
        this.bUser_id = bUser_id;
    }
    
    public static ModelViewEvaluations get_evaluations_lists(Integer aUser_id, Integer bUser_id){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            ArrayList<Integer> aValues = new ArrayList<Integer>(), bValues = new ArrayList<Integer>();
            
            ResultSet rs = db.exec("SELECT e1.value AS aValue, e2.value AS bValue FROM evaluations e1 INNER JOIN evaluations e2 ON e1.object_type = 'content' AND e2.object_type = 'content' AND e1.object_id = e2.object_id WHERE e1.user_id = "+ aUser_id +" AND e2.user_id = "+ bUser_id +";");
            
            if(rs == null) return new ModelViewEvaluations();            
            
            while (rs.next()) {
                aValues.add(rs.getInt("aValue"));
                bValues.add(rs.getInt("bValue"));
            }
            
            db.close();
            return new ModelViewEvaluations(aValues, bValues, aUser_id, bUser_id);
        } catch (SQLException ex) {
            //be safe
            return new ModelViewEvaluations();
        }
    }
}
