package Day9;

import Util.FileReader;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.math.BigDecimal;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day9/input.txt");

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static long part1(ArrayList<String> lines) {
        long total = 0;
        for (String line : lines) {
            long[][] values = parseLine(line);
            total += interpolate(values[0], values[1], values[0].length);
        }
        return total;
    }

    static long part2(ArrayList<String> lines) {
        long total = 0;
        for (String line : lines) {
            long[][] values = parseLine(line);
            total += interpolate(values[0], values[1], -1);
        }
        return total;
    }

    static long interpolate(long[] xValues, long[] yValues, long evalX) {
        int maxDegree = xValues.length;
        BigDecimal result = new BigDecimal(0);

        for (int i = 0; i < maxDegree; i++) {
            BigDecimal term = new BigDecimal(yValues[i]);
            for (int j = 0; j < maxDegree; j++) {
                if (j != i)  {
                    term = term.multiply(new BigDecimal(evalX - xValues[j]));
                    term = term.divide(new BigDecimal(xValues[i] - xValues[j]), 1000, RoundingMode.HALF_UP);
                }
            }
            result = result.add(term);
        }
        result = result.setScale(0, RoundingMode.HALF_UP);
        return result.longValue();
    }

    static long[][] parseLine(String line) {
        String[] splitLine = line.split(" ");

        long[] yValues = new long[splitLine.length];
        for (int i = 0; i < splitLine.length; i++) yValues[i] = Long.parseLong(splitLine[i]);

        long[] xValues = new long[yValues.length];
        for (int i = 0; i < yValues.length; i++) xValues[i] = i;

        return new long[][]{xValues, yValues};
    }
}
