package naive;

import naive.kernels.ExponentialKernel;
import naive.preprocessors.RemoveExclationMarksTokenizer;
import naive.preprocessors.RemoveSpecialCharsTokenizer;

import java.net.URL;
import java.util.Map;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class ComponentFactory {

    /**
     * DataContainer
     * @param clazz Classifier type e.g. Language/Review/SpamClassfier
     * @return DataContainer withTokenizer Tokenization pre-processing
     */
    public static DataContainer prepareDataset(Class clazz, Map<URL, Enum> data) {
        DataContainer dataContainer =  new DataContainer.DatasetBuilder(clazz)
                .withTokenizer(new RemoveExclationMarksTokenizer())
                .withTokenizer(new RemoveSpecialCharsTokenizer())
                .build();
        dataContainer.train(data);

        return dataContainer;
    }

    /**
     * NaiveBayesEngine
     * @param dataset withTokenizer training data & pre-processing configuration
     * @return engine to predict class of classifier of study test
     */
    public static NaiveBayesEngine prepareEngine(DataContainer dataset) {
        return NaiveBayesEngine.newNaiveBayesEngine()
                .dataSetContainer(dataset)
                .kernel(new ExponentialKernel(3))
                .build();

    }

}
