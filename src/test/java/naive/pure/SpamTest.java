package naive.pure;

import naive.DatasetFactory;
import naive.NaiveBayesEngine;
import naive.TestAbstract;
import naive.classifiers.SpamClassfier;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class SpamTest extends TestAbstract {

    @Before
    public void prepare() throws Exception {
        dataset = DatasetFactory.getDataset(SpamClassfier.class);

        engine = new NaiveBayesEngine(dataset);

        prepareSpam();
        prepareHam();
    }


    @Test
    public void spamRecognition() throws Exception{
        List<Double> efficiency = new ArrayList<>();

    }


    private double calculateEfficiency() throws Exception {
        Map<URL, Enum> URLsHam = prepareMapOfUrl("/datasets/spam-recognition/veryfication-set/ham",
                SpamClassfier.NOT_SPAM);
        return 0;
    }

    @Test
    public void spamRecognition2() throws Exception {

        Map<URL, Enum> URLsHam = prepareMapOfUrl("/datasets/spam-recognition/veryfication-set/ham",
                SpamClassfier.NOT_SPAM);

        long correctValuesHam = URLsHam.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValuesHam = (long) URLsHam.entrySet().size();

        System.out.println("Correct values : " + correctValuesHam + " of " + allValuesHam);

        Map<URL, Enum> URLsSpam = prepareMapOfUrl("/datasets/spam-recognition/veryfication-set/spam",
                SpamClassfier.SPAM);

        long correctValuesSpam = URLsSpam.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValuesSpam = (long) URLsSpam.entrySet().size();

        System.out.println("Correct values : " + correctValuesSpam + " of " + allValuesSpam);

        long correctValues = correctValuesHam + correctValuesSpam;
        long allValues = allValuesSpam + allValuesHam;
        double efficiency = (double) correctValues * 100 / allValues;
        System.out.println(String.join(" ",
                "Pure Naive Bayes predict Spam. Correct values : ",
                String.valueOf(correctValues),
                " of ",
                String.valueOf(allValues),
                "what gives",
                String.valueOf(efficiency),
                "% efficiency"));

        assertTrue(efficiency > 85);
    }
}
