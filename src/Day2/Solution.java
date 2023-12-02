package Day2;

import java.util.ArrayList;
import Util.FileReader;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day2/input.txt");

        System.out.println(part1(lines));
        System.out.println(part2(lines));

    }

    static int part1(ArrayList<String> lines) {
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int[] maxRGBCounts = maxRBGCounts(line);
            if (maxRGBCounts[0] <= 12 && maxRGBCounts[1] <= 13 && maxRGBCounts[2] <= 14) sum += i + 1;
        }
        return sum;
    }

    static int part2(ArrayList<String> lines) {
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int[] maxRGBCounts = maxRBGCounts(line);
            sum += (maxRGBCounts[0]*maxRGBCounts[1]*maxRGBCounts[2]);
        }
        return sum;
    }

    private static int[] maxRBGCounts(String line) {
        int[] maxRGBCounts = {0, 0, 0};
        line = line.split(": ")[1];
        String[] handfuls = line.split("(, )|(; )");
        for (String handful : handfuls) {
            int count = Integer.parseInt(handful.split(" ")[0]);
            String color = handful.split(" ")[1];
            if (color.equals("red") && maxRGBCounts[0] < count) maxRGBCounts[0] = count;
            else if (color.equals("green") && maxRGBCounts[1] < count) maxRGBCounts[1] = count;
            else if (color.equals("blue") && maxRGBCounts[2] < count) maxRGBCounts[2] = count;
        }
        return maxRGBCounts;
    }
}