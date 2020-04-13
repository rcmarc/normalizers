package org.marko.normalizers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Main {

    static String FILENAME = "./csv/15s";
    static String EXT = ".csv";

    public static void main(String[] args) throws IOException {
        CSVParser csv = new CSVParser(new FileReader(FILENAME + EXT), CSVFormat.DEFAULT);
        String[][] str = Utils.loadFromCSV(csv);
        csv.close();

        double[][] data = new StandardScoreNormalizer(Utils.getData(str)).apply();
        CSVPrinter printer = new CSVPrinter(new FileWriter(FILENAME + "StdNorm" + EXT),CSVFormat.DEFAULT);
        Utils.writeToCsv(printer,Utils.joinData(
                str[0],
                data,
                Utils.getClasses(str)
        ));
        printer.close();
    }

}
