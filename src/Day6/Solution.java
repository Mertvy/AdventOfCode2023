package Day6;

import Util.FileReader;

import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day6/input.txt");

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static int part1(ArrayList<String> lines) {
        int answer = 1;
        String[] timeStrings = lines.get(0).split(":")[1].strip().split(" +");
        int[] times = new int[timeStrings.length];
        for (int i = 0; i < timeStrings.length; i++) times[i] = Integer.parseInt(timeStrings[i]);

        String[] distanceStrings = lines.get(1).split(":")[1].strip().split(" +");
        int[] distances = new int[timeStrings.length];
        for (int i = 0; i < distanceStrings.length; i++) distances[i] = Integer.parseInt(distanceStrings[i]);

        for (int i = 0; i < distances.length; i++) {
            int time = times[i], distance = distances[i];

            int upper = (int) Math.ceil(0.5*(time+Math.sqrt(time*time-4*distance)) - 1);
            int lower = (int) Math.floor(0.5*(time-Math.sqrt(time*time-4*distance)) + 1);

            answer *= (upper - lower + 1);
        }
        return answer;
    }

    static long part2(ArrayList<String> lines) {
        String timeString = lines.get(0).split(":")[1].strip().replace(" ", "");
        long time = Long.parseLong(timeString);
        String distanceString = lines.get(1).split(":")[1].strip().replace(" ", "");
        long distance = Long.parseLong(distanceString);

        long upper = (int) Math.ceil(0.5*(time+Math.sqrt(time*time-4*distance)) - 1);
        long lower = (int) Math.floor(0.5*(time-Math.sqrt(time*time-4*distance)) + 1);

        return upper - lower + 1;
    }
}
