package naive;

import naive.classifiers.LanguageClassifier;
import naive.pure.PureNaiveBayesLanguageTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class TestAbstract {



    protected String loadFile(String resourcePath) {
        InputStream stream = PureNaiveBayesLanguageTest.class.getResourceAsStream(resourcePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String file = reader.lines().collect(Collectors.joining(" "));

        return file;
    }

    protected Map<String, LanguageClassifier> prepareSampleFiles(){
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
}
