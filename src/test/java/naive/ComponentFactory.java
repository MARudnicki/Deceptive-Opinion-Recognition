package naive;

import naive.kernels.ExponentialKernel;
import naive.ngrams.OneGramSplitter;
import naive.preprocessors.RemoveExclationMarksTokenizerPreprocessor;
import naive.preprocessors.RemoveShortenThen3TokenizerPreprocessor;
import naive.preprocessors.RemoveSpecialCharsTokenizerPreprocessor;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class ComponentFactory {

    /**
     * DataSet
     * @param clazz Classifier type e.g. Language/Review/SpamClassfier
     * @return DataSet withTokenizer Tokenization pre-processing
     */
    public static DataSet prepareDataset(Class clazz) {
        return new DataSet.DatasetBuilder(clazz)
                .withTokenizer(new RemoveExclationMarksTokenizerPreprocessor())
                .withTokenizer(new RemoveSpecialCharsTokenizerPreprocessor())
                .withTokenizer(new RemoveShortenThen3TokenizerPreprocessor())
                .withNGramsSplitter(new OneGramSplitter())
                .build();
    }

    /**
     * NaiveBayesEngine
     * @param dataset withTokenizer training data & pre-processing configuration
     * @return engine to predict class of classifier of study test
     */
    public static NaiveBayesEngine prepareEngine(DataSet dataset) {
        return new NaiveBayesEngine(dataset)
//                .withTokenizer(new LogicalKernel());
                .with(new ExponentialKernel(5));

    }

}
