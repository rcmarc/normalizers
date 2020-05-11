package com.github.rcmarc.normalizers;

import org.apache.commons.math3.stat.descriptive.MultivariateSummaryStatistics;

import java.util.Arrays;

/**
 * The standard score of a set of values is computed using the formula:
 * <pre>{@code
 *  z = (x - μ) / σ
 * }
 * </pre>
 * The {@link #apply()} method is used to apply the formula to the given data
 */
public class StandardScoreNormalizer extends AbstractNumericNormalizer {


    /**
     * Helper apache statistics
     */
    private final MultivariateSummaryStatistics mss;

    public StandardScoreNormalizer(double[][] data) {
        super(data);
        mss = new MultivariateSummaryStatistics(data[0].length, true);
        Arrays.stream(data).forEach(mss::addValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[][] apply() {

        final double[] means = mss.getMean();
        final double[] std = mss.getStandardDeviation();
        if (data.length > 10000) {
            return parallel(data, means, std);
        }

        return sequential(data, means, std);
    }

    /**
     *
     * @param data the data to be normalized
     * @return {@code double[][]} the normalized data
     */
    public static double[][] apply(double[][] data) {
        return new StandardScoreNormalizer(data).apply();
    }

    private double[][] parallel(double[][] data, double[] means, double[] std) {
        return Arrays.stream(data).parallel().map(arr -> apply(arr, means, std)).toArray(double[][]::new);
    }

    private double[] apply(double[] data, double[] means, double[] std) {
        double[] normalized = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            normalized[i] = (data[i] - means[i]) / std[i];
        }
        return normalized;
    }

    private double[][] sequential(double[][] data, double[] means, double[] std) {
        final double[][] normalized = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                normalized[i][j] = (data[i][j] - means[j]) / std[j];
            }
        }
        return normalized;
    }
}
