package naive;

import naive.pure.PureNaiveBayesLanguageTest;
import naive.pure.PureNaiveBayesSpamTest;

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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class TestAbstract {

    protected Dataset dataset;

    protected NaiveBayesEngine engine;

    protected String loadFile(String resourcePath) {
        InputStream stream = PureNaiveBayesLanguageTest.class.getResourceAsStream(resourcePath);
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
                .map(PureNaiveBayesSpamTest.class::getResource)
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
}
