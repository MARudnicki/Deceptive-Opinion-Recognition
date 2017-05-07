package ug.neutral;

import javafx.util.Pair;

import java.security.SecureRandom;
import java.util.Random;

public class SampleData {

    private static Random random = new SecureRandom();

    public static Pair<double[][], double[]> generate() {
        double[][] res = new double[Parameters.ROWS.getValue()][Parameters.FEATURES.getValue()+1];

        double[] y = new double[Parameters.ROWS.getValue()];

        for (int i = 0; i < res.length; i++) {

                //features
                res[i][0] = random.nextInt(5);
                res[i][1] = random.nextInt(5);

                //y value
                y[i] = calcY(res[i]);
            res[i][2] = res[i][1];
            res[i][1] = res[i][0];
            res[i][0] = 1;

        }

        return new Pair<>(res, y);
    }

    private static double calcY(double res[]) {
        return 100 + res[0] * 5 - res[1] * 1;
    }
}
