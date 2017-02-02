package naive;

import naive.classifiers.Classifier;
import naive.classifiers.ClassifierMap;

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

/**
 * Created by Maciej Rudnicki on 01/02/2017.
 */
public class Dataset {

    private Map<String, Map<Classifier, Integer>> dataSet = new HashMap<>();

    private Map<Classifier, Integer> classifierSizes = new ClassifierMap();

    public void train(Map<Classifier, URL> trainingSet) {
        trainingSet.forEach((k, v) -> addWords(readLines(v), k));
    }

    public Map<String, Map<Classifier, Integer>> getDataSet() {
        return dataSet;
    }

    public Map<Classifier, Integer> getClassifierSizes() {
        return classifierSizes;
    }



    private static List<String> readLines(URL url) {
        List<String> list;
        try {
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            list = reader
                    .lines()
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Cannot read from url, " + url.getPath());
        }
        return list;
    }

    private void addWords(Collection<String> words, final Classifier classifier) {
        words.forEach(word -> addSampleWord(word, classifier));
    }

    private void addSampleWord(String word, Classifier classifier) {
        if (dataSet.containsKey(word)) {
            update(word, classifier);
        } else {
            createNew(word, classifier);
        }
    }

    private void update(String word, Classifier classifier) {
        Map<Classifier, Integer> element = dataSet.get(word);

        int oldValue = element.get(classifier);
        int newValue = oldValue + 1;

        element.replace(classifier, newValue);
        updateClassifiersSize(classifier);
    }

    private void createNew(String word, Classifier classifier) {
        Map<Classifier, Integer> map = new HashMap<>();

        for (Classifier c : Classifier.values()) {
            map.put(c, 0);
        }

        dataSet.put(word, map);
        update(word, classifier);
    }

    private void updateClassifiersSize(Classifier classifier) {
        int oldValue = classifierSizes.get(classifier);
        int newValue = oldValue + 1;

        classifierSizes.put(classifier, newValue);
    }
}
