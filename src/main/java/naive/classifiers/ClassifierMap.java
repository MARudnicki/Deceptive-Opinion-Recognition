package naive.classifiers;

import java.util.HashMap;

/**
 * Created by Maciej Rudnicki on 02/02/2017.
 */
public class ClassifierMap<K, V> extends HashMap{

    public ClassifierMap() {
        for(Classifier c: Classifier.values()){
            this.put(c, 0);
        }
    }
}
