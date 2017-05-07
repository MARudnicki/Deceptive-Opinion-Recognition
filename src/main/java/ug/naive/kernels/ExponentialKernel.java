package ug.naive.kernels;

/**
 * Created by Maciej Rudnicki on 06/02/2017.
 */
public class ExponentialKernel implements Kernel{

    private final int power;

    public ExponentialKernel(int power) {
        this.power = power;
    }

    @Override
    public double predict(double probability) {
        return Math.pow(probability,power);
    }
}