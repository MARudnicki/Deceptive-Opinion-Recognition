package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveDotAndCommaTokenizerPreprocessor implements TokenizerPreprocessor {

    @Override
    public String tokenize(String sentence) {
        sentence = sentence.replaceAll(","," ");
        sentence = sentence.replaceAll("\\."," ");

        return sentence;
    }
}
