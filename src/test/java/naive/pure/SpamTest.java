package naive.pure;

import javafx.util.Pair;
import naive.TestAbstract;
import naive.classifiers.SpamClassfier;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static naive.ComponentFactory.prepareDataset;
import static naive.ComponentFactory.prepareEngine;
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

        for(int i = 0; i< AMOUNT_OF_ITERATIONS_OF_TEST_RUN; i++){
            Pair<Long, Long> pair = singleRun();

            summaryCorrectAnswers += pair.getKey();
            summaryRecords += pair.getValue();

            System.out.println(String.join(" ", "Loop nr",String.valueOf(i+1),
                    "Spam correct answers :",String.valueOf(pair.getKey()),
                    "on ",String.valueOf(pair.getValue()),
                    "what gives", String.valueOf((double)pair.getKey()*100/pair.getValue()),
                    "efficiency"
            ));
        }

        System.out.println(String.join(" ","Summary recognised", String.valueOf(summaryCorrectAnswers),
                "in set of",String.valueOf(summaryRecords),"records.",
                "Spam. Summary efficiency is ",
                String.valueOf((double)summaryCorrectAnswers*100/summaryRecords),"%"));

    }

    protected Pair<Long, Long> singleRun() throws Exception{

        dataset = prepareDataset(SpamClassfier.class);
        engine = prepareEngine(dataset);

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
