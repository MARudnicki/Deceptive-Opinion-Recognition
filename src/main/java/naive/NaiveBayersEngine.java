package naive;

import naive.classifiers.Classifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class NaiveBayersEngine {

    private Map<String, Map<Classifier, Integer>> dataSet;

    private Map<Classifier, Integer> classifierSizes;

    public NaiveBayersEngine(Dataset dataset) {
        this.dataSet = dataset.getDataSet();
        this.classifierSizes = dataset.getClassifierSizes();
    }

    /**
     * Examine sentence word after word with probabilities.
     *
     * @param sentence
     * @return
     */
    public Classifier predict(String sentence) {
        List<String> list = Arrays.asList(sentence.split(" "));
        List<Classifier> listOfClassifiers =
                list.stream()
                        .filter(word -> dataSet.containsKey(word))
                        .map(this::examineSimpleWord)
                        .collect(Collectors.toList());

        int recognisedWordsSize = listOfClassifiers.size();

        Map<Classifier, Long> counts =
                listOfClassifiers.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));


        for (Classifier classifier : Classifier.values()) {
            double probability = (double) counts.get(classifier) / recognisedWordsSize;
            System.out.println("Probability that sentence is in " + classifier + " is " + probability);
        }

        return counts.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
    }

    private Classifier examineSimpleWord(String word) {
        System.out.println("Examine word : " + word);

        Map<Classifier, Integer> wordEntry = dataSet.get(word);

        Classifier max = wordEntry.entrySet()
                .stream()
                .max((entry1, entry2) ->
                        (double) entry1.getValue() / classifierSizes.get(entry1.getKey()) > entry2.getValue() / classifierSizes.get(entry2.getKey()) ? 1 : -1)
                .get()
                .getKey();

        System.out.println(max.toString());
        return max;

    }

}
