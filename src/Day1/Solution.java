package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/Day1/input.txt");
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
        String[] numStrings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        for (String line : lines) {
            int first = 0;
            int last = 0;

            for (int i = 0; i < line.length(); i++) {
                int digit = 0;
                for (int j = 0; j < numStrings.length; j++) {
                    String str = numStrings[j];
                    if (i + str.length() <= line.length()) {
                        if (line.substring(i, i + str.length()).equals(str)) {
                            digit = (j % 9) + 1;
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