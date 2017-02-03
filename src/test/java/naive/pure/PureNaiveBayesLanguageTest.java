package naive.pure;

import naive.Dataset;
import naive.PureNaiveBayesEngine;
import naive.TestAbstract;
import naive.classifiers.LanguageClassifier;
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

public class PureNaiveBayesLanguageTest extends TestAbstract {

    private Map<URL, Enum> trainingFiles;

    @Before
    public void prepare() {
        trainingFiles = new HashMap<>();
        trainingFiles.put(PureNaiveBayesLanguageTest.class.getResource("/datasets/language-recognition/training" +
                ".language.en.txt"), LanguageClassifier.ENGLISH);
        trainingFiles.put( PureNaiveBayesLanguageTest.class.getResource("/datasets/language-recognition/training" +
                ".language.pl.txt"), LanguageClassifier.POLISH);

        dataset = new Dataset(LanguageClassifier.class);
        dataset.train(trainingFiles);

        engine = new PureNaiveBayesEngine(dataset);

    }

    @Test
    public void languageRecognitionPolish() throws Exception {
        assertThat(engine.predict("Ala ma kota"), is(LanguageClassifier.POLISH));
    }

    @Test
    public void languageRecognitionEnglish() throws Exception {
        assertThat(engine.predict("Ala has a cat"), is(LanguageClassifier.ENGLISH));
    }

    @Test
    public void languageRecognition() throws Exception {
        Map<String, LanguageClassifier> files = prepareSampleFiles();

        long correctValues = files.entrySet().stream()
                .filter(entry -> entry.getValue().equals(engine.predict(loadFile(entry.getKey()))))
                .count();
        long allValues = (long) files.entrySet().size();

        System.out.println(String.join(" ", "Pure Naive Bayes predict language in", String.valueOf(correctValues), " on ", String.valueOf(allValues)));

        assertThat(correctValues, is(allValues));
    }


    private Map<String, LanguageClassifier> prepareSampleFiles() {
        Map<String, LanguageClassifier> map = new HashMap<>();
        map.put("/datasets/language-recognition/test/Polish1", LanguageClassifier.POLISH);
        map.put("/datasets/language-recognition/test/Polish2", LanguageClassifier.POLISH);
        map.put("/datasets/language-recognition/test/Polish3", LanguageClassifier.POLISH);
        map.put("/datasets/language-recognition/test/Polish4", LanguageClassifier.POLISH);
        map.put("/datasets/language-recognition/test/Polish5", LanguageClassifier.POLISH);
        map.put("/datasets/language-recognition/test/English1", LanguageClassifier.ENGLISH);
        map.put("/datasets/language-recognition/test/English2", LanguageClassifier.ENGLISH);
        map.put("/datasets/language-recognition/test/English3", LanguageClassifier.ENGLISH);
        map.put("/datasets/language-recognition/test/English4", LanguageClassifier.ENGLISH);
        map.put("/datasets/language-recognition/test/English5", LanguageClassifier.ENGLISH);

        return map;
    }

}