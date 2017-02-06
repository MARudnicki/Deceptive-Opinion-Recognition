package naive.kernels;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class LinearKernel implements Kernel {
    @Override
    public double predict(double probability) {
        return probability;
    }
}
