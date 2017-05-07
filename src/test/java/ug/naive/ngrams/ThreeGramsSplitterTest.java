package ug.naive.ngrams;

import org.junit.Test;

import java.util.List;
import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreeGramsSplitterTest {

    private String sentence;
    private List<String> tokens;

    private NGramsSplitter nGramsSplitter = new ThreeGramsSplitter();


    @Test
    public void split() throws Exception {

        Given:
        sentence = "This is 5th words sentence";

        When:
        tokens = nGramsSplitter.split("This is 5th words sentence");

        Then:
        assertThat(tokens, hasSize(3));
        assertThat(tokens.get(2), is("5th words sentence"));

    }

}