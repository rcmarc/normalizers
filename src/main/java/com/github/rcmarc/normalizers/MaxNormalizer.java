package com.github.rcmarc.normalizers;

/**
 * The max normalizer method simply divides all the values in the data set
 * to the max value
 */
public class MaxNormalizer extends AbstractNumericNormalizer {
    private final double[] max;

    public MaxNormalizer(double[][] data) {
        super(data);
        max = mss.getMax();
    }

    @Override
    public double apply(double data, int pos) {
        return data / max[pos];
    }
}
