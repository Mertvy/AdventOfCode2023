package Day15;

import Util.FileReader;

import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day15/input.txt");

        System.out.println(part1(lines.get(0).split(",")));
        System.out.println(part2(lines.get(0).split(",")));
    }

    static long part1(String[] input) {
        long result = 0;
        for (String instruction : input) result += hash(instruction);
        return result;
    }

    static long part2(String[] input) {
        Box[] boxes = new Box[256];
        for (int i = 0; i < 256; i++) boxes[i] = new Box(new LensList());

        for (String instruction : input) {
            String label = instruction.split("=|-")[0];
            Box box = boxes[hash(label)];
            if (instruction.charAt(instruction.length() - 2) == '=') {
                int focalLength = Integer.parseInt(instruction.split("=")[1]);
                Lens cur = box.lenses().head;;
                boolean add = true;
                while (cur != null) {
                    if (cur.name.equals(label)) {
                        cur.focalLength = focalLength;
                        add = false;
                        break;
                    }
                    cur = cur.next;
                }
                if (add) box.lenses().add(new Lens(label, focalLength));
            }
            else {
                Lens cur = box.lenses().head;
                while (cur != null) {
                    if (cur.name.equals(label))
                        box.lenses().remove(cur);
                    cur = cur.next;
                }
            }
        }
        long totalFocusingPower = 0;
        for (int i = 0; i < boxes.length; i++) {
            Lens lens = boxes[i].lenses().head;
            for (int j = 1; j <= boxes[i].lenses().size; j++) {
                totalFocusingPower += (i + 1)*(j)*(lens.focalLength);
                lens = lens.next;
            }
        }
        return totalFocusingPower;
    }

    static int hash(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash += s.charAt(i);
            hash = (hash * 17) % 256;
        }
        return hash;
    }
}


record Box(LensList lenses) {}

class Lens {
    String name;
    int focalLength;
    Lens next;
    Lens prev;
    Lens(String name, int focalLength) {this.name = name; this.focalLength = focalLength;}
}

class LensList {
    Lens head;
    Lens tail;
    int size;

    void add(Lens lens) {
        size++;
        if (head == null) {
            this.head = lens;
            this.tail = lens;
            return;
        }
        this.tail.next = lens;
        lens.prev = this.tail;
        this.tail = lens;
    }

    void remove (Lens lens) {
        size--;
        if (lens == this.tail) {
            if (lens.prev != null) this.tail = lens.prev;
            else this.tail = null;
        }
        if (lens == this.head) {
            this.head = lens.next;
            return;
        }
        lens.prev.next = lens.next;
        if (lens.next != null) lens.next.prev = lens.prev;
    }
}