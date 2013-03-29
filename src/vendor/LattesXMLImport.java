/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vendor;

import connection.ModelLattesIds;
import connection.MySqlConnect;
import io.Writer;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Diego
 */
public class LattesXMLImport {

	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	public static List<ModelLattesIds> get_lattes_ids(){
		return ModelLattesIds.find_all();
	}
	
	public static void import_data(){
		List<ModelLattesIds> profiles = get_lattes_ids();
		
		for (ModelLattesIds p : profiles) {
			try {
				String str = "DELETE FROM `lattes_keywords` WHERE `lattes_production_id` IN (SELECT p.id FROM `lattes_productions` p INNER JOIN `lattes_infos` i ON i.id = p.lattes_info_id WHERE i.profile_id = "+ p.profile_id +");\r\n";
				str += "DELETE FROM `lattes_authors` WHERE `lattes_production_id` IN (SELECT p.id FROM `lattes_productions` p INNER JOIN `lattes_infos` i ON i.id = p.lattes_info_id WHERE i.profile_id = "+ p.profile_id +");\r\n";
				str += "DELETE FROM `lattes_productions` WHERE `lattes_info_id` IN (SELECT id FROM `lattes_infos` WHERE profile_id = "+ p.profile_id +");\r\n";
				str += "DELETE FROM `lattes_infos` WHERE profile_id = "+ p.profile_id +";\r\n";
				Writer.log(str + import_file(Writer.IN_DIR + "lattes_files\\" + p.lattes_id + ".xml", p.profile_id), Writer.OUT_DIR + "lattes_data.sql");
			} catch (IOException ex) {
				Logger.getLogger(LattesXMLImport.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static String import_file(String path, int profile_id) throws IOException {
		Path p = Paths.get(path);
		String str = "", bp_statement = "";
		try (Scanner scanner = new Scanner(p, ENCODING.name())) {
			str = scanner.nextLine();
			Pattern pattern = Pattern.compile("<PRODUCAO-BIBLIOGRAFICA>(.*)</PRODUCAO-BIBLIOGRAFICA>");
			Matcher m = pattern.matcher(str);

			if (m.find()) {
				String event_papers = m.group(1);
				bp_statement = import_bibliographic_production(event_papers, profile_id);
			}
		}
		
		return bp_statement;
	}

	public static String import_bibliographic_production(String str, int profile_id) {
		String title = "", type = "", statement = "";
		int year = 0, relevant = 0, len = 0;
		String[] productions = null;
		List<String> keywords = new ArrayList<String>(6);
		List<LattesAuthor> authors = new ArrayList<LattesAuthor>();
		LattesProduction[] prods = null;
		Pattern pattern = Pattern.compile("<([\\w\\-]+) SEQUENCIA-PRODUCAO=\"([\\d]+)\">(.*)</\\1>"),
				p_title = Pattern.compile("TITULO-[\\w\\-]+=\"(.*?)\""),
				p_year = Pattern.compile("ANO-[\\w\\-]+=\"(.*?)\""),
				p_relevant = Pattern.compile("FLAG-RELEVANCIA=\"(.*?)\""),
				p_keyword = Pattern.compile("PALAVRA-CHAVE-([\\d]{1})=\"(.*?)\""),
				p_authors = Pattern.compile("NOME-COMPLETO-DO-AUTOR=\"(.*?)\" NOME-PARA-CITACAO=\"(.*?)\"");
		Matcher m = pattern.matcher(str),
				m2 = null;

		while (m.find()) {
			type = m.group(1);
			productions = m.group(3).split("</([\\w\\-]+)><\\1 SEQUENCIA-PRODUCAO=\"([\\d]+)\">");
			len = productions.length;
			prods = new LattesProduction[len];

			for (int i = 0; i < len; i++) {
				title = "";
				year = relevant = 0;
				keywords = new ArrayList<String>(6);
				authors = new ArrayList<LattesAuthor>();
				
				m2 = p_title.matcher(productions[i]);
				if(m2.find())
					title = m2.group(1);
				
				m2 = p_year.matcher(productions[i]);
				if(m2.find())
					year =  Integer.valueOf(m2.group(1));
				
				m2 = p_relevant.matcher(productions[i]);
				if(m2.find())
					relevant =  m2.group(1).equals("SIM") ? 1 : 0;
				
				m2 = p_keyword.matcher(productions[i]);
				while(m2.find())
					keywords.add(m2.group(2));
				
				m2 = p_authors.matcher(productions[i]);
				while(m2.find())
					authors.add(new LattesAuthor(m2.group(1), m2.group(2)));
				
				prods[i] = new LattesProduction(title, type, year, relevant, "production", productions[i], keywords, authors);
			}
			
			statement += LattesProduction.get_insert_statement(prods, profile_id);
		}
		
		return statement;
	}
}

class LattesInfo{
	public String type, description;
}

class LattesProduction extends LattesInfo {
	public String title, p_type;
	public int year, relevant;
	public List<String> keywords;
	public List<LattesAuthor> authors;
	

	public LattesProduction() {
	}

	public LattesProduction(String title, String p_type, int year, int relevant,
			String type, String description, List<String> keywords, List<LattesAuthor> authors) {
		this.title = title.replace("'", "''");
		this.p_type = p_type;
		this.year = year;
		this.relevant = relevant;
		this.type = type;
		this.description = description.replace("'", "''");;
		this.keywords = keywords;
		this.authors = authors;
	}
	
	public static String get_insert_statement(LattesProduction[] array, int profile_id){
		String str = "START TRANSACTION;\r\n";
		for (LattesProduction l : array) {
			str += "\tINSERT INTO `lattes_infos` (`profile_id`, `type`, `description`) VALUES ("+ profile_id +", '"+ l.type +"', '"+ l.description +"');\r\n";
			str += "\tINSERT INTO `lattes_productions` (`title`, `relevant`, `year`, `type`, `lattes_info_id`) VALUES ('"+l.title+"', "+l.relevant+", "+l.year+", '"+l.p_type+"', (SELECT LAST_INSERT_ID()));\r\n";
			str += "\tSET @p_id := (SELECT LAST_INSERT_ID());\r\n";
			
			for (String kw : l.keywords) {
				if(!kw.equals(""))
					str += "\t\tINSERT INTO `lattes_keywords` (`word`, `lattes_production_id`) VALUES ('"+ kw +"', @p_id);\r\n";
			}
			
			for (LattesAuthor a : l.authors) {
				str += "\t\tINSERT INTO `lattes_authors` (`name`, `citation`, `lattes_production_id`) VALUES ('"+a.name+"', '"+a.citation+"', @p_id);\r\n";
			}
		}
		
		str += "COMMIT;";
		return str;
	}
}

class LattesAuthor{
	public String name, citation;

	public LattesAuthor() {
	}

	public LattesAuthor(String name, String citation) {
		this.name = name;
		this.citation = citation;
	}
}
