package naive;

import naive.classifiers.Classifier;

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
        InputStream stream = PureNaiveBayesTest.class.getResourceAsStream(resourcePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String file = reader.lines().collect(Collectors.joining(" "));

        return file;
    }

    protected Map<String, Classifier> prepareSampleFiles(){
        Map<String, Classifier> map = new HashMap<>();
        map.put("/datasets/language-recognition/test/Polish1",Classifier.POLISH);
        map.put("/datasets/language-recognition/test/Polish2",Classifier.POLISH);
        map.put("/datasets/language-recognition/test/Polish3",Classifier.POLISH);
        map.put("/datasets/language-recognition/test/Polish4",Classifier.POLISH);
        map.put("/datasets/language-recognition/test/Polish5",Classifier.POLISH);
        map.put("/datasets/language-recognition/test/English1",Classifier.ENGLISH);
        map.put("/datasets/language-recognition/test/English2",Classifier.ENGLISH);
        map.put("/datasets/language-recognition/test/English3",Classifier.ENGLISH);
        map.put("/datasets/language-recognition/test/English4",Classifier.ENGLISH);
        map.put("/datasets/language-recognition/test/English5",Classifier.ENGLISH);

        return map;
    }
}
