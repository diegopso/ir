package informationretrieval;

import tsweetselements.OpinionCorrelation;
import tsweetselements.TrustTransitivity;
import connection.*;
import io.DataBaseExtractor;
import io.DataBaseInjector;
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
		//DataBaseExtractor.import_data();
		ModelViewTrustRelationships.factory();
		ModelEvaluation.factory();
		ModelContent.factory();

		//assertion(5, 10);
		//assertion(5, 7);
		Inference.network_inference();

		ModelViewTrustRelationships.destroy();
		ModelEvaluation.destroy();
		ModelContent.destroy();
		
		DataBaseInjector.save_infered_values();
	}

	public static void assertion(int source_id, int sink_id) {
		double ti = test_trust_inference(source_id, sink_id);
		double cb = test_correlation_between(source_id, sink_id);
		double ml = test_maturity_level(sink_id);
		double r = test_reputation(sink_id);
		double i = test_inference_between(source_id, sink_id);

		System.out.println("Transitividade: " + ti + " Correlação: " + cb + " Nivel de Maturidade: " + ml + " Reputação: " + r + " TOTAL: " + i + " ");
	}

	/**
	 * Testa a inferência de confiança entre dois nós, teste final entre dois
	 * usuários
	 */
	public static Double test_inference_between(int source_id, int sink_id) {
		return Inference.infer_trust_between(source_id, sink_id);
	}

	/**
	 * Testa a analise de reputacao
	 */
	public static Double test_reputation(int sink_id) {
		return Reputation.get_reputation(sink_id);
	}

	/**
	 * Testa a analise do nivel de maturidade
	 */
	public static double test_maturity_level(int sink_id) {
		return MaturityLevel.get_level(sink_id);
	}

	/**
	 * testa a inferencia de confianca entre dois usuários
	 */
	public static double test_trust_inference(int source_id, int sink_id) {
		return TrustTransitivity.trust_between(source_id, sink_id);
	}

	/**
	 * Testa a inferência de confiança entre dois nós, teste final entre dois
	 * usuários
	 */
	public static double test_correlation_between(int source_id, int sink_id) {
		return OpinionCorrelation.correlation_between(source_id, sink_id);
	}

	public static void test_search_network(int source_id, int sink_id) {
		Network net = new Network(ModelViewTrustRelationships.getRelationship_matrix());
		net.mapPaths(8, 11);
		ArrayList<Path> paths = net.paths;

		for (Path path : paths) {
			System.out.println("____________");
			Statistics.Log(path.nodes);
		}
	}

	/**
	 * Testa a analise de correlacao entre os usuários com base nas informações
	 * de avaliacoes
	 */
	public static void test_evaluation_correlation(int source_id, int sink_id) {
		System.out.println(OpinionCorrelation.evaluation_similarity(source_id, sink_id));
	}

	/**
	 * Testa a analise de similaridade dos perfis de conhecimento dos usuários.
	 */
	private static void test_centroid_similarity(int source_id, int sink_id) {
		System.out.println(OpinionCorrelation.knowledge_profile_similarity(source_id, sink_id));
	}

	/**
	 * Testa a distribuicao de frequencia dos termos de um texto.
	 */
	private static void test_count_frequency() {
		Map<String, Integer> map = new Content("Um Banco de Dados Relacional é um banco de dados que segue o Modelo Relacional. Um Banco de Dados Relacional é um conceito abstrato que define maneiras de armazenar, manipular e recuperar dados estruturados unicamente na forma de tabelas, construindo um banco de dados. O termo é aplicado aos próprios dados, quando organizados dessa forma, ou a um Sistema Gerenciador de Banco de Dados Relacional (SGBDR) – do inglês Relational database management system (RDBMS) – um programa de computador que implementa a abstração.").instance();

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			String term = entry.getKey();
			Integer freq = entry.getValue();

			System.out.println(term + " => " + freq);
		}
	}

	/**
	 * Testa o pre-processamento dos textos, com um texto padrão.
	 *
	 * @param text
	 */
	private static void test() {
		test("Um Banco de Dados Relacional é um banco de dados que segue o Modelo Relacional. Um Banco de Dados Relacional é um conceito abstrato que define maneiras de armazenar, manipular e recuperar dados estruturados unicamente na forma de tabelas, construindo um banco de dados. O termo é aplicado aos próprios dados, quando organizados dessa forma, ou a um Sistema Gerenciador de Banco de Dados Relacional (SGBDR) – do inglês Relational database management system (RDBMS) – um programa de computador que implementa a abstração.");
	}

	/**
	 * Testa o pre-processamento dos textos.
	 *
	 * @param text
	 */
	private static void test(String text) {
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
		for (String word : tokenWords) {
			System.out.println(word);
		}
	}
}
