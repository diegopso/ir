/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package informationretrieval;

import connection.*;
import ir.*;
import statistics.*;
import statistics.Network.Path;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class InformationRetrieval {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        test_search_network();
    }
    
    public static void test_search_network(){
        ModelViewTrustRelashionships trust = new ModelViewTrustRelashionships();
        
        Network net = new Network(trust.relationship_matrix);
        net.mapPaths(8, 11);
        ArrayList<Path> paths = net.paths;
        
        for (Path path : paths) {
            System.out.println("____________");
            Statistics.Log(path.nodes);
        }
    }
    
    public static void test_evaluation_correlation(){
        ModelViewEvaluations values = ModelViewEvaluations.get_evaluations_lists(5, 6);
        
        Integer[] A = new Integer[values.aValues.size()];
        Integer[] B = new Integer[values.bValues.size()];
        
        values.aValues.toArray(A);
        values.bValues.toArray(B);
        
        System.out.println(Statistics.pearson_coeficient(A, B));
    }
    
    private static void test_centroid_similarity(){
        ArrayList<ModelContent> aContents = ModelContent.get_contents_by_user(5);
        ArrayList<ModelContent> bContents = ModelContent.get_contents_by_user(6);
        
        ArrayList<Content> alpha = ModelContent.toContents(aContents);
        ArrayList<Content> beta = ModelContent.toContents(bContents);
        
        System.out.println(Content.contentSimilarity(Centroid.getCentroid(alpha), Centroid.getCentroid(beta)));
    }
    
    private static void test_count_frequency(){
        Map <String, Integer> map = new Content("Um Banco de Dados Relacional é um banco de dados que segue o Modelo Relacional. Um Banco de Dados Relacional é um conceito abstrato que define maneiras de armazenar, manipular e recuperar dados estruturados unicamente na forma de tabelas, construindo um banco de dados. O termo é aplicado aos próprios dados, quando organizados dessa forma, ou a um Sistema Gerenciador de Banco de Dados Relacional (SGBDR) – do inglês Relational database management system (RDBMS) – um programa de computador que implementa a abstração.").instance();
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String term = entry.getKey();
            Integer freq = entry.getValue();
            
            System.out.println(term + " => " + freq);
        }
    }
    
    private static void test(){
        test("Um Banco de Dados Relacional é um banco de dados que segue o Modelo Relacional. Um Banco de Dados Relacional é um conceito abstrato que define maneiras de armazenar, manipular e recuperar dados estruturados unicamente na forma de tabelas, construindo um banco de dados. O termo é aplicado aos próprios dados, quando organizados dessa forma, ou a um Sistema Gerenciador de Banco de Dados Relacional (SGBDR) – do inglês Relational database management system (RDBMS) – um programa de computador que implementa a abstração.");
    }
    
    private static void test(String text){
        System.out.println("_____BreakWords_____");
        ArrayList<String> breakWords = ir.BrazilianWordBreaker.breakIt(text);
        for (String word : breakWords) {
            System.out.println(word);
        }
        
        System.out.println("_____StopWordsClean_____");
        ir.BrazilianStopWord.factory();
        ArrayList<String> meaningWords = ir.BrazilianStopWord.clean(breakWords);
        for (String word : meaningWords) {
            System.out.println(word);
        }
        
        System.out.println("_____Stemming_____");
        ArrayList<String> tokenWords = ir.BrazilianStemmer.stem(meaningWords);
        for(String word : tokenWords){
            System.out.println(word);
        }
    }
}
