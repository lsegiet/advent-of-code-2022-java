import java.io.File;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
public class Day12 {
    public static void print (Object object) {
        System.out.println(object);
    }
    private static class Node {
        private final int x;
        private final int y;
        private final int dist;
        Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
    public static class Pair {
        final int x;
        final int y;
        private int hashCode;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
            this.hashCode = Objects.hash(x,y);
        }
        @Override
        public boolean equals(Object o) {
            Pair that = (Pair) o;
            return x == that.x && y == that.y;
        }
        @Override
        public int hashCode() {
            return hashCode;
        }
    }
    public static class Grid {
        private final char [][] grid;
        private final int width;
        private final int height;
        private Node source;
        private Node end;
        Grid(List<String> lines) {
            height = lines.size();
            width = lines.get(0).length();
            grid = new char[height][width];
            for (int y = 0; y<lines.size(); y++) {
                String line = lines.get(y);
                for (int x = 0; x<line.length(); x++) {
                    char c = line.charAt(x);
                    grid[y][x] = c;
                    if (c == 'S') {
                        source = new Node(x,y,0);
                    }
                    if (c == 'E') {
                        end = new Node(x,y,0);
                    }
                }
            }
        }
        private boolean inTheGrid(int x, int y) {
            return x >= 0 && x < width && y >= 0 && y < height;
        }
        private List<Pair> candidates(int x, int y) {
            List<Pair> list = new ArrayList<>();
            list.add(new Pair(x-1,y));
            list.add(new Pair(x+1,y));
            list.add(new Pair(x,y-1));
            list.add(new Pair(x,y+1));
            list.removeIf(pair -> {return !inTheGrid(pair.x, pair.y);});
            return list;
        }
        private static char toHeight(char c) {
            if (c == 'S') { return 'a';}
            if (c == 'E') { return 'z';}
            return c;
        }
        private boolean canMove(int startX, int startY, int endX, int endY, boolean hikeUp) {
            char cStart = toHeight(grid[startY][startX]);
            char cEnd = toHeight(grid[endY][endX]);
            int temp = (int)cStart - (int)cEnd;
            if (hikeUp) {
                temp *= -1;
            }
            return temp <= 1;
        }

        int BFS(Node start, char end, boolean hikeUp) {
            Queue<Node> q = new ArrayDeque<>();
            q.add(start);
            Map<Pair,Boolean> visited = new HashMap<>();
            visited.put(new Pair(start.x,start.y), true);
            while (!q.isEmpty()) {
                Node current = q.remove();
                for (Pair candidate : candidates(current.x,current.y)) {
                    if (visited.containsKey(candidate)) {
                        continue;
                    }
                    if (canMove(current.x,current.y,candidate.x,candidate.y,hikeUp)) {
                        if (grid[candidate.y][candidate.x] == end) {
                            return current.dist+1;
                        }
                        Node newNode = new Node(candidate.x, candidate.y, current.dist+1);
                        q.add(newNode);
                        visited.put(new Pair(candidate.x, candidate.y), true);
                    }
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("src/12.in");
        List<String> lines = Files.readAllLines(file.toPath());
        Grid grid = new Grid(lines);
        // part 1:
        print(grid.BFS(grid.source, 'E', true));
        // part 2:
        print(grid.BFS(grid.end, 'a', false));
    }

}
