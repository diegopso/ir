/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import connection.ModelViewTrustRelationships;
import connection.MySqlConnect;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;

/**
 *
 * @author Diego
 */
public class DataBaseInjector {
	private static String root = "/home/user/Documentos/ir/data/";
	
	public static void save_infered_values() {
		try {
                    MySqlConnect db = new MySqlConnect();
                    db.connect();
                    db.executeSqlFile(root + "inference.txt");
                    db.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
