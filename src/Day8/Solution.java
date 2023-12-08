package Day8;

import Util.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day8/input.txt");

        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static int part1(ArrayList<String> lines) {
        String instructions = lines.get(0);
        HashMap<String, Node> nodesMap = createMap(lines);

        Node curNode = nodesMap.get("AAA");
        int count = 1;
        for (int i = 0; ; i = (i + 1) % instructions.length(), count++) {
            char instruction = instructions.charAt(i);
            if (instruction == 'L') curNode = curNode.left;
            else if (instruction == 'R') curNode = curNode.right;
            if (curNode == nodesMap.get("ZZZ")) return count;
        }
    }

    static long part2(ArrayList<String> lines) {
        String instructions = lines.get(0);
        HashMap<String, Node> nodesMap = createMap(lines);

        ArrayList<Node> startNodes = new ArrayList<>();
        for (int i = 2; i < lines.size(); i++) {
            String nodeID = lines.get(i).split(" ")[0];
            if (nodeID.charAt(nodeID.length() - 1) == 'A') startNodes.add(nodesMap.get(nodeID));
        }
        ArrayList<Node> endNodes = new ArrayList<>();
        for (int i = 2; i < lines.size(); i++) {
            String nodeID = lines.get(i).split(" ")[0];
            if (nodeID.charAt(nodeID.length() - 1) == 'Z') endNodes.add(nodesMap.get(nodeID));
        }

        for (Node node : startNodes) {
            node.setDistanceToEndPoint(instructions);
        }
        for (Node node : endNodes) {
            node.setDistanceToEndPoint(instructions);
        }

        Object[] curNodes = startNodes.toArray();
        long[] counts = new long[curNodes.length];
        long maxCount = 1;
        while (true) {
            boolean done = true;
            for (int i = 0; i < curNodes.length; i++) {
                Node node = (Node) curNodes[i];
                while (counts[i] < maxCount) {
                    counts[i] += node.distanceToEndPoint;
                    node = node.closestEndPoint;
                }
                if (counts[i] != maxCount) done = false;
                maxCount = counts[i];
                curNodes[i] = node;
            }
            if (done) return maxCount;
        }
    }

    static HashMap<String, Node> createMap(ArrayList<String> lines) {
        HashMap<String, Node> nodesMap = new HashMap<>();
        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get(i);
            String id = line.split(" ")[0];
            String[] adjacents = line.split(Pattern.quote(" = "))[1].split(Pattern.quote(", "));
            adjacents[0] = adjacents[0].substring(1);
            adjacents[1] = adjacents[1].substring(0, adjacents[1].length() - 1);
            Node node = new Node(id, adjacents);
            nodesMap.put(id, node);
        }

        for (Node node : nodesMap.values()) {
            node.left = nodesMap.get(node.adjacents[0]);
            node.right = nodesMap.get(node.adjacents[1]);
        }

        return nodesMap;
    }
}

class Node {
    String id;
    Node left;
    Node right;
    String[] adjacents;
    int distanceToEndPoint;
    Node closestEndPoint;

    Node (String id, String[] adj) {
        this.id = id;
        this.adjacents = adj;
    }

    void setDistanceToEndPoint(String instructions) {
        Node curNode = this;
        int count = 1;
        for (int i = 0; ; i = (i + 1) % instructions.length(), count++) {
            char instruction = instructions.charAt(i);
            if (instruction == 'L') curNode = curNode.left;
            else if (instruction == 'R') curNode = curNode.right;
            if (curNode.id.charAt(curNode.id.length() - 1) == 'Z') {
                this.distanceToEndPoint = count;
                this.closestEndPoint = curNode;
                break;
            }
        }
    }
}
