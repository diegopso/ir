/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import ir.Content;
import java.io.BufferedReader;
import java.io.FileReader;
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
    
    private static ArrayList<ModelContent> contents = new ArrayList<ModelContent>();

    public static ArrayList<ModelContent> getContents() {
        return contents;
    }
    
    public static ArrayList<ModelContent> getContents(int user_id) {
        ArrayList<ModelContent> contents = new ArrayList<ModelContent>();
        
        for (ModelContent content : ModelContent.contents) {
            if(content.user_id == user_id)
                contents.add(content);
        }
        
        return contents;
    }

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
    
    public static void factory(){
        try {
            BufferedReader d = new BufferedReader(new FileReader("data/contents.txt"));
            String line;
            String[] brokenLine;
                
            while ((line = d.readLine()) != null) {
                brokenLine = line.split(";");
                
                contents.add(new ModelContent(Integer.parseInt(brokenLine[0]), Integer.parseInt(brokenLine[1]), brokenLine[2]));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void destroy(){
        contents = null;
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
