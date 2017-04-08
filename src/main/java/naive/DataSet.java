package naive;

import naive.preprocessors.Preprocessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by Maciej Rudnicki on 01/02/2017.
 */
public class DataSet<T extends Enum> {

    private Map<String, Map<Enum, Integer>> dataSet = new HashMap<>();

    private Map<Enum, Integer> classifierSizes = new HashMap<>();

    private Class<T> classifier;

    private List<Preprocessor> preprocessors;

    public DataSet(DatasetBuilder builder) {

        classifierSizes = builder.classifierSizesBuilder;
        classifier = builder.classifierBuilder;
        preprocessors = builder.preprocessorsBuilder;
    }

    public void train(Map<URL, Enum> trainingSet) {

        trainingSet.forEach((k, v) -> addWords(processURL(k), v));
    }



    public Map<String, Map<Enum, Integer>> getDataSet() {
        return dataSet;
    }

    public Map<Enum, Integer> getClassifierSizes() {
        return classifierSizes;
    }

    public Class<T> getClassifier() {
        return classifier;
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

    public String preprocess(String sentence){
        for(Preprocessor p : preprocessors){
            sentence = p.process(sentence);
        }
        return sentence;
    }

    private Collection<String> processURL(URL url){
        String sentence = readSentence(url);

        sentence = preprocess(sentence);

        return Arrays.stream(sentence.split(" ")).map(String::trim).collect(toList());
    }

    private void addWords(Collection<String> words, final Enum classifier) {
        words.forEach(word -> addSampleWord(word, classifier));
    }

    private void addSampleWord(String word, Enum classifier) {
        if (dataSet.containsKey(word)) {
            update(word, classifier);
        } else {
            createNew(word, classifier);
        }
    }

    private void update(String word, Enum classifier) {
        Map<Enum, Integer> element = dataSet.get(word);

        int oldValue = element.get(classifier);
        int newValue = oldValue + 1;

        element.replace(classifier, newValue);
        updateClassifiersSize(classifier);
    }

    private void createNew(String word, Enum classifier) {

        Map<Enum, Integer> map = (Map<Enum, Integer>) EnumSet.allOf(classifier.getDeclaringClass())
                .stream().collect(Collectors.toMap(c->c, c->0));

        dataSet.put(word, map);
        update(word, classifier);
    }

    private void updateClassifiersSize(Enum classifier) {
        int oldValue = classifierSizes.get(classifier);
        int newValue = oldValue + 1;

        classifierSizes.put(classifier, newValue);
    }

    public static class DatasetBuilder{

        private Map<Enum, Integer> classifierSizesBuilder;

        private Class classifierBuilder;

        private List<Preprocessor> preprocessorsBuilder = new ArrayList<>();

        public DatasetBuilder(Class enumClass) {
            this.classifierSizesBuilder = (Map<Enum, Integer>) EnumSet.allOf(enumClass).stream()
                    .collect(Collectors.toMap(enumType -> enumType, enumType->0));
            this.classifierBuilder = enumClass;
        }

        public DatasetBuilder with(Preprocessor preprocessor){
            preprocessorsBuilder.add(preprocessor);
            return this;
        }
        public DataSet build(){
            return new DataSet(this);
        }
    }
}
