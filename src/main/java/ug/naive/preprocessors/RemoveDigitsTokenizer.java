package ug.naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveDigitsTokenizer implements Tokenizer {

    @Override
    public String process(String sentence) {
        return sentence.replaceAll("[0-9]"," ");

    }
}
