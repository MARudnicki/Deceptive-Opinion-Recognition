package random;

import naive.preprocessors.RemoveSpecialCharsPreprocessor;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RandomTest {

    Random random = new SecureRandom();

    @Test
    public void myRandomTest(){

        String a = "ala\n12{3()k?ota!";

        System.out.println(Math.pow(3,2));

    }

}
