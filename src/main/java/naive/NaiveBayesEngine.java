package naive;

import javafx.util.Pair;
import naive.exceptions.NaiveBayesException;
import naive.kernels.Kernel;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class NaiveBayesEngine<T extends Enum> {

    private Map<String, Map<Enum, Integer>> dataSet;

    private Map<T, Integer> classifierSizes;

    private Class<T> classifierType;

    private DataSet dataset;

    private Kernel kernel;

    public NaiveBayesEngine(DataSet dataset) {
        this.dataSet = dataset.getDataSet();
        this.classifierSizes = dataset.getClassifierSizes();
        this.classifierType = dataset.getClassifier();
        this.dataset = dataset;
    }

    public NaiveBayesEngine with(Kernel kernel) {
        if (this.kernel != null) {
            throw new NaiveBayesException("Only one kernel is allowed !");
        }
        this.kernel = kernel;
        return this;
    }

    /**
     * Examine sentence word after word with probabilities.
     *
     * @param sentence
     * @return predicted T
     */
    public Enum predict(String sentence) {
        if (sentence.isEmpty()) {
            throw new NaiveBayesException("Sentence cannot be empty ");
        }
        if (kernel == null) {
            throw new NaiveBayesException("Kernel is null ");
        }

        sentence = dataset.preprocess(sentence);

        List<String> listOfTokens =
                Arrays.stream(sentence.split(" "))
                        .filter(word -> dataSet.containsKey(word))
                        .collect(Collectors.toList());

        if (listOfTokens.size() == 0) {
            throw new NaiveBayesException("None of words were recognised ");
        }

        List<Pair<String,Map<Enum,Double>>> wordsWithClassifierProbabilities = calculateWords(listOfTokens);

        Map<Enum, Double> results = new HashMap<>();

        for (Object object : EnumSet.allOf(classifierType)) {
            Enum classifier = (Enum)object;
            results.put(
                    classifier,
                    wordsWithClassifierProbabilities.stream()
                        .map(entry -> entry.getValue().get(classifier))
                        .mapToDouble(d->d)
                        .sum());
        }

//        double allProbabilities = results.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();
//        results.forEach((k,v)-> System.out.println(k+" "+(v*100/allProbabilities)));

        return results.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
    }


    private List<Pair<String, Map<Enum, Double>>> calculateWords(List<String> words) {
        return words.stream().map(word -> new Pair<>(word, calculateSingleWord(word))).collect(Collectors
                .toList());

    }

    private Map<Enum, Double> calculateSingleWord(String word) {
        Map<Enum, Integer> occurences = dataSet.get(word);

        double probabilitySummarized = occurences.entrySet().stream()
                .map((entry -> (double) entry.getValue() / classifierSizes.get(entry.getKey())))
                .mapToDouble(d -> d)
                .sum();

        Map<Enum, Double> probabilities = new HashMap<>();

        occurences.forEach((k, v) -> probabilities.put(k,
                kernel.predict(((double) v / classifierSizes.get(k)) / probabilitySummarized)));

        return probabilities;
    }

}
