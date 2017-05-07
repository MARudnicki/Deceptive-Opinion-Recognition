package ug.neutral;

import com.google.common.base.Preconditions;
import javafx.util.Pair;
import ug.MachineLearningEngine;
import ug.naive.NaiveDataContainer;
import org.la4j.Matrix;
import org.la4j.Vector;

public class NeutralEngine implements MachineLearningEngine {

    /**
     * Normal Equasion
     * θ=(XT * X)^−1 * XT * y
     */
    private NaiveDataContainer dataSetContainer;
    private double alpha;

    private NeutralEngine(Builder builder) {
        this.dataSetContainer = builder.dataSetContainer;
        this.alpha = builder.alpha;
    }

    public static Builder newNeutralEngine() {
        return new Builder();
    }

    @Override
    public Enum predict(String sentence){

        run();

        return null;
    }

    private void run(){
        Pair<double[][], double[]> data = SampleData.generate();

        double[][] x = data.getKey();
        double[] y = data.getValue();

        Matrix xMatrix = Matrix.from2DArray(x);
        Vector yVector = Vector.fromArray(y);

        Vector theta = Vector.fromArray(new double[]{10, 5, 1});

        double ans = costFunction(xMatrix, yVector, theta);
        System.out.println(ans);

        for (int i = 0; i < 1000; i++) {
            theta = gradientDescent(xMatrix, yVector, theta);
            System.out.println(" theta " + theta.toString());
            double cost = costFunction(xMatrix, yVector, theta);
            System.out.println(" Cost " + cost);
        }
    }

    private double costFunction(Matrix x, Vector y, Vector theta) {
        int m = y.length();
        Matrix temp = x.multiply(theta).subtract(y).toRowMatrix();

        return (1.0 / (2.0 * m)) * temp.multiplyByItsTranspose().get(0, 0);

    }

    private Vector gradientDescent(Matrix x, Vector y, Vector theta) {
        int m = y.length();

        for (int i = 0; i < theta.length(); i++) {

            //theta[0] -
            theta.set(i, theta.get(i) - alpha * (1.0 / m) * x.multiply(theta).subtract(y).toRowMatrix().multiply(x
                    .getColumn
                            (i))
                    .sum());
        }
        return theta;
    }


    public static final class Builder {
        private NaiveDataContainer dataSetContainer;
        private double alpha;

        private Builder() {
        }

        public NeutralEngine build() {
            Preconditions.checkState(dataSetContainer != null, "dataSetContainer cannot be null");
            Preconditions.checkState(alpha != 0, "alpha has to be declared");

            return new NeutralEngine(this);
        }

        public Builder dataSetContainer(NaiveDataContainer dataSetContainer) {
            this.dataSetContainer = dataSetContainer;
            return this;
        }

        public Builder alpha(double alpha) {
            this.alpha = alpha;
            return this;
        }
    }
}

