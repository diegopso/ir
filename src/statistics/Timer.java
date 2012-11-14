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
	public static long start, delay;
	public static int matrix_size, evaluations_size, contents_size;
	
	public static void start(){
		matrix_size = ModelViewTrustRelationships.get_matrix_size();
		evaluations_size = ModelEvaluation.get_evaluations_size();
		contents_size = ModelContent.get_contents_size();
		start = System.currentTimeMillis();
	}
	
	public static void stop(){
		delay = System.currentTimeMillis();
	}
	
	public static String log(){
		return matrix_size + ";" + evaluations_size + ";" + contents_size + ";" + (delay - start);
	}
}
