package tsweetselements;

import connection.ModelEvaluation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Diego
 */
public class MaturityLevel {
    public static double get_level(Integer user_id){
        return MaturityLevel.get_level(ModelEvaluation.get_evaluations_for_user(user_id));
    }
    
    private static double get_level(ArrayList<ModelEvaluation> evaluations){        
        Double[] values = new Double[evaluations.size()];
        int i = 0;
        
        for (ModelEvaluation model : evaluations) {
            values[i++] = (double)model.value;
        }
        
        return statistics.Statistics.average(values);
    }
    
    private static Map<Double, Double> get_count_and_average(ArrayList<ModelEvaluation> evaluations){
        Map<Integer, ArrayList<Double>> content_evaluations = new HashMap<Integer, ArrayList<Double>>();
        Map<Double, Double> note_map = new HashMap<Double, Double>();
        
        for (ModelEvaluation m : evaluations) {
            if(!content_evaluations.containsKey(m.object_id)){
                content_evaluations.put(m.object_id, new ArrayList<Double>());
            }
            
            content_evaluations.get(m.object_id).add((double) m.value / 10.0);
        }
        
        for (Map.Entry<Integer, ArrayList<Double>> en : content_evaluations.entrySet()) {
            ArrayList<Double> notes = en.getValue();
            Double[] notes_for_avg = new Double[notes.size()];
            notes.toArray(notes_for_avg);
            
            note_map.put((double) notes.size(), statistics.Statistics.average(notes_for_avg));
        }
        
        return note_map;
    }
}
