package naive;

import naive.classifiers.Classifier;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maciej Rudnicki on 01/02/2017.
 */
public class ExampleNativeBayes {

    public static void main(String[] args) {

        Map<Classifier, URL> trainingFiles = new HashMap<>();
        trainingFiles.put(Classifier.ENGLISH, ExampleNativeBayes.class.getResource("/datasets/training.language.en.txt"));
        trainingFiles.put(Classifier.POLISH, ExampleNativeBayes.class.getResource("/datasets/training.language.pl.txt"));

        Dataset dataset = new Dataset();
        dataset.train(trainingFiles);

        NaiveBayersEngine engine = new NaiveBayersEngine(dataset);
        System.out.println(engine.predict("Ala ma kota being").toString());

    }



}
