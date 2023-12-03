package Day3;

import Util.FileReader;

import java.util.ArrayList;
import java.util.HashSet;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day3/input.txt");

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static int part1(ArrayList<String> lines) {
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
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
                    System.out.println("(" + j + "," + i + "): " + gearRatio);
                    sum += gearRatio;
                }
            }
        }

        return sum;
    }

    static boolean adjacentSymbol(ArrayList<String> lines, int row, int column) {
        try {if (isSymbol(lines.get(row - 1).charAt(column - 1))) return true;} catch (Exception e) {;}
        try {if (isSymbol(lines.get(row - 1).charAt(column    ))) return true;} catch (Exception e) {;}
        try {if (isSymbol(lines.get(row - 1).charAt(column + 1))) return true;} catch (Exception e) {;}
        try {if (isSymbol(lines.get(row).charAt(column - 1    ))) return true;} catch (Exception e) {;}
        try {if (isSymbol(lines.get(row).charAt(column + 1    ))) return true;} catch (Exception e) {;}
        try {if (isSymbol(lines.get(row + 1).charAt(column - 1))) return true;} catch (Exception e) {;}
        try {if (isSymbol(lines.get(row + 1).charAt(column    ))) return true;} catch (Exception e) {;}
        try {if (isSymbol(lines.get(row + 1).charAt(column + 1))) return true;} catch (Exception e) {;}
        return false;
    }

    static HashSet<Number> getAdjacentNumbers(ArrayList<String> lines, int row, int column) {
        HashSet<Number> adjNums = new HashSet<>();
        try {adjNums.add(getNumber(lines, row - 1, column - 1));} catch (Exception e) {;}
        try {adjNums.add(getNumber(lines, row - 1, column          ));} catch (Exception e) {;}
        try {adjNums.add(getNumber(lines, row - 1, column + 1));} catch (Exception e) {;}
        try {adjNums.add(getNumber(lines, row, column - 1         ));} catch (Exception e) {;}
        try {adjNums.add(getNumber(lines, row, column + 1         ));} catch (Exception e) {;}
        try {adjNums.add(getNumber(lines, row + 1, column - 1));} catch (Exception e) {;}
        try {adjNums.add(getNumber(lines, row + 1, column          ));} catch (Exception e) {;}
        try {adjNums.add(getNumber(lines, row + 1, column + 1));} catch (Exception e) {;}
        if (adjNums.contains(null)) adjNums.remove(null);
        return adjNums;
    }

    static boolean isSymbol(char c) {
        return (!Character.isDigit(c) && c != '.');
    }

    static Number getNumber(ArrayList<String> lines, int row, int index) {
        String line = lines.get(row);
        if (!Character.isDigit(line.charAt(index))) return null;

        StringBuilder numBuilder = new StringBuilder();
        while ((index - 1) >= 0 && Character.isDigit(line.charAt(index - 1))) index--;
        int start = index;
        while (index < line.length() && Character.isDigit(line.charAt(index))) {
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