package Day11;

import Util.FileReader;
import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> input = FileReader.fileLines("src/Day11/input.txt");

        ArrayList<Integer> emptyCols = new ArrayList<>();
        ArrayList<Integer> emptyRows = new ArrayList<>();

        for (int col = 0; col < input.get(0).length(); col++) {
            boolean empty = true;
            for (int row = 0; row < input.size(); row++) {
                if (input.get(row).charAt(col) != '.')  {
                    empty = false;
                    break;
                }
            }
            if (empty) emptyCols.add(col);
        }

        for (int row = 0; row < input.size(); row++) {
            if (!input.get(row).contains("#")) emptyRows.add(row);
        }

        ArrayList<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '#') galaxies.add(new Galaxy(i, j));
            }
        }

        System.out.println(part1(galaxies, emptyRows, emptyCols));
        System.out.println(part2(galaxies, emptyRows, emptyCols));
    }

    static long part1(ArrayList<Galaxy> galaxies, ArrayList<Integer> emptyRows, ArrayList<Integer> emptyCols) {
        long total = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                total += galaxies.get(j).distance(galaxies.get(i), 1, emptyRows, emptyCols);
            }
        }
        return total;
    }

    static long part2(ArrayList<Galaxy> galaxies, ArrayList<Integer> emptyRows, ArrayList<Integer> emptyCols) {
        long total = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                total += galaxies.get(j).distance(galaxies.get(i), 1000000 - 1, emptyRows, emptyCols);
            }
        }
        return total;
    }
}

class Galaxy {
    int row, col;
    Galaxy(int row, int col) {this.row = row; this.col = col;}

    long distance(Galaxy galaxy, int gravitationalWarp, ArrayList<Integer> emptyRows, ArrayList<Integer> emptyCols) {
        int distance = Math.abs(this.row - galaxy.row) + Math.abs(this.col - galaxy.col);
        for (int row : emptyRows) {
            if ((row > this.row && row < galaxy.row) || (row < this.row && row > galaxy.row))
                distance += gravitationalWarp;
        }
        for (int col : emptyCols) {
            if ((col > this.col && col < galaxy.col) || (col < this.col && col > galaxy.col))
                distance += gravitationalWarp;
        }
        return distance;
    }
}