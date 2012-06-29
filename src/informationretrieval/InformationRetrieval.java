/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package informationretrieval;

import ir.BrazilianStemmer;
import ir.Content;
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
