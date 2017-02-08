package naive;

import naive.kernels.SVNkernel;
import naive.preprocessors.RemoveExclationMarksPreprocessor;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class ComponentFactory {

    public static Dataset getDataset(Class clazz) {
        return new Dataset.DatasetBuilder(clazz)
                .with(new RemoveExclationMarksPreprocessor())
//                .with(new RemoveQuotationPreprocessor())
//                .with(new IgnoreCasePreprocessor())
                .build();
    }

    public static NaiveBayesEngine getEngine(Dataset dataset) {
        return new NaiveBayesEngine(dataset)
                .with(new SVNkernel());

    }

}
