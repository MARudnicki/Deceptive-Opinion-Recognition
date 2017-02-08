package random;

import naive.preprocessors.RemoveDotAndCommaPreprocessor;
import naive.preprocessors.RemovePersonalFormAndTimesForm;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RandomTest {

    Random random = new SecureRandom();

    @Test
    public void myRandomTest(){

        System.out.println(2.71*2.71);
        System.out.println(Math.exp(2));
    }

}
