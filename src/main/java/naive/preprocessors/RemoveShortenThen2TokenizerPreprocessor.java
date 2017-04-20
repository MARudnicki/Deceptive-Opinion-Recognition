package naive.preprocessors;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveShortenThen2TokenizerPreprocessor implements TokenizerPreprocessor {

    @Override
    public String tokenize(String sentence) {
        return Arrays.stream(sentence.split(" "))
                .map(String::trim)
                .filter(el -> el.length()>2)
                .collect(Collectors.joining(" "));
    }
}
