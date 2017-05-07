package ug.naive.pure;

import javafx.util.Pair;
import ug.naive.TestAbstract;
import ug.naive.classifiers.ReviewClassfier;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static ug.naive.ComponentFactory.prepareDataset;
import static ug.naive.ComponentFactory.prepareEngine;

/**
 * Created by Maciej Rudnicki on 03/02/2017.
 */
public class ReviewTest extends TestAbstract {

    private String answer;

    @Test
    @Ignore
    public void sample() throws Exception {

        Given:
        data = new HashMap<>();
        data.putAll(prepareDeceptiveReviewsNegative());
        data.putAll(prepateDeceptiveReviewsPositive());
        data.putAll(prepareThuthfullReviewsNegative());
        data.putAll(prepareThuthfullReviewsPositive());

        dataset = prepareDataset(ReviewClassfier.class, data);

        engine = prepareEngine(dataset);

        When:
        answer = engine.predict("It was a greate hostel. Really it was best ever!").name();
        System.out.println("Predicted " + answer);
    }

    @Test
    public void runReviewTest() throws Exception {
        long summaryCorrectAnswers = 0;
        long summaryRecords = 0;

        for (int i = 0; i < AMOUNT_OF_ITERATIONS_OF_TEST_RUN; i++) {
            Pair<Long, Long> pair = singleRun();

            summaryCorrectAnswers += pair.getKey();
            summaryRecords += pair.getValue();

            System.out.println(String.join(" ", "Loop nr", String.valueOf(i + 1),
                    "Reviews correct answers :", String.valueOf(pair.getKey()),
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

    private Pair<Long, Long> singleRun() throws Exception {
        data = new HashMap<>();

        data.putAll(prepareDeceptiveReviewsNegative());
        data.putAll(prepateDeceptiveReviewsPositive());
        data.putAll(prepareThuthfullReviewsNegative());
        data.putAll(prepareThuthfullReviewsPositive());

        Pair<
                Map<URL, Enum>,
                Map<URL, Enum>
                > pair = splitDataIntoTrainingAndVerificationSet(data);

        Map<URL, Enum> trainingSet = pair.getKey();
        Map<URL, Enum> verificationSet = pair.getValue();

        dataset = prepareDataset(ReviewClassfier.class, trainingSet);
        engine = prepareEngine(dataset);

        long correctValues = verificationSet.entrySet()
                .stream()
                .filter(this::predictAndCompare)
                .count();

        long allValues = (long) verificationSet.entrySet().size();

        return new Pair<>(correctValues, allValues);
    }

}
