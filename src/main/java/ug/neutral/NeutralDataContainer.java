package ug.neutral;

import ug.naive.ngrams.NGramsSplitter;
import ug.naive.preprocessors.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NeutralDataContainer<T extends Enum> {

    private Map<String, Map<Enum, Integer>> dataSet = new HashMap<>();

    private Map<Enum, Integer> classifierSizes = new HashMap<>();

    private Class<T> classifier;

    private List<Tokenizer> tokenizers;

    private NGramsSplitter nGramsSplitter;

    public void train(Map<URL, Enum> trainingSet) {

        trainingSet.forEach((k, v) -> addWords(processURL(k), v));
    }

    private void addWords(Collection<String> words, final Enum classifier) {
        words.forEach(word -> process(word, classifier));
    }

    private void process(String word, Enum classifier){

    }


    private Collection<String> processURL(URL url) {
        String sentence = readSentence(url);

        sentence = performTokenization(sentence);

        return performNGramsSplitter(sentence);
    }

    public String performTokenization(String sentence) {
        for (Tokenizer p : tokenizers) {
            sentence = p.process(sentence);
        }
        return sentence;
    }


    public List<String> performNGramsSplitter(String sentence){
        return nGramsSplitter.split(sentence);
    }


    private static String readSentence(URL url) {
        try {
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            return reader
                    .lines()
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .collect(Collectors.joining(" "));

        } catch (IOException e) {
            throw new RuntimeException("Cannot read from url, " + url.getPath());
        }
    }
}
