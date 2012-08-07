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
        ArrayList<ModelEvaluation> aEvaluations = ModelEvaluation.get_evaluations_by_user(aUser_id);
        ArrayList<ModelEvaluation> bEvaluations = ModelEvaluation.get_evaluations_by_user(bUser_id);
        
        ModelViewEvaluations model = new ModelViewEvaluations();
        int size = aEvaluations.size();
        
        model.aUser_id = aUser_id;
        model.bUser_id = bUser_id;
        
        for (ModelEvaluation a : aEvaluations) {
            for (ModelEvaluation b : bEvaluations) {
                if(a.object_id == b.object_id){
                    model.aValues.add(a.value);
                    model.bValues.add(b.value);
                }
            }
        }
        
        return model;
    }
}
