package org.marko.normalizers;

import org.apache.commons.math3.stat.descriptive.MultivariateSummaryStatistics;

import java.util.Arrays;
import java.util.stream.IntStream;


public abstract class AbstractNumericNormalizer implements NumericNormalizer {

    protected final MultivariateSummaryStatistics mss;

    protected final double[][] data;

    protected AbstractNumericNormalizer(double[][] data) {
        this.data = data;
        mss = new MultivariateSummaryStatistics(data[0].length, true);
        Arrays.stream(data).forEach(mss::addValue);
    }

    /**
     * This method simply calls the {@link #apply(double[][])} method
     *
     * @return The normalized data {@link double[][]}
     */
    public double[][] apply() {
        return apply(data);
    }

    /**
     * @param data The data to normalize
     * @return {@link double[][]} The normalized data with a standard score normalizer
     */
    public double[][] apply(double[][] data) {
        final double[][] normalized = new double[data.length][data[0].length];
        IntStream.range(0, data.length)
                .forEach(i -> IntStream.range(0, data[0].length)
                        .forEach(j -> normalized[i][j] = apply(data[i][j], j)));
        return normalized;
    }
}
