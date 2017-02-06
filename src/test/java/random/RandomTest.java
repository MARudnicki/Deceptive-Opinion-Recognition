package random;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class RandomTest {

    Random random = new SecureRandom();

    @Test
    public void myRandomTest(){

        Map<Integer, String > map = new HashMap<>();

        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"c");
        map.put(4,"d");
        map.put(5,"e");


    }

}
