package naive;

import naive.preprocessors.IgnoreCasePreprocessor;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class DatasetFactory {

    public static Dataset getDataset(Class clazz) {
        return new Dataset.DatasetBuilder(clazz)
                .with(new IgnoreCasePreprocessor())

                .build();
    }

}
