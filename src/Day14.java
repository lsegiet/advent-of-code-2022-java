import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Day14 {
    public static void print (Object object) {
        System.out.println(object);
    }
    static class Cave {
        private Map<Integer,Map<Integer,Character>> mapGrid = new HashMap<>();
        private int depth;
        Cave(List<String> lines) {
            for (String line : lines) {
                String pairs [] = line.split(" -> ");
                String first = pairs[0];
                for (int i = 1; i < pairs.length; i++) {
                    String second = pairs[i];
                    initTheRock(first, second);
                    first = second;
                }
            }
            depth = calculateDepth();
        }

        int pourSand(boolean withFloor) {
            int count = 0;
            while (addOneSand(withFloor)) {
                count += 1;
            }
            return withFloor ? count + 1 : count;
        }

        void resetSand() {
            for (int x : mapGrid.keySet()) {
                List<Integer> toBeRemoved = new ArrayList<>();
                for (int y : mapGrid.get(x).keySet()) {
                    if (mapGrid.get(x).get(y).equals('o')) {
                        toBeRemoved.add(y);
                    }
                }
                for (int y : toBeRemoved) {
                    mapGrid.get(x).remove(y);
                }
            }
        }

        private boolean addOneSand(boolean withFloor) {
            int x = 500;
            int y = 0;
            
            while (true) {
                if (y > depth && !withFloor) {
                    return false;
                }
                if (!isOccupied(x, y+1, withFloor)) {
                    y += 1;
                } else if (!isOccupied(x-1,y+1, withFloor)) {
                    x -= 1;
                    y += 1;
                } else if (!isOccupied(x+1,y+1, withFloor)) {
                    x += 1;
                    y += 1;
                } else {
                    if (y == 0) {
                        return false;
                    }
                    if (!mapGrid.containsKey(x)) {
                        mapGrid.put(x,new HashMap<>());
                    }
                    mapGrid.get(x).put(y,'o');
                    return true;
                }
            }
        }

        private boolean isOccupied(int x, int y) {
            return mapGrid.containsKey(x) && mapGrid.get(x).containsKey(y);
        }

        private boolean isOccupiedWithFloor(int x, int y) {
            return isOccupied(x, y) || y >= depth+2;
        }

        private boolean isOccupied(int x, int y, boolean withFloor) {
            return withFloor ? isOccupiedWithFloor(x, y) : isOccupied(x, y);
        }

        private int calculateDepth() {
            int d = 0;
            for (int x : mapGrid.keySet()) {
                for (int y : mapGrid.get(x).keySet()) {
                    d = Math.max(d, y);
                }
            }
            return d;
        }

        private void initTheRock(String first, String second) {
            String coords1 [] = first.split(",");
            String coords2 [] = second.split(",");

            int x1 = Integer.parseInt(coords1[0]);
            int y1 = Integer.parseInt(coords1[1]);
            int x2 = Integer.parseInt(coords2[0]);
            int y2 = Integer.parseInt(coords2[1]);

            int xMin = Math.min(x1,x2);
            int xMax = Math.max(x1,x2);
            int yMin = Math.min(y1,y2);
            int yMax = Math.max(y1,y2);

            for (int x = xMin; x <= xMax; x++) {
                for (int y = yMin; y <= yMax; y++) {
                    if (!mapGrid.containsKey(x)) {
                        mapGrid.put(x,new HashMap<>());
                    }
                    mapGrid.get(x).put(y, '#');
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("src/14.in");
        List<String> lines = Files.readAllLines(file.toPath());
        Cave cave = new Cave(lines);
        // part 1:
        print(cave.pourSand(false));
        // part 2:
        cave.resetSand();
        print(cave.pourSand(true));
    }

}
