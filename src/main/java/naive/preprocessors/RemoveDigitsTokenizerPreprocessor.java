package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveDigitsTokenizerPreprocessor implements TokenizerPreprocessor {

    @Override
    public String tokenize(String sentence) {
        return sentence.replaceAll("[0-9]"," ");

    }
}
