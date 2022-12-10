import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.Math;

public class Day09 {
    public static void print (Object object) {
        System.out.println(object);
    }
    public static class Position {
        private int x;
        private int y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {return x;}
        public int getY() {return y;}

        @Override
        public int hashCode(){
            // There are 2000 lines in the input and integers in input are <20
            return this.x + 20 * 2000 * this.y; 
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null) {return false;}
            if (getClass() != o.getClass()) {return false;}
            Position other = (Position) o;
            if (this.x == other.getX() && this.y == other.getY()) {
                return true;
            }
            return false;
        }
        @Override
        public String toString() {
            return "(" + Integer.toString(x) + "," + Integer.toString(y) + ")";
        }
    }
    public static class Motions {
        private final List<Character> directions = new ArrayList<>();
        private final List<Integer> steps = new ArrayList<>();
        private final Set<Position> tailPositionSet = new HashSet<>();
        private final Set<Position> longTailPositionSet = new HashSet<>();
        private Position head = new Position(0,0);
        private Position tail = new Position(0,0);
        private Position [] longTail = new Position[10];
        Motions(List<String> lines) {
            for (String line : lines) {
                directions.add(line.split(" ")[0].toCharArray()[0]);
                steps.add(Integer.parseInt(line.split(" ")[1]));
                tailPositionSet.add(tail);
                for (int i = 0; i < 10; i++) {
                    longTail[i] = new Position(0,0);
                }
                longTailPositionSet.add(new Position(0, 0));
            }
        }
        void moveHead(char dir) {
            switch (dir) {
                case 'R':
                    head = new Position(head.getX()+1, head.getY());
                    break;
                case 'L':
                    head = new Position(head.getX()-1, head.getY());
                    break;
                case 'U':
                    head = new Position(head.getX(), head.getY()+1);
                    break;
                case 'D':
                    head = new Position(head.getX(), head.getY()-1);
                    break;
            }
            longTail[0] = head;
        }
        void moveTail() {
            int diffX = head.getX() - tail.getX();
            int diffY = head.getY() - tail.getY();
            if (Math.abs(diffX) > 1) {
                tail = new Position(tail.getX() + diffX/Math.abs(diffX), tail.getY());
                if (Math.abs(diffY) > 0) {
                    tail = new Position(tail.getX(), tail.getY() + diffY/Math.abs(diffY));
                }
            } else if (Math.abs(diffY) > 1) {
                tail = new Position(tail.getX(), tail.getY() + diffY/Math.abs(diffY));
                if (Math.abs(diffX) > 0) {
                    tail = new Position(tail.getX() + diffX/Math.abs(diffX), tail.getY());
                }
            }
            if (!tailPositionSet.contains(tail)) {
                tailPositionSet.add(tail);
            }
        }
        void moveLongTail() {
            for (int i = 1; i < 10; i++) {
                int diffX = longTail[i-1].getX() - longTail[i].getX();
                int diffY = longTail[i-1].getY() - longTail[i].getY();
                if (Math.abs(diffX) > 1) {
                    longTail[i] = new Position(longTail[i].getX() + diffX/Math.abs(diffX), longTail[i].getY());
                    if (Math.abs(diffY) > 0) {
                        longTail[i] = new Position(longTail[i].getX(), longTail[i].getY() + diffY/Math.abs(diffY));
                    }
                } else if (Math.abs(diffY) > 1) {
                    longTail[i] = new Position(longTail[i].getX(), longTail[i].getY() + diffY/Math.abs(diffY));
                    if (Math.abs(diffX) > 0) {
                        longTail[i] = new Position(longTail[i].getX() + diffX/Math.abs(diffX), longTail[i].getY());
                    }
                }
                if (!longTailPositionSet.contains(longTail[9])) {
                    longTailPositionSet.add(longTail[9]);
                }
            }
        }
        void move(int steps, char dir) {
            if (steps > 0) {
                moveHead(dir);
                moveTail();
                moveLongTail();
                move(steps-1, dir);
            }
        }
        void moveAll() {
            for (int i = 0; i<directions.size(); i++) {
                move(steps.get(i), directions.get(i));
            }
        }
        int tailPositionSetSize() {
            //print(tailPositionSet);
            return tailPositionSet.size();
        }
        int longTailPositionSetSize() {
            //print(longTailPositionSet);
            return longTailPositionSet.size();
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("src/09.in");
        List<String> lines = Files.readAllLines(file.toPath());
        Motions motions = new Motions(lines);
        motions.moveAll();

        // part 1:
        print(motions.tailPositionSetSize());
        // part 2:
        print(motions.longTailPositionSetSize());
    }

}
