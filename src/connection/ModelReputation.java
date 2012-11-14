package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ModelReputation {
    private static ArrayList<Double> values;

    public static ArrayList<Double> getValues(Integer user_id) {
        factory(user_id);
        return values;
    }
    
    private static void factory(Integer user_id){
        values = new ArrayList<Double>();
        
        boolean[][] relationship_matrix = ModelViewTrustRelationships.getRelationship_matrix();
        int[][] trust_info = ModelViewTrustRelationships.getTrust_info();
        int size = relationship_matrix.length;

        for (int i = 0; i < size; i++) {
            if(relationship_matrix[i][user_id]){
                values.add((double)trust_info[i][user_id] / 10);
            }
        }
    }
}
