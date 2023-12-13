package Day13;

import Util.FileReader;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day13/input.txt");

        ArrayList<ArrayList<String>> unparsedPatterns = new ArrayList<>();
        ArrayList<String> curPattern = new ArrayList<>();
        for (String line : lines) {
            if (line.equals("")) {
                unparsedPatterns.add(curPattern);
                curPattern = new ArrayList<>();
                continue;
            }
            curPattern.add(line);
        } unparsedPatterns.add(curPattern);

        ArrayList<Pattern> patterns = new ArrayList<>();
        for (ArrayList<String> unparsedPattern : unparsedPatterns) {
            char[][] rows = new char[unparsedPattern.size()][unparsedPattern.get(0).length()];
            char[][] cols = new char[unparsedPattern.get(0).length()][unparsedPattern.size()];

            for (int row = 0; row < unparsedPattern.size(); row++) {
                for (int col = 0; col < unparsedPattern.get(0).length(); col++) {
                    char c = unparsedPattern.get(row).charAt(col);
                    rows[row][col] = c;
                    cols[col][row] = c;
                }
            }
            patterns.add(new Pattern(rows, cols));
        }

        System.out.println(part1(patterns));
        System.out.println(part2(patterns));
    }

    static long part1(ArrayList<Pattern> patterns) {
        long total = 0;
        for (Pattern pattern : patterns) {
            char[][] rows = pattern.rows();
            char[][] cols = pattern.cols();

            long patternScore = 0;
            int rowsMirror = findMirror(rows);
            int colsMirror = findMirror(cols);

            if (rowsMirror >= 0) patternScore += 100*(rowsMirror + 1);
            if (colsMirror >= 0) patternScore += (colsMirror + 1);

            total += patternScore;
        }
        return total;
    }

    static long part2(ArrayList<Pattern> patterns) {
        long total = 0;
        for (Pattern pattern : patterns) {
            char[][] rows = pattern.rows();
            char[][] cols = pattern.cols();

            long patternScore = 0;
            int rowsSmudgedMirror = findSmudgedMirror(rows);
            int colsSmudgedMirror = findSmudgedMirror(cols);

            if (rowsSmudgedMirror >= 0) patternScore += 100*(rowsSmudgedMirror + 1);
            if (colsSmudgedMirror >= 0) patternScore += (colsSmudgedMirror + 1);

            total += patternScore;
        }
        return total;
    }

    static int findMirror(char[][] arr) {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < arr.length - 1; i++) if (Arrays.equals(arr[i], arr[i + 1])) candidates.add(i);

        for (Integer candidate : candidates) {
            int lower = candidate;
            int upper = candidate + 1;
            boolean isMirror = true;
            while (lower >= 0 && upper < arr.length) {
                if (!Arrays.equals(arr[lower], arr[upper])) {
                    isMirror = false;
                    break;
                }
                lower--; upper++;
            }
            if (isMirror) {
                return candidate;
            }
        }
        return -1;
    }

    static int findSmudgedMirror(char[][] arr) {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < arr.length - 1; i++) {
            if (Arrays.equals(arr[i], arr[i + 1]) || offByASmudge(arr[i], arr[i + 1])) candidates.add(i);
        }

        for (Integer candidate : candidates) {
            int lower = candidate;
            int upper = candidate + 1;
            boolean isMirror = true;
            boolean usedSmudge = false;
            while (lower >= 0 && upper < arr.length) {
                if (!Arrays.equals(arr[lower], arr[upper])) {
                    if (usedSmudge || !offByASmudge(arr[lower], arr[upper])) {
                        isMirror = false;
                        break;
                    }
                    usedSmudge = true;
                }
                lower--; upper++;
            }
            if (isMirror && usedSmudge) {
                return candidate;
            }
        }
        return -1;
    }

    static boolean offByASmudge(char[] arr1, char[] arr2) {
        int deviations = 0;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) deviations++;
        }
        return (deviations == 1);
    }
}

record Pattern(char[][] rows, char[][] cols){}
