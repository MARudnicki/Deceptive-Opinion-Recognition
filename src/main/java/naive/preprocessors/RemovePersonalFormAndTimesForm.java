package naive.preprocessors;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Maciej Rudnicki on 07/02/2017.
 */
public class RemovePersonalFormAndTimesForm implements Preprocessor {
    @Override
    public String process(String sentence) {

            Collection<String> words =  Arrays.stream(sentence.split(" "))
                    .map(String::trim)
                    .collect(Collectors.toList());

            StringBuilder stringBuilder = new StringBuilder();
            for(String word : words){
                if(word.endsWith("s")){
                    word = word.substring(0, word.length()-1);
                }else if(word.endsWith("ed")){
                    word = word.substring(0, word.length()-2);
                }else if(word.endsWith("ing")){
                    word = word.substring(0, word.length()-3);
                }
                stringBuilder.append(word).append(" ");
            }
            return stringBuilder.toString();
    }
}
