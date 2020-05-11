package com.github.rcmarc.normalizers;


public abstract class AbstractNumericNormalizer implements NumericNormalizer {

    protected final double[][] data;

    protected AbstractNumericNormalizer(double[][] data) {
        this.data = data;
    }

    /**
     *
     * @return The normalized data
     */
    @Override
    public abstract double[][] apply();
}
