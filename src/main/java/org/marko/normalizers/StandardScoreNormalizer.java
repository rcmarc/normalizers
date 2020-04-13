package org.marko.normalizers;

/**
 * The standard score of a set of values is computed using the formula:
 * <pre>{@code
 *  z = (x - μ) / σ
 * }
 * </pre>
 * The {@link #apply(double[][])} is used to apply the formula to the given data
 */
public class StandardScoreNormalizer extends AbstractNumericNormalizer {

    /**
     * The means array of the data set
     */
    private final double[] means;

    /**
     * The standard deviation array of the data set
     */
    private final double[] standard_dev;

    public StandardScoreNormalizer(double[][] data) {
        super(data);
        means = mss.getMean();
        standard_dev = mss.getStandardDeviation();
    }

    @Override
    public double apply(double data, int pos) {
        return (data - means[pos]) / standard_dev[pos];
    }

}
