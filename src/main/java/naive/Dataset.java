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

    private Map<String, Map<T, Integer>> dataSet = new HashMap<>();

    private Map<T, Integer> classifierSizes = new HashMap<>();

    private Class<T> classifier;

    public Dataset(Class<T> enumClass) {
        classifierSizes = (Map<T, Integer>) EnumSet.allOf(enumClass).stream()
                .collect(Collectors.toMap(enumType -> enumType, enumType->0));
        classifier = enumClass;
    }

    public void train(Map<URL, T> trainingSet) {
        trainingSet.forEach((k, v) -> addWords(readLines(k), v));
    }

    public Map<String, Map<T, Integer>> getDataSet() {
        return dataSet;
    }

    public Map<T, Integer> getClassifierSizes() {
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

    private void addWords(Collection<String> words, final T classifier) {
        words.forEach(word -> addSampleWord(word, classifier));
    }

    private void addSampleWord(String word, T classifier) {
        if (dataSet.containsKey(word)) {
            update(word, classifier);
        } else {
            createNew(word, classifier);
        }
    }

    private void update(String word, T classifier) {
        Map<T, Integer> element = dataSet.get(word);

        int oldValue = element.get(classifier);
        int newValue = oldValue + 1;

        element.replace(classifier, newValue);
        updateClassifiersSize(classifier);
    }

    private void createNew(String word, T classifier) {

        Map<T, Integer> map = (Map<T, Integer>) EnumSet.allOf(classifier.getDeclaringClass())
                .stream().collect(Collectors.toMap(c->c, c->0));

        dataSet.put(word, map);
        update(word, classifier);
    }

    private void updateClassifiersSize(T classifier) {
        int oldValue = classifierSizes.get(classifier);
        int newValue = oldValue + 1;

        classifierSizes.put(classifier, newValue);
    }
}
