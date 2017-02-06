package naive.preprocessors;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RemoveSpecialCharsPreprocessor implements Preprocessor{

    @Override
    public String process(String sentence) {
        return sentence.replaceAll("[\"\\t\\n\\r\\f!\\\"#%&()*+,./:;<=>?@[\\\\]^-_`{|}~\"]"," ");
    }
}
