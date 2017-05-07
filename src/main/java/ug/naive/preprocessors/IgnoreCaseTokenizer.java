package ug.naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 05/02/2017.
 */
public class IgnoreCaseTokenizer implements Tokenizer {

    @Override
    public String process(String sentence) {
        return sentence.toLowerCase();
    }

}
