package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveSpecialCharsTokenizerPreprocessor implements TokenizerPreprocessor {

    @Override
    public String tokenize(String sentence) {
        return sentence.replaceAll("[\"\\t\\n\\r\\f!\\\"#%&()*+,./:;<=>?@[\\\\]^-_`{|}~\"]"," ");
    }
}
