package ir;

import connection.ModelContent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import statistics.Statistics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Diego
 */
public class Content {
    private String text;
    private ArrayList<String> terms;
    private ArrayList<Integer> frequency;

    public Content() { }

    @Override
    public Content clone(){
        ArrayList<String> terms = new ArrayList<String>(this.terms);
        ArrayList<Integer> frequency = new ArrayList<Integer>(this.frequency);
        
        return new Content(terms, frequency);
    }

    public ArrayList<Integer> getFrequency() {
        return frequency;
    }

    public ArrayList<String> getTerms() {
        return terms;
    }

    public Content(String text) {
        this.text = text.toLowerCase();
        
        terms = BrazilianStemmer.stem(BrazilianStopWord.clean(BrazilianWordBreaker.breakIt(this.text)));
        
        Map<String, Integer> mapTerms = WordCount.count(terms);
        frequency = new ArrayList<>(mapTerms.values());
        terms = new ArrayList<>(mapTerms.keySet());
    }
    
    public Content(ArrayList<String> terms, ArrayList<Integer> frequency){
        this.terms = terms;
        this.frequency = frequency;
    }
    
    public void put(String term, int freq){
        terms.add(term);
        frequency.add(freq);
    }
    
    public Map<String, Integer> instance(){
        Map<String, Integer> freq = new HashMap<String, Integer>(terms.size());
        
        for(int i = 0; i < frequency.size(); i++){
            freq.put(terms.get(i), frequency.get(i));
        }
        
        return freq;
    }

    public String getText() {
        return text;
    }
    
    public Term get(int index){
        //be safe
        if(index >= terms.size())
            return new Term("", 0);
        
        return new Term(terms.get(index), frequency.get(index));
    }
    
    public Term get(String term){
        if(terms.contains(term)){
            int index = terms.indexOf(term);
            return new Term(term, frequency.get(index));
        }
        
        return new Term(term, 0);
    }
    
    public static double contentSimilarity(Content alpha, Content beta){
        Content A = alpha.clone();
        Content B = beta.clone();
        
        B.terms.removeAll(A.terms);
        A.terms.addAll(B.terms);
        
        Integer[] aFreq = new Integer[A.terms.size()];
        Integer[] bFreq = new Integer[A.terms.size()];
        
        for (int i = 0; i < aFreq.length; i++) {
            aFreq[i] = alpha.get(A.terms.get(i)).frequency;
            bFreq[i] = beta.get(A.terms.get(i)).frequency;
        }
        
        return Statistics.cossine_similarity(aFreq, bFreq);
    }
    
    protected class Term{
        public String term;
        public int frequency;

        public Term(String term, int frequency) {
            this.term = term;
            this.frequency = frequency;
        }

        public Term() { }
    }
}
