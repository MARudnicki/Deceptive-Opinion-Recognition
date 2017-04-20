package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 05/02/2017.
 */
public class IgnoreCaseTokenizerPreprocessor implements TokenizerPreprocessor {

    @Override
    public String tokenize(String sentence) {
        return sentence.toLowerCase();
    }

}
