package informationretrieval;

import tsweetselements.OpinionCorrelation;
import tsweetselements.TrustTransitivity;
import connection.*;
import extractors.DataBaseExtrator;
import ir.*;
import statistics.*;
import wbsn.*;
import java.util.ArrayList;
import java.util.Map;
import tsweetselements.*;

/**
 *
 * @author Diego
 */
public class InformationRetrieval {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //importar os dados do banco para os arquivos texto
        //DataBaseExtrator.import_trust_network();
        //DataBaseExtrator.import_contents();
        //DataBaseExtrator.import_evaluations();
        
        //teste de cada uma das metricas
        //test_reputation();
        //test_centroid_similarity();
        //test_evaluation_correlation();
        //test_trust_inference();
        //test_maturity_level();
        
        //teste geral entre dois usuários
        test_inference_between();
    }
    
    /**
     * Testa a inferência de confiança entre dois nós, teste final entre dois usuários
     */
    public static void test_inference_between(){
        ModelViewTrustRelationships.factory();
        ModelEvaluation.factory();
        ModelContent.factory();
        System.out.println(Inference.infer_trust_between(5, 6));
        ModelViewTrustRelationships.destroy();
        ModelEvaluation.destroy();
        ModelContent.destroy();
    }
    
    /**
     * Testa a analise de reputacao
     */
    public static void test_reputation(){
        ModelViewTrustRelationships.factory();
        System.out.println(Reputation.get_reputation(11));
        ModelViewTrustRelationships.destroy();
    }
    
    /**
     * Testa a analise do nivel de maturidade
     */
    public static void test_maturity_level(){
        ModelEvaluation.factory();
        System.out.println(MaturityLevel.get_level(5));
        ModelEvaluation.destroy();
    }
    
    /**
     * testa a inferencia de confianca entre dois usuários
     */
    public static void test_trust_inference(){
        ModelViewTrustRelationships.factory();
        System.out.println(TrustTransitivity.trust_between(8, 11));
        ModelViewTrustRelationships.destroy();
    }
    
    public static void test_search_network(){
        Network net = new Network(ModelViewTrustRelationships.getRelationship_matrix());
        net.mapPaths(8, 11);
        ArrayList<Path> paths = net.paths;
        
        for (Path path : paths) {
            System.out.println("____________");
            Statistics.Log(path.nodes);
        }
    }
    
    /**
     * Testa a analise de correlacao entre os usuários com base nas informações de avaliacoes
     */
    public static void test_evaluation_correlation(){
        ModelEvaluation.factory();
        System.out.println(OpinionCorrelation.correlation_between(8, 11));
        ModelEvaluation.destroy();
    }
    
    /**
     * Testa a analise de similaridade dos perfis de conhecimento dos usuários.
     */
    private static void test_centroid_similarity(){
        ModelContent.factory();
        ArrayList<ModelContent> aContents = ModelContent.getContents(5);
        ArrayList<ModelContent> bContents = ModelContent.getContents(6);
        ModelContent.destroy();
        
        ArrayList<Content> alpha = ModelContent.toContents(aContents);
        ArrayList<Content> beta = ModelContent.toContents(bContents);
        
        System.out.println(Content.contentSimilarity(Centroid.getCentroid(alpha), Centroid.getCentroid(beta)));
    }
    
    /**
     * Testa a distribuicao de frequencia dos termos de um texto.
     */
    private static void test_count_frequency(){
        Map <String, Integer> map = new Content("Um Banco de Dados Relacional é um banco de dados que segue o Modelo Relacional. Um Banco de Dados Relacional é um conceito abstrato que define maneiras de armazenar, manipular e recuperar dados estruturados unicamente na forma de tabelas, construindo um banco de dados. O termo é aplicado aos próprios dados, quando organizados dessa forma, ou a um Sistema Gerenciador de Banco de Dados Relacional (SGBDR) – do inglês Relational database management system (RDBMS) – um programa de computador que implementa a abstração.").instance();
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String term = entry.getKey();
            Integer freq = entry.getValue();
            
            System.out.println(term + " => " + freq);
        }
    }
    
    /**
     * Testa o pre-processamento dos textos, com um texto padrão.
     * @param text 
     */
    private static void test(){
        test("Um Banco de Dados Relacional é um banco de dados que segue o Modelo Relacional. Um Banco de Dados Relacional é um conceito abstrato que define maneiras de armazenar, manipular e recuperar dados estruturados unicamente na forma de tabelas, construindo um banco de dados. O termo é aplicado aos próprios dados, quando organizados dessa forma, ou a um Sistema Gerenciador de Banco de Dados Relacional (SGBDR) – do inglês Relational database management system (RDBMS) – um programa de computador que implementa a abstração.");
    }
    
    /**
     * Testa o pre-processamento dos textos.
     * @param text 
     */
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
