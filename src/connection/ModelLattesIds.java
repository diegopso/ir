/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego
 */
public class ModelLattesIds {
	public int profile_id;
	public String lattes_id;

	public ModelLattesIds() {
	}

	public ModelLattesIds(int profile_id, String lattes_id) {
		this.profile_id = profile_id;
		this.lattes_id = lattes_id;
	}
	
	public static List<ModelLattesIds> find_all(){
        try {
            MySqlConnect db = new MySqlConnect();
			List<ModelLattesIds> results = new ArrayList<ModelLattesIds>();
            db.connect();
			
            ResultSet rs = db.exec("SELECT id, lattes_id FROM profiles WHERE lattes_id IS NOT NULL;");
            
            if(rs == null) return results;
			
			while (rs.next()) {
                results.add(new ModelLattesIds(rs.getInt("id"), rs.getString("lattes_id")));
            }
            
            db.close();
			
			return results;
        } catch (SQLException ex) {
            //be safe
            return new ArrayList<ModelLattesIds>();
        }
    }
}
