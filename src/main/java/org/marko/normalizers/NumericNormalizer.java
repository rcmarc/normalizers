package org.marko.normalizers;

/**
 * Normalizer interface
 */
public interface NumericNormalizer {

    /**
     *
     * @param data The value to process
     * @param pos The column position of the data
     * @return the data transformed
     */
    double apply(double data, int pos);

}
