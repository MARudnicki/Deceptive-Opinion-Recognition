package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveSpecialCharsTokenizer implements Tokenizer {

    @Override
    public String process(String sentence) {
        return sentence.replaceAll("[\"\\t\\n\\r\\f!\\\"#%&()*+,./:;<=>?@[\\\\]^-_`{|}~\"]"," ");
    }
}
