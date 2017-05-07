package ug.naive.pure;

import ug.naive.TestAbstract;
import ug.naive.classifiers.LanguageClassifier;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static ug.naive.ComponentFactory.prepareDataset;
import static ug.naive.ComponentFactory.prepareEngine;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Maciej Rudnicki on 01/02/2017.
 */

public class LanguageTest extends TestAbstract {


    @Before
    public void prepare() throws Exception {
        data = new HashMap<>();
        data.putAll(prepareData(LanguageClassifier.ENGLISH, "/datasets/language-recognition/eng"));
        data.putAll(prepareData(LanguageClassifier.POLISH, "/datasets/language-recognition/pl"));

        dataset = prepareDataset(LanguageClassifier.class, data);

        engine = prepareEngine(dataset)
                ;

    }


    @Test
//    @Ignore
    public void sample() throws Exception{

        System.out.println("Predicted "+engine.predict("This is sample message"));
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
        Map<String, LanguageClassifier> files = prepareLanguageSampleFiles();

        long correctValues = files.entrySet().stream()
                .filter(entry -> entry.getValue().equals(engine.predict(loadFile(entry.getKey()))))
                .count();
        long allValues = (long) files.entrySet().size();

        System.out.println(String.join(" ", "Pure Naive Bayes predict language in", String.valueOf(correctValues), " on ", String.valueOf(allValues)));

    }


}