package Day4;

import Util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day4/input.txt");

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static int part1(ArrayList<String> lines) {
        int sum = 0;
        for (String line : lines) sum += Math.pow(2, processCard(line) - 1);
        return sum;
    }

    static int part2(ArrayList<String> lines) {
        int totalCards = 0;
        int[] cardInstances = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            cardInstances[i]++;
            int winningNums = processCard(lines.get(i));
            for (int j = i + 1; j <= i + winningNums; j++) cardInstances[j] += cardInstances[i];
            totalCards += cardInstances[i];
        }
        return totalCards;
    }

    static int processCard(String card) {
        HashSet<String> winningNumsSet = new HashSet<>();
        String[] winningNums = card.split(Pattern.quote(" | "))[0].split(Pattern.quote(": "))[1].strip().split(" +");
        Collections.addAll(winningNumsSet, winningNums);

        HashSet<String> cardNumsSet = new HashSet<>();
        String[] cardNums = card.split(Pattern.quote(" | "))[1].strip().split(" +");
        Collections.addAll(cardNumsSet, cardNums);


        int totalWinners = 0;
        for (String num : cardNumsSet) if (winningNumsSet.contains(num)) totalWinners++;

        return totalWinners;
    }
}
