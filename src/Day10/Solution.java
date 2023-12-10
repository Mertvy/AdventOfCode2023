package Day10;

import Util.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        ArrayList<String> lines = FileReader.fileLines("src/Day10/input.txt");
        Util.Tools.padInput(lines, '.');

        HashMap<Coords, Pipe> pipeMap = new HashMap<>();
        Pipe start = new Pipe('.', -1, -1);

        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(row).length(); col++) {
                Pipe pipe = new Pipe(lines.get(row).charAt(col), row, col);
                pipeMap.put(new Coords(row, col), pipe);
                if (lines.get(row).charAt(col) == 'S') start = pipe;
            }
        }

        for (Pipe pipe : pipeMap.values()) pipe.setConnected(pipeMap, lines.size(), lines.get(0).length());

        boolean done = false;
        while (!done) {
            done = false;
            for (Pipe pipe : pipeMap.values()) {
                if (!pipe.isValid()) {
                    done = true;
                    pipe.shape = '.';
                }
            }
        }

        HashSet<Pipe> mainPipes = new HashSet<>();
        for (Pipe pipe : pipeMap.values()) {

            if (pipe.shape != '.') {
                mainPipes.add(pipe);
            }
        }

        ArrayList<Pipe> adjStart = new ArrayList<>();
        for (Pipe pipe : mainPipes) if (pipe.connected[0] == start || pipe.connected[1] == start) adjStart.add(pipe);
        start.connected[0] = adjStart.get(0);
        start.connected[1] = adjStart.get(1);


        System.out.println(part1(start));
        System.out.println(part2(pipeMap, lines.size(), lines.get(0).length()));
    }

    static int part1(Pipe start) {
        int distance = 0;
        Pipe cur1 = start, cur2 = start;
        while (true) {
            cur1.mainPipe = true;
            cur2.mainPipe = true;

            if (!cur1.connected[0].mainPipe) cur1 = cur1.connected[0];
            else if (cur1.connected[1].mainPipe) return distance;
            else cur1 = cur1.connected[1];

            if (!cur2.connected[1].mainPipe) cur2 = cur2.connected[1];
            else if (cur2.connected[0].mainPipe) return distance;
            else cur2 = cur2.connected[0];

            distance++;
        }
    }

    static int part2(HashMap<Coords, Pipe> pipes, int height, int width) {
        char[][] layout = new char[height][width];
        char[][] newLayout = new char[2 * height - 1][2 * width - 1];
        for (int i = 0; i < newLayout.length; i++) {
            for (int j = 0; j < 2 * width - 1; j++) newLayout[i][j] = '~';
        }

        for (Pipe pipe : pipes.values()) {
            if (!pipe.mainPipe) pipe.shape = '.';
        }

        for (Pipe pipe : pipes.values()) {
            layout[pipe.coords.row][pipe.coords.col] = pipe.shape;
            newLayout[pipe.coords.row * 2][pipe.coords.col * 2] = pipe.shape;
            if (pipe.shape == '.') continue;
            if (pipe.shape == '|') {
                newLayout[pipe.coords.row * 2 - 1][pipe.coords.col * 2] = '|';
                newLayout[pipe.coords.row * 2 + 1][pipe.coords.col * 2] = '|';
            }
            if (pipe.shape == '-') {
                newLayout[pipe.coords.row * 2][pipe.coords.col * 2 - 1] = '-';
                newLayout[pipe.coords.row * 2][pipe.coords.col * 2 + 1] = '-';
            }
            if (pipe.shape == 'L') {
                newLayout[pipe.coords.row * 2 - 1][pipe.coords.col * 2] = '|';
                newLayout[pipe.coords.row * 2][pipe.coords.col * 2 + 1] = '-';
            }
            if (pipe.shape == 'J') {
                newLayout[pipe.coords.row * 2 - 1][pipe.coords.col * 2] = '|';
                newLayout[pipe.coords.row * 2][pipe.coords.col * 2 - 1] = '-';
            }
            if (pipe.shape == '7') {
                newLayout[pipe.coords.row * 2 + 1][pipe.coords.col * 2] = '|';
                newLayout[pipe.coords.row * 2][pipe.coords.col * 2 - 1] = '-';
            }
            if (pipe.shape == 'F') {
                newLayout[pipe.coords.row * 2 + 1][pipe.coords.col * 2] = '|';
                newLayout[pipe.coords.row * 2][pipe.coords.col * 2 + 1] = '-';
            }
        }

        changeBoundary(newLayout, new Coords(0,0));

        int total = 0;
        for (char[] line : newLayout)
            for (char c : line)
                if (c == '.') total++;
        return total;
    }

    static void changeBoundary(char[][] layout, Coords coords) {
        Stack<Coords> stack = new Stack<>();
        stack.push(coords);

        while (!stack.isEmpty()) {
            Coords cur = stack.pop();
            if (cur.row < 0 || cur.row > layout.length - 1 || cur.col < 0 || cur.col > layout[0].length - 1) continue;
            if (layout[cur.row][cur.col] != '.' && layout[cur.row][cur.col] != '~') continue;
            layout[cur.row][cur.col] = '*';
            stack.push(new Coords(cur.row - 1, cur.col));
            stack.push(new Coords(cur.row + 1, cur.col));
            stack.push(new Coords(cur.row, cur.col - 1));
            stack.push(new Coords(cur.row, cur.col + 1));
        }
    }
}

class Pipe {
    char shape;
    Coords coords;
    Pipe[] connected;
    Pipe[] adjacent;

    boolean mainPipe;
    boolean visited;
    Pipe(char shape, int row, int col) {
        this.shape = shape;
        this.coords = new Coords(row, col);
        this.connected = new Pipe[2];
        this.adjacent = new Pipe[4];
        this.visited = false;
        this.mainPipe = false;
    }

    void setConnected(HashMap<Coords, Pipe> pipes, int height, int width) {
        boolean notTop = this.coords.row > 0, notBottom = this.coords.row < height - 1,
                notLeft = this.coords.col > 0, notRight = this.coords.col < width - 1;
        Coords above = new Coords(this.coords.row - 1, this.coords.col),
                below = new Coords(this.coords.row + 1, this.coords.col),
                left = new Coords(this.coords.row, this.coords.col - 1),
                right = new Coords(this.coords.row, this.coords.col + 1);
        if (this.shape == '|') {
            if (notTop) connected[0] = pipes.get(above);
            if (notBottom) connected[1] = pipes.get(below);
        }
        else if (this.shape == '-') {
            if (notLeft) connected[0] = pipes.get(left);
            if (notRight) connected[1] = pipes.get(right);
        }
        else if (this.shape == 'L') {
            if (notTop) connected[0] = pipes.get(above);
            if (notRight) connected[1] = pipes.get(right);
        }
        else if (this.shape == 'J') {
            if (notTop) connected[0] = pipes.get(above);
            if (notLeft) connected[1] = pipes.get(left);
        }
        else if (this.shape == '7') {
            if (notLeft) connected[0] = pipes.get(left);
            if (notBottom) connected[1] = pipes.get(below);
        }
        else if (this.shape == 'F') {
            if (notRight) connected[0] = pipes.get(right);
            if (notBottom) connected[1] = pipes.get(below);
        }

        if (notTop) adjacent[0] = (pipes.get(above));
        if (notBottom) adjacent[1] = (pipes.get(below));
        if (notLeft) adjacent[2] = (pipes.get(left));
        if (notRight) adjacent[3] = (pipes.get(right));
    }

    boolean isValid() {
        if (this.shape == 'S') return true;
        if (this.shape == '.') return false;
        if (this.connected[0] == null || this.connected[1] == null ||
                this.connected[0].shape == '.' || this.connected[1].shape == '.') return false;

        if ((this.connected[0].connected[0] != this
                && this.connected[0].connected[1] != this
                && this.connected[0].shape != 'S')) return false;

        if ((this.connected[1].connected[0] != this
                && this.connected[1].connected[1] != this
                && this.connected[1].shape != 'S')) return false;
        return true;
    }
}

class Coords {
    int row, col;

    Coords(int row, int col) {
        this.row = row; this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coords)) return false;
        return (((Coords) o).row == this.row && ((Coords) o).col == this.col);
    }

    @Override
    public int hashCode() {
        return row * 7 + col * 3 - row * col;
    }
}