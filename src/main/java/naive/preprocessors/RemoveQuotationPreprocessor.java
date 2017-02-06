package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveQuotationPreprocessor implements Preprocessor{

    @Override
    public String process(String sentence) {
        sentence = sentence.replace("'"," ");
        sentence = sentence.replace("\""," ");
        return sentence;
    }
}
