package connection;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class ModelViewTrustRelationships {
    private static boolean[][] relationship_matrix = null;
    private static int[][] trust_info = null;
	private static int matrix_size;
	
	public static int get_matrix_size(){
		return ModelViewTrustRelationships.matrix_size;
	}

    public static int[][] getTrust_info() {
        return trust_info;
    }

    public static boolean[][] getRelationship_matrix() {
        return relationship_matrix;
    }
    
    public static void destroy(){
        trust_info = null;
        relationship_matrix = null;
    }
    
    public static void factory(){
        try {
            BufferedReader d = new BufferedReader(new FileReader("data/trust_matrix.txt"));
            String line = null;
            int row = 0, size = 0, i = 0, value = 0;
            String[] brokenLine = null;
            
            //be faster
            if((line = d.readLine()) != null){
                brokenLine = line.split(";");
                
                size = brokenLine.length;
				ModelViewTrustRelationships.matrix_size = size;
                relationship_matrix = new boolean[size][size];
                trust_info = new int[size][size];
                row++;
                
                while ((line = d.readLine()) != null) {
                    brokenLine = line.split(";");

                    for(i = 0; i < size; i++){
                        value = Integer.parseInt(brokenLine[i]);
                        relationship_matrix[row][i] = value != -1;
                        trust_info[row][i] = value;
                    }
                    row++;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
