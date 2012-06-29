package statistics;

/**
 *
 * @author Diego
 */
public class Statistics {
    
    /**
     * Retorna o somatório dos valores do vetor passado como parametro
     * @param values vetor com os valores a serem somados
     * @return soma dos valores no vetor
     */
    public static Double sum(Double[] values){
        Double sum = 0.0;
        
        for (int i = 0; i < values.length; i++) {
            sum += (Double) values[i];
        }
        
        return sum;
    }
    
    /**
     * Retorna o somatório do quadrado dos valores passados como parametro.
     * @param values valores a serem somados
     * @return soma dos quadrados dos termos passados como parametro
     */
    public static Double square_sum(Double[] values){
        Double sum = 0.0;
        
        for (int i = 0; i < values.length; i++) {
            sum += ((Double)values[i]) * ((Double)values[i]);
        }
        
        return sum;
    }
    
    /**
     * Retorna o somatório dos quadrados da diferença dos valores passados como primeiro parametro pelo segundo valor passado como parametro
     * @param values valores a serem somados
     * @param avg valor de centro
     * @return a soma dos quadrados das diferenças dos valores passados como parametro pelo valor de centro
     */
    public static Double square_sum(Double[] values, Double avg){
        Double sum = 0.0;
        
        for (int i = 0; i < values.length; i++) {
            sum += Math.pow(values[i] - avg, 2);
        }
        
        return sum;
    }
    
    /**
     * Retonra a média aritmética dos valores passados como parametro.
     * @param values valores sobre os quais deve-se calcular a média
     * @return a média dos valores passados como parametro
     */
    public static Double average(Double[] values){
        Double sum = sum(values);
        return sum / values.length;
    }
    
    /**
     * Retorna a média ponderada dos valores passados como parametro
     * @param values parcelas a serem somadas
     * @param weights peso das parcelas a serem somadas
     * @return média ponderada dos valores passados como parametro
     */
    public static Double weighted_average(Double[] values, Double[] weights){
        Double sum = 0.0;
        
        for (int i = 0; i < values.length; i++) {
            sum += ((Double)values[i]) * ((Double)weights[i]);
        }
        
        return sum / sum(weights);
    }
    
    /**
     * Retorna o valor de correlação entre os dois conjuntos de variaveis passados como parametro
     * @param aValues primeiro conjunto de variaveis
     * @param bValues segundo conjunto de variaveis
     * @return o valor de correlação entre os dois conjuntos de variaveis
     */
    public static Double pearson_coeficient(Double[] aValues, Double[] bValues){
        Double a = average(aValues);
        Double b = average(bValues);
        
        Double sum = 0.0;
        
        for (int i = 0; i < aValues.length; i++) {
            sum += Math.abs(aValues[i] - a) * Math.abs(bValues[i] - b);
        }
        
        return sum / (Math.sqrt(square_sum(aValues, a))*Math.sqrt(square_sum(bValues, b)));
    }
    
    /**
     * Retorna o desvio padrao dos valores passados como parameto
     * @param values os valores sobre os quais deve-se calcular o desvio padrao
     * @return o valor do desvio padrao
     */
    public static Double standard_deviation(Double[] values){
        Double avg = average(values);
        Double sum = square_sum(values, avg);
        
        return Math.sqrt(sum / (values.length - 1));
    }
    
    /**
     * Retorna o valor do cosseno entre dois vetores representados pelos valores passados como parametro
     * @param aValues o primeiros conjunto de valores
     * @param bValues o segundo conjunto de valores
     * @return o valor do cosseno
     */
    public static Double cossine_similarity(Double[] aValues, Double[] bValues){
        Double sum = 0.0;
        
        for (int i = 0; i < aValues.length; i++) {
            sum += aValues[i]*bValues[i];
        }
        
        return sum / (Math.sqrt(square_sum(aValues)) * Math.sqrt(square_sum(bValues)));
    }
}
