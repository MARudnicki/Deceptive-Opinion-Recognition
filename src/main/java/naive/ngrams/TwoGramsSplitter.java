package naive.ngrams;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TwoGramsSplitter implements NGramsSplitter {

    @Override
    public List<String> split(String sentence) {
        List<String> ans = Lists.newArrayList();

        List<String> tokens = Arrays.stream(sentence.split(" ")).map(String::trim).collect(toList());

        if(tokens.size() < 2){
            return Collections.singletonList(sentence);
        }

        for (int i = 1; i < tokens.size(); i++) {
            ans.add(String.join(" ", tokens.get(i - 1), tokens.get(i)));
        }
        return ans;
    }
}
