package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveQuotationTokenizerPreprocessor implements TokenizerPreprocessor {

    @Override
    public String tokenize(String sentence) {
        sentence = sentence.replace("'"," ");
        sentence = sentence.replace("\""," ");
        return sentence;
    }
}
