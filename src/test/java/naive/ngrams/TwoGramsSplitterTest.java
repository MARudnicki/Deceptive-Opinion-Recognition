package naive.ngrams;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TwoGramsSplitterTest {

    private String sentence;
    private List<String> tokens;

    private NGramsSplitter nGramsSplitter = new TwoGramsSplitter();


    @Test
    public void split() throws Exception {

        Given:
        sentence = "This is 5th words sentence";

        When:
        tokens = nGramsSplitter.split("This is 5th words sentence");

        Then:
        assertThat(tokens, hasSize(4));
        assertThat(tokens.get(3), is("words sentence"));

    }

}