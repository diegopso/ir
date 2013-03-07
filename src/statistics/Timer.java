/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import connection.*;

/**
 *
 * @author Diego
 */
public class Timer {
	public static long start, delay, turn_r;
	public static int matrix_size, evaluations_size, contents_size, turn_i, turn_j;
	public static long[][] turns;
	
	public static void start(){
		matrix_size = ModelViewTrustRelationships.get_matrix_size();
		turns = new long[matrix_size * matrix_size][4];
		turn_i = 0;
		turn_j = 0;
		evaluations_size = ModelEvaluation.get_evaluations_size();
		contents_size = ModelContent.get_contents_size();
		start = System.nanoTime();
		turn_r = start;
	}
	
	public static void turn(){
		long t = System.nanoTime();
		turns[turn_i][turn_j] = t - turn_r;
		turn_r = t;
		if(turn_j == 3){
			turn_j = 0;
			turn_i++;
		}else{
			turn_j++;
		}
	}
	
	public static void stop(){
		delay = System.nanoTime();
		System.nanoTime();
	}
	
	public static String log(){
		String str = "";
		
		for(int i = 0; i < matrix_size * matrix_size; i++){
			str += turns[i][0] + ";" + turns[i][1] + ";" + turns[i][2] + ";" + turns[i][3] + "\n";
		}
		
		return str + "\n" + matrix_size + ";" + evaluations_size + ";" + contents_size + ";" + (delay - start);
	}
}
