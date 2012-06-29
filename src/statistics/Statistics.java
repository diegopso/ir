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
    public static double sum(double[] values){
        double sum = 0.0;
        
        for (int i = 0; i < values.length; i++) {
            sum += (double) values[i];
        }
        
        return sum;
    }
    
    /**
     * Retorna o somatório do quadrado dos valores passados como parametro.
     * @param values valores a serem somados
     * @return soma dos quadrados dos termos passados como parametro
     */
    public static double square_sum(double[] values){
        double sum = 0.0;
        
        for (int i = 0; i < values.length; i++) {
            sum += ((double)values[i]) * ((double)values[i]);
        }
        
        return sum;
    }
    
    /**
     * Retorna o somatório dos quadrados da diferença dos valores passados como primeiro parametro pelo segundo valor passado como parametro
     * @param values valores a serem somados
     * @param avg valor de centro
     * @return a soma dos quadrados das diferenças dos valores passados como parametro pelo valor de centro
     */
    public static double square_sum(double[] values, double avg){
        double sum = 0.0;
        
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
    public static double average(double[] values){
        double sum = sum(values);
        return sum / values.length;
    }
    
    /**
     * Retorna a média ponderada dos valores passados como parametro
     * @param values parcelas a serem somadas
     * @param weights peso das parcelas a serem somadas
     * @return média ponderada dos valores passados como parametro
     */
    public static double weighted_average(double[] values, double[] weights){
        double sum = 0.0;
        
        for (int i = 0; i < values.length; i++) {
            sum += ((double)values[i]) * ((double)weights[i]);
        }
        
        return sum / sum(weights);
    }
    
    /**
     * Retorna o valor de correlação entre os dois conjuntos de variaveis passados como parametro
     * @param aValues primeiro conjunto de variaveis
     * @param bValues segundo conjunto de variaveis
     * @return o valor de correlação entre os dois conjuntos de variaveis
     */
    public static double pearson_coeficient(double[] aValues, double[] bValues){
        double a = average(aValues);
        double b = average(bValues);
        
        double sum = 0.0;
        
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
    public static double standard_deviation(double[] values){
        double avg = average(values);
        double sum = square_sum(values, avg);
        
        return Math.sqrt(sum / (values.length - 1));
    }
}
