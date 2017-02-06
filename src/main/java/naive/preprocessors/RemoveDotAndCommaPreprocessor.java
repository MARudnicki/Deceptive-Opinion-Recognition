package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveDotAndCommaPreprocessor implements Preprocessor{

    @Override
    public String process(String sentence) {
        sentence = sentence.replaceAll(","," ");
        sentence = sentence.replaceAll("\\."," ");

        return sentence;
    }
}
