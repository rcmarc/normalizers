package org.marko.normalizers;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

class Utils {

    static String[][] joinData(String[] headers, double[][] data, String[] classes) {
        String[][] str = new String[data.length + 1][data[0].length + 1];
        str[0] = headers;
        IntStream.range(1, str.length).forEach(i ->
                IntStream.range(0, str[0].length).forEach(j -> {
                    if (j != str[0].length - 1)
                        str[i][j] = data[i - 1][j] + "";
                    else
                        str[i][j] = classes[i - 1];
                })
        );
        return str;
    }

    static void writeToCsv(CSVPrinter csv, String[][] data) throws IOException {
        Arrays.stream(data).forEach(array -> print(csv, array));
        csv.flush();
    }

    static String[][] loadFromCSV(CSVParser csv) throws IOException {
        var records = csv.getRecords();
        String[][] str = new String[records.size()][records.get(0).size()];
        for (int i = 0; i < records.size(); i++) {
            str[i] = getValues(records.get(i));
        }
        return str;
    }

    private static String[] getValues(CSVRecord record) {
        String[] str = new String[record.size()];
        Arrays.setAll(str, record::get);
        return str;
    }

    static String[] getClasses(String[][] str) {
        return IntStream.range(1, str.length).mapToObj(i -> str[i][str[i].length - 1]).toArray(String[]::new);
    }

    static double[][] getData(String[][] str) {
        double[][] data = new double[str.length - 1][str[0].length - 1];
        IntStream.range(0, str.length - 1).forEach(i ->
                data[i] = IntStream.range(0, str[0].length - 1).mapToDouble(j -> Double.parseDouble(str[i + 1][j])).toArray()
        );
        return data;
    }

    static void print(CSVPrinter csv, String[] values) {
        try {
            for (var str : values) {
                csv.print(str);
            }
            csv.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
