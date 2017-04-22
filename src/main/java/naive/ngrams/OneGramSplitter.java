package naive.ngrams;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class OneGramSplitter implements NGramsSplitter {

    @Override
    public List<String> split(String sentence) {

        return Arrays.stream(sentence.split(" ")).map(String::trim).collect(toList());
    }
}
