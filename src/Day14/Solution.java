package Day14;

import Util.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day14/input.txt");

        char[][] platform = new char[lines.size()][lines.get(0).length()];
        for (int row = 0; row < platform.length; row++) {
            for (int col = 0; col < platform[0].length; col++) {
                platform[row][col] = lines.get(row).charAt(col);
            }
        }

        System.out.println(part1(platform));
        System.out.println(part2(platform));
    }

    static long part1(char[][] platform) {
        tiltNorth(platform);
        return detectLoad(platform);
    }

    static long part2(char[][] platform) {
        final int BILLION = 1000000000;
        HashMap<Platform, Integer> map = new HashMap<>();
        HashMap<Integer, Platform> reverseMap = new HashMap<>();
        for (int i = 0; i < BILLION; i++) {
            cycle(platform);
            Platform cur = new Platform(platform);
            if (!map.containsKey(cur)) {
                map.put(cur, i);
                reverseMap.put(i, cur);
            } else {
                int cycleLength = i - map.get(cur);
                i = map.get(cur) + BILLION - 1 - (i + cycleLength * (int) Math.floor((BILLION - 1 - i) / cycleLength));

                return detectLoad(reverseMap.get(i).platform);

            }
        }
        return detectLoad(platform);
    }

    static void tiltNorth(char[][] platform) {
        for (int col = 0; col < platform[0].length; col++) {
            int placementIndex = 0;
            for (int row = 0; row < platform.length; row++) {
                if (platform[row][col] == '#') {
                    placementIndex = row + 1;
                } else if (platform[row][col] == 'O') {
                    platform[row][col] = '.';
                    platform[placementIndex][col] = 'O';
                    placementIndex++;
                }
            }
        }
    }

    static void tiltWest(char[][] platform) {
        for (int row = 0; row < platform.length; row++) {
            int placementIndex = 0;
            for (int col = 0; col < platform[0].length; col++) {
                if (platform[row][col] == '#') {
                    placementIndex = col + 1;
                } else if (platform[row][col] == 'O') {
                    platform[row][col] = '.';
                    platform[row][placementIndex] = 'O';
                    placementIndex++;
                }
            }
        }
    }

    static void tiltSouth(char[][] platform) {
        for (int col = 0; col < platform[0].length; col++) {
            int placementIndex = platform.length - 1;
            for (int row = platform.length - 1; row >= 0; row--) {
                if (platform[row][col] == '#') {
                    placementIndex = row - 1;
                } else if (platform[row][col] == 'O') {
                    platform[row][col] = '.';
                    platform[placementIndex][col] = 'O';
                    placementIndex--;
                }
            }
        }
    }

    static void tiltEast(char[][] platform) {
        for (int row = 0; row < platform.length; row++) {
            int placementIndex = platform[0].length - 1;
            for (int col = platform[0].length - 1; col >= 0; col--) {
                if (platform[row][col] == '#') {
                    placementIndex = col - 1;
                } else if (platform[row][col] == 'O') {
                    platform[row][col] = '.';
                    platform[row][placementIndex] = 'O';
                    placementIndex--;
                }
            }
        }
    }

    static void cycle(char[][] platform) {
        tiltNorth(platform);
        tiltWest(platform);
        tiltSouth(platform);
        tiltEast(platform);
    }

    static long detectLoad(char[][] platform) {
        long load = 0;
        for (int row = 0; row < platform.length; row++) {
            for (int col = 0; col < platform[row].length; col++) {
                if (platform[row][col] == 'O') load += platform.length - row;
            }
        }
        return load;
    }
}
class Platform {
    char[][] platform;

    Platform(char[][] platform) {
        this.platform = new char[platform.length][];
        for (int i = 0; i < platform.length; i++) this.platform[i] = Arrays.copyOf(platform[i], platform[i].length);
    }

    @Override
    public boolean equals(Object o) {
        Platform platform = (Platform) o;
        for (int i = 0; i < this.platform.length; i++) {
            for (int j = 0; j < this.platform[0].length; j++) {
                if (this.platform[i][j] != platform.platform[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (int i = 0; i < this.platform.length; i++) {
            for (int j = 0; j < this.platform[0].length; j++) {
                hashCode += 2 * i + 3 * j + i + j;
            }
        }
        return hashCode;
    }
}