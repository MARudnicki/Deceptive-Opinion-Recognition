package naive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 01/02/2017.
 */
public class Dataset<T extends Enum> {

    private Map<String, Map<Enum, Integer>> dataSet = new HashMap<>();

    private Map<Enum, Integer> classifierSizes = new HashMap<>();

    private Class<T> classifier;

    public Dataset(Class<T> enumClass) {

        classifierSizes = (Map<Enum, Integer>) EnumSet.allOf(enumClass).stream()
                .collect(Collectors.toMap(enumType -> enumType, enumType->0));
        classifier = enumClass;
    }

    public void train(Map<URL, Enum> trainingSet) {
        trainingSet.forEach((k, v) -> addWords(readLines(k), v));
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
}
