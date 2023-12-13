package Day12;

import Util.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        ArrayList<String> input = FileReader.fileLines("src/Day12/input.txt");

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    static long part1(ArrayList<String> input) {
        long total = 0;
        for (String line : input) {
            String row = line.split(" ")[0];
            String[] recordsStr = line.split(" ")[1].split(",");
            Integer[] records = new Integer[recordsStr.length];
            for (int i = 0; i < recordsStr.length; i++) records[i] = Integer.parseInt(recordsStr[i]);
            HashMap<State, Long> cache = new HashMap<>();

            total += countArrangements(row, Arrays.asList(records), cache);
        }
        return total;
    }

    static long part2(ArrayList<String> input) {
        long total = 0;
        for (String line : input) {
            String row = line.split(" ")[0] + "?" + line.split(" ")[0] + "?" + line.split(" ")[0] + "?" + line.split(" ")[0] + "?" + line.split(" ")[0];
            String[] recordsStr = line.split(" ")[1].split(",");
            Integer[] records = new Integer[recordsStr.length * 5];
            for (int i = 0; i < recordsStr.length * 5; i++)
                records[i] = Integer.parseInt(recordsStr[i % recordsStr.length]);
            HashMap<State, Long> cache = new HashMap<>();

            total += countArrangements(row, Arrays.asList(records), cache);
        }
        return total;
    }

    static long countArrangements(String condition, List<Integer> records, HashMap<State, Long> cache) {
        State state = new State(condition, records);
        if (cache.containsKey(state))
            return cache.get(state);

        if (condition.length() == 0) {
            if (records.isEmpty()) return 1;
            return 0;
        }

        long arrangements = 0;
        char firstChar = condition.charAt(0);
        if (firstChar == '.') arrangements = countArrangements(condition.substring(1), records, cache);
        else if (firstChar == '?') {
            arrangements += countArrangements("." + condition.substring(1), records, cache)
                    + countArrangements("#" + condition.substring(1), records, cache);
        }
        else if (firstChar == '#') {
            if (records.isEmpty()) {
                cache.put(state, Long.valueOf(0));
                return 0;
            }

            int damageLength = records.get(0);
            List<Integer> newRecords = records.subList(1, records.size());

            if (damageLength > condition.length()) {
                cache.put(state, Long.valueOf(0));
                return 0;
            }

            for (int i = 0; i < damageLength && i < condition.length(); i++) {
                if (condition.charAt(i) == '.') {
                    cache.put(state, Long.valueOf(0));
                    return 0;
                }
            }

            if (damageLength == condition.length()) {
                if (newRecords.isEmpty()) arrangements = 1;
                else arrangements = 0;
            }

            else if (condition.charAt(damageLength) == '#') arrangements = 0;

            else if (condition.charAt(damageLength) == '.')
                arrangements = countArrangements(condition.substring(damageLength + 1), newRecords, cache);

            else arrangements =
                        countArrangements('.' + condition.substring(damageLength + 1), newRecords, cache);
        }

        cache.put(state, arrangements);
        return arrangements;
    }
}

record State (String condition, List<Integer> records) {}