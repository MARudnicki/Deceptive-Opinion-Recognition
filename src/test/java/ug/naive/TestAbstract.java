package ug.naive;

import javafx.util.Pair;
import ug.MachineLearningEngine;
import ug.naive.classifiers.LanguageClassifier;
import ug.naive.classifiers.ReviewClassfier;
import ug.naive.classifiers.SpamClassfier;
import ug.naive.pure.LanguageTest;
import ug.naive.pure.SpamTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class TestAbstract {

    protected Random random = new SecureRandom();

    protected static final double VALIDATION_SIZE_PERCENT = 20;

    protected static final int AMOUNT_OF_ITERATIONS_OF_TEST_RUN = 10;

    protected NaiveDataContainer dataset;

    protected MachineLearningEngine engine;

    protected Map<URL, Enum> data;

    protected Pair<Map<URL,Enum>,Map<URL,Enum>> splitDataIntoTrainingAndVerificationSet(Map<URL, Enum> data){

        Map<URL, Enum> veryficationSet = new HashMap<>();
        Map<URL, Enum> trainingSet = new HashMap<>(data);

        long validationSize = Math.round(data.size()*VALIDATION_SIZE_PERCENT/100);

        for(int i = 0; i<validationSize; i++) {
            ArrayList<URL> keys = new ArrayList(trainingSet.keySet());

            URL randomKey = keys.get(random.nextInt(keys.size()));

            veryficationSet.put(randomKey, trainingSet.get(randomKey));
            trainingSet.remove(randomKey);
        }
        return new Pair<>(trainingSet, veryficationSet);
    }

    protected String loadFile(String resourcePath) {
        InputStream stream = LanguageTest.class.getResourceAsStream(resourcePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        return reader.lines().collect(Collectors.joining(" "));
    }


    protected Map<URL, Enum> prepareData(final Enum spamClassfier, String path) throws Exception {
        return prepareMapOfUrl(path, spamClassfier);
    }


    protected Map<URL, Enum> prepareMapOfUrl(String path, final Enum spamClassfier) throws
            URISyntaxException, IOException {

        URL url = TestAbstract.class.getResource(path);

        return Files.walk(Paths.get(url.toURI()))
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .map(this::pathToResource)
                .map(SpamTest.class::getResource)
                .collect(Collectors.toMap(URL -> URL, URL -> spamClassfier));
    }

    protected Boolean predictAndCompare(Map.Entry<URL, Enum> entry) {
        String path = pathToResource(entry.getKey().toString());

        String sentence = loadFile(path);

        return entry.getValue().equals(engine.predict(sentence));
    }

    protected String pathToResource(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf("target/classes") + 14);
    }


    protected Map<String, LanguageClassifier> prepareLanguageSampleFiles() {
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

    protected Map<URL, Enum> prepareSpam() throws Exception {
        return prepareMapOfUrl("/datasets/spam-recognition/training-set/spam", SpamClassfier.SPAM);
    }

    protected Map<URL, Enum> prepareHam() throws Exception {
        return prepareMapOfUrl("/datasets/spam-recognition/training-set/ham", SpamClassfier.NOT_SPAM);
    }

    protected Map<URL, Enum> prepareThuthfullReviewsPositive() throws Exception {
        Map<URL, Enum> map = new HashMap<>();

        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold1",ReviewClassfier.TRUTHFULL));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold2",ReviewClassfier.TRUTHFULL));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold3",ReviewClassfier.TRUTHFULL));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold4",ReviewClassfier.TRUTHFULL));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold5",ReviewClassfier.TRUTHFULL));
        return map;
    }

    protected Map<URL, Enum>  prepareThuthfullReviewsNegative() throws Exception {
        Map<URL, Enum> map = new HashMap<>();

        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold1",ReviewClassfier.TRUTHFULL));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold2",ReviewClassfier.TRUTHFULL));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold3",ReviewClassfier.TRUTHFULL));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold4",ReviewClassfier.TRUTHFULL));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold5",ReviewClassfier.TRUTHFULL));

        return map;
    }

    protected Map<URL, Enum>  prepateDeceptiveReviewsPositive() throws Exception {
        Map<URL, Enum> map = new HashMap<>();

        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold1",ReviewClassfier.DECEPTIVE));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold2",ReviewClassfier.DECEPTIVE));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold3",ReviewClassfier.DECEPTIVE));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold4",ReviewClassfier.DECEPTIVE));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold5",ReviewClassfier.DECEPTIVE));
        return map;
    }

    protected Map<URL, Enum>  prepareDeceptiveReviewsNegative() throws Exception {
        Map<URL, Enum> map = new HashMap<>();

        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold1", ReviewClassfier.DECEPTIVE));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold2",ReviewClassfier.DECEPTIVE));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold3",ReviewClassfier.DECEPTIVE));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold4",ReviewClassfier.DECEPTIVE));
        map.putAll(prepareMapOfUrl(
                "/datasets/op_spam_v1.4/negative_polarity/deceptive_from_MTurk/fold5",ReviewClassfier.DECEPTIVE));
        return map;
    }


}
