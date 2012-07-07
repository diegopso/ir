/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractors;

import connection.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class DataBaseExtrator {
    public static void import_data(){
        
    }
    
    public static void import_trust_network(){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            
            ResultSet rs = db.exec("SELECT user_source_id, user_sink_id, trust_source_sink, trust_sink_source FROM user_relationships WHERE trust_sink_source IS NOT NULL OR trust_source_sink IS NOT NULL;");
            
            ResultSet s = db.exec("SELECT MAX(id) AS max FROM users;");
            s.next();
            
            int size = s.getInt("max");
            boolean[][] relationship_matrix = new boolean[size + 1][size + 1];
            int[][] trust_matrix = new int[size + 1][size + 1];
            
            while (rs.next()) {
                int source_id = rs.getInt("user_source_id");
                int sink_id = rs.getInt("user_sink_id");
                
                relationship_matrix[source_id][sink_id] = rs.getObject("trust_source_sink") != null;
                relationship_matrix[sink_id][source_id] = rs.getObject("trust_sink_source") != null;
                
                trust_matrix[source_id][sink_id] = rs.getInt("trust_source_sink");
                trust_matrix[sink_id][source_id] = rs.getInt("trust_sink_source");
            }
            
            db.close();
            
            File f = new File("data/trust_matrix.txt");
            if(f.exists())
                f.delete();
            
            FileWriter x = new FileWriter("data/trust_matrix.txt", true);
            String texto = "";
            for (int i = 0; i < size + 1; i++) {
                String line = "";
                int j = 0;
                for (; j < size; j++) {
                    if(relationship_matrix[i][j]){
                        line += trust_matrix[i][j] + ";";
                    }else{
                        line += "-1;";
                    }
                }
                
                if(relationship_matrix[i][j]){
                    line += trust_matrix[i][j];
                }else{
                    line += "-1";
                }
                
                texto += line + "\n";
            }
            x.write(texto); // armazena o texto no objeto x, que aponta para o arquivo           
            x.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}