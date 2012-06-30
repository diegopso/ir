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
    public Integer user_id, content_id;
    public String text = "";
    public String path = "Not yet...";

    public ModelContent() {
    }

    public ModelContent(Integer content_id, Integer user_id, String text) {
        this.content_id = content_id;
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
            ResultSet rs = db.exec("SELECT id, text FROM contents WHERE type = 'text' AND user_id = " + user_id + ";");
            
            if(rs == null) return results;            
            
            while (rs.next()) {            
                results.add(new ModelContent(rs.getInt("id"), user_id, rs.getString("text")));
            }
            
            db.close();
            return results;
        } catch (SQLException ex) {
            //be safe
            return new ArrayList<ModelContent>();
        }
    }
    
    public ArrayList<ModelEvaluation> get_evaluations(){
        return ModelEvaluation.get_evaluations_by_content(content_id);
    }
    
    public static ModelContent get(Integer content_id){
        try {
            MySqlConnect db = new MySqlConnect();
            db.connect();
            
            //remover especificidade de tipo = text quando for melhorar
            ResultSet rs = db.exec("SELECT user_id, text FROM contents WHERE type = 'text' AND id="+ content_id +";");
            
            if(rs == null) return new ModelContent();
            
            db.close();
            return new ModelContent(content_id, rs.getInt("user_id"), rs.getString("text"));
        } catch (SQLException ex) {
            //be safe
            return new ModelContent();
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
