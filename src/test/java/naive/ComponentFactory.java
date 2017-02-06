package naive;

import naive.kernels.BooleanKernel;
import naive.kernels.CubicKernel;
import naive.kernels.SquaredKernel;
import naive.preprocessors.IgnoreCasePreprocessor;
import naive.preprocessors.RemoveDigitsPreprocessor;
import naive.preprocessors.RemoveExclationMarksPreprocessor;
import naive.preprocessors.RemoveShortenThen3Preprocessor;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class ComponentFactory {

    public static Dataset getDataset(Class clazz) {
        return new Dataset.DatasetBuilder(clazz)
                .with(new IgnoreCasePreprocessor())
                .with(new RemoveDigitsPreprocessor())
                .with(new RemoveExclationMarksPreprocessor())
                .with(new RemoveShortenThen3Preprocessor())
                .build();
    }

    public static NaiveBayesEngine getEngine(Dataset dataset){
        return new NaiveBayesEngine(dataset)
                .with(new CubicKernel());

    }

}
