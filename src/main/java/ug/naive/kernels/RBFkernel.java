package ug.naive.kernels;

/**
 * Created by Maciej Rudnicki on 08/02/2017.
 */
public class RBFkernel implements Kernel{

    @Override
    public double predict(double probability) {
        return Math.exp(probability);
    }
}
