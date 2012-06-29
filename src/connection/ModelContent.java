/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import ir.Content;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class ModelContent {
    public int user_id;
    public String text = "";
    public String path = "Not yet...";

    public ModelContent() {
    }

    public ModelContent(int user_id, String text) {
        this.user_id = user_id;
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
    
    public static ArrayList<ModelContent> get_contents_by_user(int user_id){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            ArrayList<ModelContent> results = new ArrayList<ModelContent>();
            
            //remover especificidade de tipo = text quando for melhorar
            ResultSet rs = db.exec("SELECT text FROM contents WHERE type = 'text' AND user_id = " + user_id + ";");
            
            if(rs == null) return results;            
            
            while (rs.next()) {            
                results.add(new ModelContent(user_id, rs.getString("text")));
            }
            
            db.close();
            return results;
        } catch (SQLException ex) {
            //be safe
            return new ArrayList<ModelContent>();
        }
    }
    
    public Content toContent(){
        return new Content(this.text);
    }
    
    public static ArrayList<Content> toContents(ArrayList<ModelContent> contents){
        ArrayList<Content> cs = new ArrayList<Content>();
        
        for (ModelContent content : contents) {
            cs.add(content.toContent());
        }
        
        return cs;
    }
}
