package naive.kernels;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class BooleanKernel implements Kernel {

    @Override
    public double predict(double probability) {
        if (probability > 0.5) {
            return 1;
        } else {
            return 0;
        }
    }
}
