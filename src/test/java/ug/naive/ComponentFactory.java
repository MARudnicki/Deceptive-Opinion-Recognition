package ug.naive;

import ug.MachineLearningEngine;
import ug.naive.ngrams.OneGramSplitter;
import ug.naive.preprocessors.RemoveExclationMarksTokenizer;
import ug.naive.preprocessors.RemoveSpecialCharsTokenizer;
import ug.neutral.NeutralEngine;

import java.net.URL;
import java.util.Map;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class ComponentFactory {

    /****
     *
     *
     *
     * NAIVE BAYES AND N-GRAMS SECTION
     *
     *
     *
     *
     */

    /**
     * NaiveDataContainer
     *
     * @param clazz Classifier type e.g. Language/Review/SpamClassfier
     * @return NaiveDataContainer tokenizer Tokenization pre-processing
     */
//    public static NaiveDataContainer prepareDataset(Class clazz, Map<URL, Enum> data) {
//        NaiveDataContainer dataContainer = new NaiveDataContainer.DatasetBuilder(clazz)
//                .tokenizer(new RemoveExclationMarksTokenizer())
//                .tokenizer(new RemoveSpecialCharsTokenizer())
//                .nGramsSplitter(new OneGramSplitter())
//                .build();
//        dataContainer.train(data);
//
//        return dataContainer;
//    }

    /**
     * NaiveBayesEngine
     *
     * @param dataset tokenizer training data & pre-processing configuration
     * @return engine to predict class of classifier of study test
     */
//    public static MachineLearningEngine prepareEngine(NaiveDataContainer dataset) {
//        return NaiveBayesEngine.newNaiveBayesEngine()
//                .dataSetContainer(dataset)
//                .kernel(new ExponentialKernel(3))
//                .debugMode(false)
//                .build();
//    }


    /**
     *
     *
     *
     * NEUTRAL NETWORK SECTION
     *
     *
     *
     *
     */


    /**
     * NaiveDataContainer
     *
     * @param clazz Classifier type e.g. Language/Review/SpamClassfier
     * @return NaiveDataContainer tokenizer Tokenization pre-processing
     */
    public static NaiveDataContainer prepareDataset(Class clazz, Map<URL, Enum> data) {
        NaiveDataContainer naiveDataContainer = new NaiveDataContainer.DatasetBuilder(clazz)
                .tokenizer(new RemoveExclationMarksTokenizer())
                .tokenizer(new RemoveSpecialCharsTokenizer())
                .nGramsSplitter(new OneGramSplitter())
                .build();
        naiveDataContainer.train(data);

        return naiveDataContainer;
    }
    /**
     * MachineLearningEngine
     *
     * @param dataset tokenizer training data & pre-processing configuration
     * @return engine to predict class of classifier of study test
     */
    public static MachineLearningEngine prepareEngine(NaiveDataContainer dataSet) {
        return NeutralEngine.newNeutralEngine()
                .alpha(0.05)
                .dataSetContainer(dataSet)
                .build();
    }

}
