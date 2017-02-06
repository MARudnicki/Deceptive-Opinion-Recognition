package naive.pure;

import javafx.util.Pair;
import naive.DatasetFactory;
import naive.NaiveBayesEngine;
import naive.TestAbstract;
import naive.classifiers.SpamClassfier;
import org.junit.Test;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class SpamTest extends TestAbstract {

    @Test
    public void runSpamTest() throws Exception{
        long summaryCorrectAnswers = 0;
        long summaryRecords = 0;

        for(int i=0 ; i< NMBER_OF_TEST_RUNS; i++){
            Pair<Long, Long> pair = singleRun();

            summaryCorrectAnswers += pair.getKey();
            summaryRecords += pair.getValue();

            System.out.println(String.join(" ", "Loop nr",String.valueOf(i+1),
                    "Spam correct answers :",String.valueOf(pair.getKey()),
                    "on ",String.valueOf(pair.getValue())));
        }

        System.out.println(String.join(" ","Spam. Summary efficiency is ",
                String.valueOf((double)summaryCorrectAnswers*100/summaryRecords),"%"));

    }

    protected Pair<Long, Long> singleRun() throws Exception{

        dataset = DatasetFactory.getDataset(SpamClassfier.class);
        engine = new NaiveBayesEngine(dataset);

        data = new HashMap<>();
        data.putAll(prepareSpam());
        data.putAll(prepareHam());

        Pair<
                Map<URL,Enum>,
                Map<URL,Enum>
                > pair = splitDataIntoTrainingAndVerificationSet(data);

        Map<URL, Enum> trainingSet = pair.getKey();
        Map<URL, Enum> verificationSet = pair.getValue();

        dataset.train(trainingSet);

        long correctValues = verificationSet.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValues = (long) verificationSet.entrySet().size();

        return new Pair<>(correctValues, allValues);
    }
}
