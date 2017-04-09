package naive;

import naive.kernels.ExponentialKernel;
import naive.kernels.LogicalKernel;
import naive.preprocessors.RemoveExclationMarksPreprocessor;
import naive.preprocessors.RemoveSpecialCharsPreprocessor;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class ComponentFactory {

    /**
     * DataSet
     * @param clazz Classifier type e.g. Language/Review/SpamClassfier
     * @return DataSet with Tokenization pre-processing
     */
    public static DataSet prepareDataset(Class clazz) {
        return new DataSet.DatasetBuilder(clazz)
                .with(new RemoveExclationMarksPreprocessor())
                .with(new RemoveSpecialCharsPreprocessor())
                .build();
    }

    /**
     * NaiveBayesEngine
     * @param dataset with training data & pre-processing configuration
     * @return engine to predict class of classifier of study test
     */
    public static NaiveBayesEngine prepareEngine(DataSet dataset) {
        return new NaiveBayesEngine(dataset)
//                .with(new LogicalKernel())
                .with(new ExponentialKernel(3));

    }

}
