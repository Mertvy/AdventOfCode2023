package Day5;

import Util.FileReader;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day5/input.txt");

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static long part1(ArrayList<String> lines) {

        Map[] maps = new Map[7];
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals("seed-to-soil map:")) maps[0] = new Map(createMapInput(lines, i));
            if (lines.get(i).equals("soil-to-fertilizer map:")) maps[1] = new Map(createMapInput(lines, i));
            if (lines.get(i).equals("fertilizer-to-water map:")) maps[2] = new Map(createMapInput(lines, i));
            if (lines.get(i).equals("water-to-light map:")) maps[3] = new Map(createMapInput(lines, i));
            if (lines.get(i).equals("light-to-temperature map:")) maps[4] = new Map(createMapInput(lines, i));
            if (lines.get(i).equals("temperature-to-humidity map:")) maps[5] = new Map(createMapInput(lines, i));
            if (lines.get(i).equals("humidity-to-location map:")) maps[6] = new Map(createMapInput(lines, i));
        }

        String[] seedsString = lines.get(0).split(Pattern.quote(": "))[1].split(" ");
        long[] seeds = new long[seedsString.length];
        for (int i = 0; i < seedsString.length; i++) seeds[i] = Long.parseLong(seedsString[i]);

        long min = seeds[0];
        for (long seed : seeds) {
            for (Map map : maps) {
                seed = map.transform(seed);
            }
            min = Math.min(min, seed);
        }
        return min;
    }

    static long part2(ArrayList<String> lines) {
        ArrayList<long[]> seedRanges = generateSeeds(lines.get(0));

        Map[] reverseMaps = new Map[7];
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals("seed-to-soil map:")) reverseMaps[0] = new Map(createMapInput(lines, i), true);
            if (lines.get(i).equals("soil-to-fertilizer map:")) reverseMaps[1] = new Map(createMapInput(lines, i), true);
            if (lines.get(i).equals("fertilizer-to-water map:")) reverseMaps[2] = new Map(createMapInput(lines, i), true);
            if (lines.get(i).equals("water-to-light map:")) reverseMaps[3] = new Map(createMapInput(lines, i), true);
            if (lines.get(i).equals("light-to-temperature map:")) reverseMaps[4] = new Map(createMapInput(lines, i), true);
            if (lines.get(i).equals("temperature-to-humidity map:")) reverseMaps[5] = new Map(createMapInput(lines, i), true);
            if (lines.get(i).equals("humidity-to-location map:")) reverseMaps[6] = new Map(createMapInput(lines, i), true);
        }
        for (long location = 0; ; location++) {
            long value = location;
            for (int i = 6; i >= 0; i--) {
                value = reverseMaps[i].transform(value);
            }

            for (long[] range : seedRanges) {
                if (value >= range[0] && value < range[1]) return location;
            }
        }
    }

    static ArrayList<String> createMapInput(ArrayList<String> lines, int index) {
        ArrayList<String> mapInput = new ArrayList<>();
        index++;
        while (index < lines.size() && !lines.get(index).equals("")) {
            mapInput.add(lines.get(index));
            index++;
        }
        return mapInput;
    }

    static ArrayList<long[]> generateSeeds(String seedString) {
        seedString = seedString.split(Pattern.quote(": "))[1];
        String[] split = seedString.split(" ");

        ArrayList<long[]> seedRanges = new ArrayList<>(split.length/2);
        long minSeed = Long.MAX_VALUE;
        for (int i = 0; i < split.length; i += 2) {
            long start = Long.parseLong(split[i]);
            long range = Long.parseLong(split[i + 1]);

            minSeed = Math.min(start, minSeed);
            seedRanges.add(new long[] {start, start + range});
        }
        return seedRanges;
    }
}

class Map {
    private final long[][] bounds;
    private final long[] shift;

    Map(ArrayList<String> input){
        bounds = new long[input.size()][2];
        shift = new long[bounds.length];
        for (int i = 0; i < input.size(); i++) {
            String[] splitLine = input.get(i).split(" ");

            long source = Long.parseLong(splitLine[1]);
            long destination = Long.parseLong(splitLine[0]);
            long range = Long.parseLong(splitLine[2]);

            long[] bound = {source, source + range};
            bounds[i] = bound;
            shift[i] = destination - source;
        }
    }

    Map(ArrayList<String> input, boolean reverse) {
        bounds = new long[input.size()][2];
        shift = new long[bounds.length];

        for (int i = 0; i < input.size(); i++) {
            String[] splitLine = input.get(i).split(" ");

            long source = Long.parseLong(splitLine[1]);
            long destination = Long.parseLong(splitLine[0]);
            long range = Long.parseLong(splitLine[2]);

            long[] bound = {destination, destination + range};
            bounds[i] = bound;
            shift[i] = source - destination;
        }
    }

    long transform(long source) {
        for (int i = 0; i < bounds.length; i++)
            if (source >= bounds[i][0] && source < bounds[i][1])
                return source + shift[i];
        return source;
    }
}
