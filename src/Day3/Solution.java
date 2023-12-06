package Day3;

import Util.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day3/input.txt");
        Tools.padInput(lines, '.');

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static int part1(ArrayList<String> lines) {
        int sum = 0;
        for (int i = 1; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            for (int j = 1; j < line.length() - 1; j++) {
                if (Character.isDigit(line.charAt(j))) {
                    boolean adjacentSymbol = false;
                    StringBuilder number = new StringBuilder();
                    for (int k = j; k < lines.get(i).length() && Character.isDigit(line.charAt(k)); k++, j++) {
                        number.append(line.charAt(k));
                        if (!adjacentSymbol) adjacentSymbol = adjacentSymbol(lines, i, k);
                    }
                    if (adjacentSymbol) sum += Integer.parseInt(number.toString());
                }
            }
        }
        return sum;
    }

    static int part2(ArrayList<String> lines) {
        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) != '*') continue;
                HashSet<Number> adjacentNums = getAdjacentNumbers(lines, i, j);

                if (adjacentNums.size() == 2) {
                    int gearRatio = 1;
                    for (Number n : adjacentNums) gearRatio *= n.value;
                    sum += gearRatio;
                }
            }
        }

        return sum;
    }

    static boolean adjacentSymbol(ArrayList<String> lines, int row, int column) {
        return (   isSymbol(lines.get(row - 1).charAt(column - 1))
                || isSymbol(lines.get(row - 1).charAt(column))
                || isSymbol(lines.get(row - 1).charAt(column + 1))
                || isSymbol(lines.get(row).charAt(column - 1))
                || isSymbol(lines.get(row).charAt(column + 1))
                || isSymbol(lines.get(row + 1).charAt(column - 1))
                || isSymbol(lines.get(row + 1).charAt(column))
                || isSymbol(lines.get(row + 1).charAt(column + 1))
        );
    }

    static HashSet<Number> getAdjacentNumbers(ArrayList<String> lines, int row, int column) {
        HashSet<Number> adjNums = new HashSet<>();
        adjNums.add(getNumber(lines, row - 1, column - 1));
        adjNums.add(getNumber(lines, row - 1, column));
        adjNums.add(getNumber(lines, row - 1, column + 1));
        adjNums.add(getNumber(lines, row, column - 1));
        adjNums.add(getNumber(lines, row, column + 1));
        adjNums.add(getNumber(lines, row + 1, column - 1));
        adjNums.add(getNumber(lines, row + 1, column));
        adjNums.add(getNumber(lines, row + 1, column + 1));
        adjNums.remove(null);
        return adjNums;
    }

    static boolean isSymbol(char c) {return (!Character.isDigit(c) && c != '.');}

    static Number getNumber(ArrayList<String> lines, int row, int index) {
        String line = lines.get(row);
        if (!Character.isDigit(line.charAt(index))) return null;

        StringBuilder numBuilder = new StringBuilder();
        while ((index - 1) >= 0 && Character.isDigit(line.charAt(index - 1))) index--;
        int start = index;
        while (Character.isDigit(line.charAt(index))) {
            numBuilder.append(line.charAt(index));
            index++;
        }
        return new Number(row, start, Integer.parseInt(numBuilder.toString()));
    }
}

class Coordinate {
    int row;
    int column;

    Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        Coordinate c = (Coordinate) o;
        return (c.column == this.column && c.row == this.row);
    }

    @Override
    public int hashCode() {
        return (this.column * this.row) + this.column + this.row;
    }
}

class Number {
    Coordinate coordinate;
    int value;

    Number(int row, int column, int value){
        this.coordinate = new Coordinate(row, column);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        Number n = (Number) o;
        return (n.coordinate.equals(this.coordinate) && n.value == this.value);
    }

    @Override
    public int hashCode(){
        return this.coordinate.hashCode() + this.value;
    }
}