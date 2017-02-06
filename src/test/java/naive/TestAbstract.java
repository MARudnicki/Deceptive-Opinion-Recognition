package naive;

import naive.classifiers.LanguageClassifier;
import naive.classifiers.ReviewClassfier;
import naive.classifiers.SpamClassfier;
import naive.pure.LanguageTest;
import naive.pure.SpamTest;

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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class TestAbstract {

    protected Dataset dataset;

    protected NaiveBayesEngine engine;

    protected String loadFile(String resourcePath) {
        InputStream stream = LanguageTest.class.getResourceAsStream(resourcePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        return reader.lines().collect(Collectors.joining(" "));
    }



    protected void prepareData(final Enum spamClassfier, String path) throws Exception {
        Map<URL, Enum> URLs = prepareMapOfUrl(path, spamClassfier);

        dataset.train(URLs);
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

    protected Boolean isPredictionCorrect(Map.Entry<URL, Enum> entry) {
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

    protected void prepareSpam() throws Exception {
        prepareData(SpamClassfier.SPAM, "/datasets/spam-recognition/training-set/spam");
    }

    protected void prepareHam() throws Exception {
        prepareData(SpamClassfier.NOT_SPAM, "/datasets/spam-recognition/training-set/ham");
    }

    protected void prepareThuthfullReviewsPositive() throws Exception {

        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold1");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold2");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold3");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/positive_polarity/truthful_from_TripAdvisor/fold4");
    }

    protected void prepareThuthfullReviewsNegative() throws Exception {

        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold1");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold2");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold3");
        prepareData(ReviewClassfier.TRUTHFULL,
                "/datasets/op_spam_v1.4/negative_polarity/truthful_from_Web/fold4");
    }

    protected void prepateDeceptiveReviewsPositive() throws Exception {
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold1");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold2");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold3");
        prepareData(ReviewClassfier.DECEPTIVE,
                "/datasets/op_spam_v1.4/positive_polarity/deceptive_from_MTurk/fold4");
    }

    protected void prepareDeceptiveReviewsNegative() throws Exception {
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
