package naive;

import naive.classifiers.Classifier;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Maciej Rudnicki on 01/02/2017.
 */


public class PureNaiveBayesTest extends TestAbstract {

    private Dataset dataset;

    private Map<Classifier, URL> trainingFiles;

    private NaiveBayersEngine engine;

    @Before
    public void prepare() {
        trainingFiles = new HashMap<>();
        trainingFiles.put(Classifier.ENGLISH, PureNaiveBayesTest.class.getResource("/datasets/language-recognition/training.language.en.txt"));
        trainingFiles.put(Classifier.POLISH, PureNaiveBayesTest.class.getResource("/datasets/language-recognition/training.language.pl.txt"));

        dataset = new Dataset();
        dataset.train(trainingFiles);

        engine = new NaiveBayersEngine(dataset);

    }

    @Test
    public void languageRecognitionPolish() throws Exception {
        assertThat(engine.predict("Ala ma kota"), is(Classifier.POLISH));
    }

    @Test
    public void languageRecognitionEnglish() throws Exception {
        assertThat(engine.predict("Ala has a cat"), is(Classifier.ENGLISH));
    }

    @Test
    public void languageRecognition() throws Exception {
        Map<String, Classifier> files = prepareSampleFiles();
        long correctValues = files.entrySet().stream()
                .filter(entry -> entry.getValue().equals(engine.predict(loadFile(entry.getKey()))))
                .count();
        long allValues = (long) files.entrySet().size();

        System.out.println(String.join(" ", "Pure Naive Bayes predict language in", String.valueOf(correctValues), " on ", String.valueOf(allValues)));

    }


}