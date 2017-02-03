package naive.pure;

import naive.Dataset;
import naive.PureNaiveBayesEngine;
import naive.TestAbstract;
import naive.classifiers.ReviewClassfier;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by Maciej Rudnicki on 03/02/2017.
 */
public class PureNaiveBayesReviewTest extends TestAbstract {

    @Before
    public void prepare() throws Exception {
        dataset = new Dataset(ReviewClassfier.class);
        engine = new PureNaiveBayesEngine(dataset);

        prepareDeceptiveReviewsNegative();
        prepateDeceptiveReviewsPositive();
        prepareThuthfullReviewsNegative();
        prepareThuthfullReviewsPositive();

    }


    @Test
    public void deceptiveReviewsRecognition() throws Exception {

        Map<URL, Enum> truthfullReviewsPositive = prepareMapOfUrl
                ("/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold5",
                        ReviewClassfier.TRUTHFULL);

        long correctValuesTruthfullReviewsPositive = truthfullReviewsPositive.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValuesTruthfullReviewsPositive = (long) truthfullReviewsPositive.entrySet().size();

        System.out.println("Correct values : " + correctValuesTruthfullReviewsPositive + " of " + allValuesTruthfullReviewsPositive);



        Map<URL, Enum> truthfullReviewsNegative = prepareMapOfUrl
                ("/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold5",
                        ReviewClassfier.TRUTHFULL);

        long correctValuesTruthfullReviewsNegative = truthfullReviewsNegative.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValuesTruthfullReviewsNegative = (long) truthfullReviewsNegative.entrySet().size();

        System.out.println("Correct values : " + correctValuesTruthfullReviewsNegative + " of " + allValuesTruthfullReviewsNegative);



        Map<URL, Enum> deceptiveReviewsPositive = prepareMapOfUrl
                ("/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold5",
                        ReviewClassfier.DECEPTIVE);

        long correctValuesDeceptiveReviewsPositive = deceptiveReviewsPositive.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValuesDeceptiveReviewsPositive = (long) deceptiveReviewsPositive.entrySet().size();

        System.out.println("Correct values : " + correctValuesDeceptiveReviewsPositive + " of " + allValuesDeceptiveReviewsPositive);



        Map<URL, Enum> deceptiveReviewsNegative = prepareMapOfUrl
                ("/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold5",
                        ReviewClassfier.DECEPTIVE);

        long correctValuesDeceptiveReviewsNegative = deceptiveReviewsNegative.entrySet().stream()
                .filter(this::isPredictionCorrect)
                .count();
        long allValuesDeceptiveReviewsNegative = (long) deceptiveReviewsNegative.entrySet().size();

        System.out.println("Correct values : " + correctValuesDeceptiveReviewsNegative + " of " + allValuesDeceptiveReviewsNegative);



        long correctValues =
                    correctValuesTruthfullReviewsPositive
                +   correctValuesTruthfullReviewsNegative
                +   correctValuesDeceptiveReviewsPositive
                +   correctValuesDeceptiveReviewsNegative;
        long allValues =
                    allValuesTruthfullReviewsPositive
                +   allValuesTruthfullReviewsNegative
                +   allValuesDeceptiveReviewsPositive
                +   allValuesDeceptiveReviewsNegative;

        double efficiency = (double) correctValues * 100 / allValues;

        System.out.println(String.join(" ",
                "SUMMARY Correct values : ",
                String.valueOf(correctValues),
                " of ",
                String.valueOf(allValues),
                "what gives",
                String.valueOf(efficiency),
                "% efficiency"));

        assertTrue(efficiency > 54);
    }


    private void prepareThuthfullReviewsPositive() throws Exception {

        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold1");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold2");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold3");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold4");
    }

    private void prepareThuthfullReviewsNegative() throws Exception {

        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold1");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold2");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold3");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold4");
    }

    private void prepateDeceptiveReviewsPositive() throws Exception {
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold1");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold2");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold3");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold4");
    }

    private void prepareDeceptiveReviewsNegative() throws Exception {
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold1");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold2");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold3");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold4");
    }
}