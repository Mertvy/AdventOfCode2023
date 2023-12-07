package Day7;

import Util.FileReader;

import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day7/input.txt");

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static int part1(ArrayList<String> lines) {
        ArrayList<Hand1> hands = new ArrayList<>();
        for (String line : lines) {
            String[] splitLine = line.split(" ");
            hands.add(new Hand1(splitLine[0], Integer.parseInt(splitLine[1])));
        }

        Collections.sort(hands);

        int winnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            winnings += (i + 1) * hands.get(i).bid;
        }

        return winnings;
    }

    static int part2(ArrayList<String> lines) {
        ArrayList<Hand2> hands = new ArrayList<>();
        for (String line : lines) {
            String[] splitLine = line.split(" ");
            hands.add(new Hand2(splitLine[0], Integer.parseInt(splitLine[1])));
        }

        Collections.sort(hands);

        int winnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            winnings += (i + 1) * hands.get(i).bid;
        }

        return winnings;
    }
}


class Card1 implements Comparable<Card1> {
    int strength;

    Card1(char c) {
        if (Character.isDigit(c)) this.strength = c - '0';
        else {
            char[] cards = {'T', 'J', 'Q', 'K', 'A'};
            for (int i = 0; i < cards.length; i++) {
                if (c == cards[i]) this.strength = i + 10;
            }
        }
    }

    @Override
    public int compareTo(Card1 c) {
        return this.strength - c.strength;
    }
}

class Hand1 implements Comparable<Hand1> {
    Card1[] cards;
    int type;
    int bid;

    Hand1(String hand, int bid) {
        int[] letterCounts = new int[91];
        cards = new Card1[5];
        for (int i = 0; i < 5; i++) {
            letterCounts[hand.charAt(i)]++;
            cards[i] = new Card1(hand.charAt(i));
        }

        boolean[] multiples = new boolean[4]; //{pair, triple, quadruple, quintuple}
        int twos = 0;
        for (int count : letterCounts) {
            if (count > 1) multiples[count - 2] = true;
            if (count == 2) twos++;
        }

        if (multiples[3]) type = 6;
        else if (multiples[2]) type = 5;
        else if (multiples[1] && multiples[0]) type = 4;
        else if (multiples[1]) type = 3;
        else if (twos == 2) type = 2;
        else if (multiples[0]) type = 1;
        else type = 0;

        this.bid = bid;
    }

    @Override
    public int compareTo(Hand1 hand) {
        if (this.type == hand.type) {
            for (int i = 0; i < 5; i++) {
                if (!(this.cards[i].compareTo(hand.cards[i]) == 0))
                    return this.cards[i].compareTo(hand.cards[i]);
            }
            return 0;
        }
        else return this.type - hand.type;
    }
}

class Card2 implements Comparable<Card2> {
    int strength;

    Card2(char c) {
        if (Character.isDigit(c)) this.strength = c - '0';
        else if (c == 'J') this.strength = 1;
        else {
            char[] cards = {'T', '{', 'Q', 'K', 'A'};
            for (int i = 0; i < cards.length; i++) {
                if (c == cards[i]) this.strength = i + 10;
            }
        }
    }

    @Override
    public int compareTo(Card2 c) {
        return this.strength - c.strength;
    }
}

class Hand2 implements Comparable<Hand2> {
    Card2[] cards;
    int type;
    int bid;

    Hand2(String hand, int bid) {
        int[] letterCounts = new int[91];
        cards = new Card2[5];
        for (int i = 0; i < 5; i++) {
            letterCounts[hand.charAt(i)]++;
            cards[i] = new Card2(hand.charAt(i));
        }

        int jokerCount = letterCounts['J'];
        int maxOccurence = 0;
        int maxIndex = -1;
        for (int i = 0; i < letterCounts.length; i++) {
            if (letterCounts[i] > maxOccurence && i != 'J') {
                maxIndex = i;
                maxOccurence = letterCounts[i];
            }
        }

        if (maxOccurence > 0) {
            letterCounts[maxIndex] += jokerCount;
            letterCounts['J'] = 0;
        }

        boolean[] multiples = new boolean[4]; //{pair, triple, quadruple, quintuple}
        int twos = 0;
        for (int count : letterCounts) {
            if (count > 1) multiples[count - 2] = true;
            if (count == 2) twos++;
        }


        if (multiples[3]) type = 6;
        else if (multiples[2]) type = 5;
        else if (multiples[1] && multiples[0]) type = 4;
        else if (multiples[1]) type = 3;
        else if (twos == 2) type = 2;
        else if (multiples[0]) type = 1;
        else type = 0;

        this.bid = bid;
    }

    @Override
    public int compareTo(Hand2 hand) {
        if (this.type == hand.type) {
            for (int i = 0; i < 5; i++) {
                if (!(this.cards[i].compareTo(hand.cards[i]) == 0))
                    return this.cards[i].compareTo(hand.cards[i]);
            }
            return 0;
        }
        else return this.type - hand.type;
    }
}