import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Day08 {
    public static void print (Object object) {
        System.out.println(object);
    }
    static private class Grid {
        int length;
        int width ;
        int[][] grid;
        Grid (List<String> lines) {
            this.length = lines.size();
            this.width = lines.get(0).length();
            this.grid = new int[length][width];
            for (int i = 0; i < length; i++) {
                String line = lines.get(i);
                for (int j = 0; j < width; j++) {
                    int digit = Integer.parseInt(line.substring(j,j+1));
                    grid[i][j] = digit;
                }
            }
        }
        private boolean checkBounds(int x, int y) {
            return x >= 0 && y >= 0 && x < width && y < length;
        }
        private boolean isVisibleFromTop(int x, int y) {
            if (!checkBounds(x, y)) return false;
            for(int itY = 0; itY < y; itY += 1) {
                if (grid[itY][x] >= grid[y][x]) return false;
            }
            return true;
        }
        private boolean isVisibleFromBottom(int x, int y) {
            if (!checkBounds(x, y)) return false;
            for(int itY = length-1; itY > y; itY -= 1) {
                if (grid[itY][x] >= grid[y][x]) return false;
            }
            return true;
        }
        private boolean isVisibleFromLeft(int x, int y) {
            if (!checkBounds(x, y)) return false;
            for(int itX = 0; itX < x; itX += 1) {
                if (grid[y][itX] >= grid[y][x]) return false;
            }
            return true;
        }
        private boolean isVisibleFromRight(int x, int y) {
            if (!checkBounds(x, y)) return false;
            for(int itX = length-1; itX > x; itX -= 1) {
                if (grid[y][itX] >= grid[y][x]) return false;
            }
            return true;
        }
        private boolean isVisible(int x, int y) {
            if (!checkBounds(x, y)) return false;
            return isVisibleFromTop(x, y) ||
                    isVisibleFromBottom(x, y) ||
                    isVisibleFromLeft(x, y) ||
                    isVisibleFromRight(x, y);
        }
        private int scenicTop(int x, int y) {
            int multiplier = 0;
            int itY = y - 1;
            while (checkBounds(x,itY) && grid[y][x] > grid[itY][x]) {
                multiplier += 1;
                itY -= 1;
            }
            return checkBounds(x, itY) ? multiplier + 1 : Math.max(multiplier,1);
        }
        private int scenicBottom(int x, int y) {
            int multiplier = 0;
            int itY = y + 1;
            while (checkBounds(x,itY) && grid[y][x] > grid[itY][x]) {
                multiplier += 1;
                itY += 1;
            }
            return checkBounds(x, itY) ? multiplier + 1 : Math.max(multiplier,1);
        }
        private int scenicLeft(int x, int y) {
            int multiplier = 0;
            int itX = x - 1;
            while (checkBounds(itX,y) && grid[y][x] > grid[y][itX]) {
                multiplier += 1;
                itX -= 1;
            }
            return checkBounds(itX, y) ? multiplier + 1 : Math.max(multiplier,1);
        }
        private int scenicRight(int x, int y) {
            int multiplier = 0;
            int itX = x + 1;
            while (checkBounds(itX,y) && grid[y][x] > grid[y][itX]) {
                multiplier += 1;
                itX += 1;
            }
            return checkBounds(itX, y) ? multiplier + 1 : Math.max(multiplier,1);
        }
        int countVisible() {
            int count = 0;
            for (int y = 0; y < length; y++) {
                for (int x = 0; x < width; x++) {
                    count += isVisible(x, y) ? 1 : 0;
                }
            }
            return count;
        }
        int scenicScore(int x, int y) {
            return scenicTop(x, y) * scenicBottom(x, y) * scenicLeft(x, y) * scenicRight(x, y);
        }
        int findMaxScenicScore() {
            int max = 0;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < length; y++) {
                    max = Math.max(max, scenicScore(x,y));
                }
            }
            return max;
        }
    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/08.in");
        List<String> lines = Files.readAllLines(file.toPath());
        Grid grid = new Grid(lines);

        // part 1:
        print(grid.countVisible());
        // part 2:
        print(grid.findMaxScenicScore());
    }
}
