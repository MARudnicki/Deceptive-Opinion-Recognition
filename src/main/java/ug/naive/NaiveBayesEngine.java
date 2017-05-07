package ug.naive;

import com.google.common.base.Preconditions;
import javafx.util.Pair;
import ug.MachineLearningEngine;
import ug.naive.classifiers.ReviewClassfier;
import ug.naive.classifiers.SpamClassfier;
import ug.naive.exceptions.NaiveBayesException;
import ug.naive.kernels.Kernel;

import java.security.SecureRandom;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class NaiveBayesEngine implements MachineLearningEngine {

    private NaiveDataContainer dataSetContainer;

    private Kernel kernel;

    private boolean debugMode = false; //default FALSE

    private NaiveBayesEngine(Builder builder) {
        this.dataSetContainer = builder.dataSetContainer;
        this.kernel = builder.kernel;
        this.debugMode = builder.debugMode;
    }

    public static Builder newNaiveBayesEngine() {
        return new Builder();
    }

    /**
     * Examine sentence word after word tokenizer probabilities.
     *
     * @param sentence
     * @return predicted T
     */
    @Override
    public Enum predict(String sentence) {
        if (sentence.isEmpty()) {
            throw new NaiveBayesException("Sentence cannot be empty ");
        }

        sentence = dataSetContainer.performTokenization(sentence);

        List<String> listOfTokens = dataSetContainer.performNGramsSplitter(sentence);

        listOfTokens = removeNotRocognisedWords(listOfTokens);

        if (listOfTokens.size() == 0) {
            if (dataSetContainer.getClassifier().equals(ReviewClassfier.class)) {
                return randomReviewClassifier();
            } else if (dataSetContainer.getClassifier().equals(SpamClassfier.class)) {
                return randomSpamClassifier();
            } else {
                throw new NaiveBayesException("None of words were recognised ");
            }
        }

        List<Pair<String, Map<Enum, Double>>> wordsWithClassifierProbabilities = calculateWords(listOfTokens);

        Map<Enum, Double> results = new HashMap<>();

        for (Object object : EnumSet.allOf(dataSetContainer.getClassifier())) {
            Enum classifier = (Enum) object;
            results.put(
                    classifier,
                    wordsWithClassifierProbabilities.stream()
                            .map(entry -> entry.getValue().get(classifier))
                            .mapToDouble(d -> d)
                            .sum());

        }

        /**
         * for printing custom text probabilities.
         */
        if (debugMode) {
            double allProbabilities = results.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();
            results.forEach((k, v) -> System.out.println(k + " " + (v * 100 / allProbabilities)));
        }
        return results.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
    }


    private List<Pair<String, Map<Enum, Double>>> calculateWords(List<String> words) {
        return words.stream()
                .map(word -> new Pair<>(
                        word,
                        calculateSingleWord(word)))
                .collect(Collectors.toList()
                );

    }

    private Map<Enum, Double> calculateSingleWord(String word) {
        Map<Enum, Integer> occurences = (Map<Enum, Integer>) dataSetContainer.getDataSet().get(word);

        double probabilitySummarized = occurences.entrySet().stream()
                .map((entry -> (double) entry.getValue() / (Integer) dataSetContainer.getClassifierSizes().get(entry
                        .getKey())))
                .mapToDouble(d -> d)
                .sum();

        Map<Enum, Double> probabilities = new HashMap<>();

        occurences.forEach((k, v) -> probabilities.put(k,
                kernel.predict(((double) v / (Integer) dataSetContainer.getClassifierSizes().get(k)) / probabilitySummarized)));

        return probabilities;
    }

    private List<String> removeNotRocognisedWords(List<String> listOfTokens) {
        return listOfTokens.stream()
                .filter(word -> dataSetContainer.getDataSet().containsKey(word))
                .collect(Collectors.toList());
    }

    private static Random random = new SecureRandom();

    private static Enum randomReviewClassifier() {
        int x = random.nextInt(ReviewClassfier.values().length);
        return ReviewClassfier.values()[x];
    }

    private static Enum randomSpamClassifier() {
        int x = random.nextInt(SpamClassfier.values().length);
        return SpamClassfier.values()[x];
    }


    public static final class Builder {
        private NaiveDataContainer dataSetContainer;
        private Kernel kernel;
        private boolean debugMode;

        private Builder() {
        }

        public NaiveBayesEngine build() {
            Preconditions.checkState(kernel != null, "Kernel has to be set");

            return new NaiveBayesEngine(this);
        }

        public Builder dataSetContainer(NaiveDataContainer dataSetContainer) {
            this.dataSetContainer = dataSetContainer;
            return this;
        }

        public Builder kernel(Kernel kernel) {
            Preconditions.checkState(this.kernel == null, "Only one Kernel is allowed");
            this.kernel = kernel;
            return this;
        }

        public Builder debugMode(boolean debugMode) {
            this.debugMode = debugMode;
            return this;
        }
    }
}
