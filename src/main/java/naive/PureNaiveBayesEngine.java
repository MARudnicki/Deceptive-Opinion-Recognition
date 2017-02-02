package naive;

import naive.exceptions.NaiveBayesException;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class PureNaiveBayesEngine <T extends Enum>{

    private Map<String, Map<T, Integer>> dataSet;

    private Map<T, Integer> classifierSizes;

    private Class<T> classifierType;

    public PureNaiveBayesEngine(Dataset dataset) {
        this.dataSet = dataset.getDataSet();
        this.classifierSizes = dataset.getClassifierSizes();
        this.classifierType = dataset.getClassifier();
    }

    /**
     * Examine sentence word after word with probabilities.
     *
     * @param sentence
     * @return predicted T
     */
    public T predict(String sentence) {
        if(sentence.isEmpty()){
            throw new NaiveBayesException("Sentence cannot be empty ");
        }

        List<String> list = Arrays.asList(sentence.split(" "));
        List<T> listOfClassifiers =
                list.stream()
                        .filter(word -> dataSet.containsKey(word))
                        .map(this::examineSimpleWord)
                        .collect(Collectors.toList());

        int recognisedWordsSize = listOfClassifiers.size();

        if(recognisedWordsSize==0){
            throw new NaiveBayesException("None of words were recognised ");
        }

        Map<T, Long> counts =
                listOfClassifiers.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        EnumSet.allOf(classifierType).stream().forEach(classifier -> {
            double probability = (double) counts.getOrDefault(classifier,0L) / recognisedWordsSize;
            System.out.println("Probability that sentence is in " + classifier + " is " + probability);
        });

        return counts.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
    }

    /**
     * examine sample word
     * @param word to examine
     * @return output classifierType
     */
    private T examineSimpleWord(String word) {
        System.out.println("Examine word : " + word);

        Map<T, Integer> wordEntry = dataSet.get(word);
        T winner=null;
        double value = 0;

        for(Map.Entry<T, Integer> entry : wordEntry.entrySet()){
            if(winner==null || (double)entry.getValue() / classifierSizes.get(entry.getKey()) > value){
                winner = entry.getKey();
                value = (double)entry.getValue()/classifierSizes.get(entry.getKey());
            }
        }
        System.out.println(String.join(" ","Token",word,"is",winner.toString()));

        return winner;

    }

}
