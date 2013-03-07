/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

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
public class DataBaseExtractor {
    public static void import_data(){
        import_contents();
		import_evaluations();
		import_trust_network();
    }
    
    /**
     * gera um .txt com as linhas contendo reviwer_id;author_id;object_id;value
     */
    public static void import_evaluations(){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            
            ResultSet rs = db.exec("SELECT e.user_id AS reviwer_id, c.user_id AS author_id, e.object_id, e.value FROM evaluations e INNER JOIN contents c ON e.object_id = c.id AND e.object_type = 'content' WHERE e.value IS NOT NULL;");          
            
            File f = new File("data/evaluations.txt");
            if (f.exists()) {
                f.delete();
            }
            
            FileWriter x = new FileWriter("data/evaluations.txt", true);
            String texto = "";
            
            while (rs.next()) {
                texto += rs.getInt("reviwer_id") + ";" + rs.getInt("author_id") + ";" + rs.getInt("object_id") + ";" + rs.getInt("value") + "\n";
            }
            
            x.write(texto);
            x.close();
            
            db.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void import_contents(){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            
            //remover especificidade de tipo = text quando for melhorar
            ResultSet rs = db.exec("SELECT id, user_id, text FROM contents WHERE type = 'text';");        
            
            File f = new File("data/contents.txt");
            if (f.exists()) {
                f.delete();
            }

            FileWriter x = new FileWriter("data/contents.txt", true);
            String texto = "";
            
            while (rs.next()) {
                texto += rs.getInt("id") + ";" + rs.getInt("user_id") + ";" + rs.getString("text").replaceAll("\n", " ") + "\n";
            }
            
            x.write(texto);
            x.close();
            db.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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
