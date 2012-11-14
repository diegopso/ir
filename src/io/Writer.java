/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import tsweetselements.Inference;

/**
 *
 * @author Diego
 */
public class Writer {

	public static void write_file(String content, String file) {
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}

		FileWriter x;

		try {
			x = new FileWriter(file, true);

			x.write(content);
			x.close();
		} catch (IOException ex) {
			Logger.getLogger(Inference.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void log(String content, String file) {
		try {
			FileOutputStream f = new FileOutputStream(file, true);
			PrintStream p = new PrintStream(f);

			p.println(content);

			f.close();
			p.close();
		} catch (IOException ex) {
			Logger.getLogger(Inference.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
