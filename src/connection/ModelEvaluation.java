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
public class ModelEvaluation {
    public Integer user_id, object_id, value;

    public ModelEvaluation() {
    }

    public ModelEvaluation(Integer user_id, Integer object_id, Integer value) {
        this.user_id = user_id;
        this.object_id = object_id;
        this.value = value;
    }
    
    public ModelContent get_content(){
        return ModelContent.get(object_id);
    }
    
    public static ArrayList<ModelEvaluation> get_evaluations_by_user(Integer user_id){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            ArrayList<ModelEvaluation> results = new ArrayList<ModelEvaluation>();
            
            ResultSet rs = db.exec("SELECT object_id, value FROM evaluations WHERE object_type = 'content' AND user_id = " + user_id + ";");
            
            if(rs == null) return results;            
            
            while (rs.next()) {
                results.add(new ModelEvaluation(user_id, rs.getInt("object_id"), rs.getInt("value")));
            }
            
            db.close();
            return results;
        } catch (SQLException ex) {
            //be safe
            return new ArrayList<ModelEvaluation>();
        }
    }
    
    public static ArrayList<ModelEvaluation> get_evaluations_by_content(Integer content_id){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            ArrayList<ModelEvaluation> results = new ArrayList<ModelEvaluation>();
            
            ResultSet rs = db.exec("SELECT user_id, value FROM evaluations WHERE object_type = 'content' AND object_id = " + content_id + ";");
            
            if(rs == null) return results;            
            
            while (rs.next()) {
                results.add(new ModelEvaluation(rs.getInt("user_id"), content_id, rs.getInt("value")));
            }
            
            db.close();
            return results;
        } catch (SQLException ex) {
            //be safe
            return new ArrayList<ModelEvaluation>();
        }
    }
}
