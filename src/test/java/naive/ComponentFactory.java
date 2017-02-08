package naive;

import naive.kernels.ExponentialKernel;
import naive.kernels.RBFkernel;
import naive.kernels.SVNkernel;
import naive.preprocessors.IgnoreCasePreprocessor;
import naive.preprocessors.RemoveExclationMarksPreprocessor;
import naive.preprocessors.RemoveShortenThen2Preprocessor;
import naive.preprocessors.RemoveSpecialCharsPreprocessor;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class ComponentFactory {

    public static Dataset getDataset(Class clazz) {
        return new Dataset.DatasetBuilder(clazz)
//                .with(new RemoveExclationMarksPreprocessor())
                .with(new RemoveSpecialCharsPreprocessor())
                .build();
    }

    public static NaiveBayesEngine getEngine(Dataset dataset) {
        return new NaiveBayesEngine(dataset)
//                .with(new SVNkernel());
                .with(new ExponentialKernel(3));
//                .with(new RBFkernel());

    }

}
