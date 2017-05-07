package ug.naive.pure;

import javafx.util.Pair;
import ug.naive.TestAbstract;
import ug.naive.classifiers.SpamClassfier;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static ug.naive.ComponentFactory.prepareDataset;
import static ug.naive.ComponentFactory.prepareEngine;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class SpamTest extends TestAbstract {

    private String answer;

    @Test
    @Ignore
    public void sample() throws Exception {

        Given:
        data = new HashMap<>();
        data.putAll(prepareSpam());
        data.putAll(prepareHam());

        dataset = prepareDataset(SpamClassfier.class, data);
        engine = prepareEngine(dataset);

        Then:
        answer = engine.predict("???").name();
        System.out.println("Predicted " + answer);
    }

    @Test
    public void runSpamTest() throws Exception {
        long summaryCorrectAnswers = 0;
        long summaryRecords = 0;

        for (int i = 0; i < AMOUNT_OF_ITERATIONS_OF_TEST_RUN; i++) {
            Pair<Long, Long> pair = singleRun();

            summaryCorrectAnswers += pair.getKey();
            summaryRecords += pair.getValue();

            System.out.println(String.join(" ", "Loop nr", String.valueOf(i + 1),
                    "Spam correct answers :", String.valueOf(pair.getKey()),
                    "on ", String.valueOf(pair.getValue()),
                    "what gives", String.valueOf((double) pair.getKey() * 100 / pair.getValue()),
                    "efficiency"
            ));
        }

        System.out.println(String.join(" ", "Summary recognised", String.valueOf(summaryCorrectAnswers),
                "in set of", String.valueOf(summaryRecords), "records.",
                "Spam. Summary efficiency is ",
                String.valueOf((double) summaryCorrectAnswers * 100 / summaryRecords), "%"));

    }

    protected Pair<Long, Long> singleRun() throws Exception {

        data = new HashMap<>();
        data.putAll(prepareSpam());
        data.putAll(prepareHam());

        Pair<
                Map<URL, Enum>,
                Map<URL, Enum>
                > pair = splitDataIntoTrainingAndVerificationSet(data);

        Map<URL, Enum> trainingSet = pair.getKey();
        Map<URL, Enum> verificationSet = pair.getValue();

        dataset = prepareDataset(SpamClassfier.class,trainingSet);
        engine = prepareEngine(dataset);

        long correctValues = verificationSet.entrySet()
                .stream()
                .filter(this::predictAndCompare)
                .count();

        long allValues = (long) verificationSet.entrySet().size();

        return new Pair<>(correctValues, allValues);
    }
}
