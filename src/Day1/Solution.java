package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day1 {

    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
                lines.add(myReader.nextLine());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(part1(lines));
        System.out.println(part2(lines));

    }

    static int part1(ArrayList<String> lines) {
        int sum = 0;
        for (String line : lines) {
            char first = 0;
            char last = 0;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (Character.isDigit(c)) {
                    if (first == 0) first = c;
                    last = c;
                }
            }
            int calibrationValue = 10*(first - '0') + (last - '0');
            sum += calibrationValue;
        }
        return sum;
    }

    static int part2(ArrayList<String> lines) {
        int sum = 0;
        HashMap<String, Integer> numsMap = new HashMap<>();
        numsMap.put("1", 1);
        numsMap.put("2", 2);
        numsMap.put("3", 3);
        numsMap.put("4", 4);
        numsMap.put("5", 5);
        numsMap.put("6", 6);
        numsMap.put("7", 7);
        numsMap.put("8", 8);
        numsMap.put("9", 9);
        numsMap.put("one", 1);
        numsMap.put("two", 2);
        numsMap.put("three", 3);
        numsMap.put("four", 4);
        numsMap.put("five", 5);
        numsMap.put("six", 6);
        numsMap.put("seven", 7);
        numsMap.put("eight", 8);
        numsMap.put("nine", 9);

        for (String line : lines) {
            int first = 0;
            int last = 0;

            for (int i = 0; i < line.length(); i++) {
                int digit = 0;
                for (String key : numsMap.keySet()) {
                    if (i + key.length() <= line.length()) {
                        if (line.substring(i, i + key.length()).equals(key)) {
                            digit = numsMap.get(key);
                            break;
                        }
                    }
                }
                if (first == 0) first = digit;
                if (digit != 0) last = digit;
            }
            sum += 10*first + last;
        }
        return sum;
    }
}