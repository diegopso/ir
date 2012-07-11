/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ModelEvaluation {
    public Integer reviwer_id, author_id, object_id, value;
    
    private static ArrayList<ModelEvaluation> evaluations = new ArrayList<ModelEvaluation>();

    public static ArrayList<ModelEvaluation> getEvaluations() {
        return evaluations;
    }

    public static ArrayList<ModelEvaluation> get_evaluations_for_user(int user_id) {
        ArrayList<ModelEvaluation> evaluations = new ArrayList<ModelEvaluation>();
        
        for (ModelEvaluation evaluation : ModelEvaluation.evaluations) {
            if(evaluation.author_id == user_id)
                evaluations.add(evaluation);
        }
        
        return evaluations;
    }
    
    public static ArrayList<ModelEvaluation> get_evaluations_by_user(int user_id) {
        ArrayList<ModelEvaluation> evaluations = new ArrayList<ModelEvaluation>();
        
        for (ModelEvaluation evaluation : ModelEvaluation.evaluations) {
            if(evaluation.reviwer_id == user_id)
                evaluations.add(evaluation);
        }
        
        return evaluations;
    }
    
    public ModelEvaluation() {
    }

    public ModelEvaluation(Integer reviwer_id, Integer author_id, Integer object_id, Integer value) {
        this.reviwer_id = reviwer_id;
        this.author_id = author_id;
        this.object_id = object_id;
        this.value = value;
    }
    
    public ModelContent get_content(){
        return ModelContent.get(object_id);
    }
    
    public static void factory(){
        try {
            BufferedReader d = new BufferedReader(new FileReader("data/contents.txt"));
            String line;
            String[] brokenLine;
                
            while ((line = d.readLine()) != null) {
                brokenLine = line.split(";");
                
                evaluations.add(new ModelEvaluation(Integer.parseInt(brokenLine[0]), Integer.parseInt(brokenLine[1]), Integer.parseInt(brokenLine[2]), Integer.parseInt(brokenLine[3])));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void destroy(){
        evaluations = null;
    }
}
