import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day07 {
    public static void print (Object object) {
        System.out.println(object);
    }
    static private class Directory {
        private int size;
        private Map<String,Directory> children;
        private Directory parent;
        private boolean isFile;
        public Directory getParent() {
            return parent;
        }
        boolean isFile() {return isFile;}
        Directory(int size) {
            this(size, null);
        }
        Directory(int size, Directory parent) {
            this.size = size;
            this.isFile = size > 0 ? true : false;
            this.children = new HashMap<>();
            this.parent = parent;
        }
        void addChild(int size, String name) {
            children.put(name, new Directory(size, this));
        }
        Directory moveTo(String name) {
            return children.get(name);
        }
        List<Directory> getChildrenList() {
            return new LinkedList<Directory>(this.children.values());
        }
        int getSize() {
            if (this.size == 0) {
                int sum = 0;
                for (Directory dir : children.values()) {
                    sum += dir.getSize();
                }
                return sum;
            } else {
                return this.size;
            }
        }
    }

    static int BFSPart1 (Directory parent) {
        int sum = 0;
        List<Directory> toBeScanned = parent.getChildrenList();
        while (!toBeScanned.isEmpty()) {
            Directory dir = toBeScanned.remove(0);
            if (!dir.isFile()) {
                int size = dir.getSize();
                if (size <= 100000) {
                    sum+=size;
                }
            }
            toBeScanned.addAll(dir.getChildrenList());
        }
        return sum;
    }
    static int BFSPart2 (Directory parent, int currentUsedSpace, int maxUsedSpace) {
        int min = currentUsedSpace;
        List<Directory> toBeScanned = parent.getChildrenList();
        while (!toBeScanned.isEmpty()) {
            Directory dir = toBeScanned.remove(0);
            if (!dir.isFile()) {
                int size = dir.getSize();
                if (currentUsedSpace - size <= maxUsedSpace) {
                    if (size < min) {
                        min = size;
                    }
                }
            }
            toBeScanned.addAll(dir.getChildrenList());
        }
        return min;
    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/07.in");
        List<String> lines = Files.readAllLines(file.toPath());
        Directory current = null;
        Directory outermost = null;
        for (String line : lines) {
            String lineArray [] = line.split(" ");
            if (lineArray.length > 0 && lineArray[0].charAt(0) == '$') {
                if (lineArray[1].equals("cd")) {
                    // Assumption: the first line is: `$ cd /`
                    if (current == null) {
                        outermost = new Directory(0);
                        current = outermost;
                    } else if (lineArray[2].equals("..")) {
                        current = current.getParent();
                    } else {
                        current = current.moveTo(lineArray[2]);
                    }
                } else if (lineArray[1] == "ls") {
                    // do nothing
                }
            } else if (lineArray.length > 0 && lineArray[0].equals("dir")) {
                current.addChild(0, lineArray[1]);
            } else if (lineArray.length > 0) {
                current.addChild(Integer.parseInt(lineArray[0]),lineArray[1]);
            }
        }
        // part 1:
        print(BFSPart1(outermost));
        // part 2:
        int currentUsedSpace = outermost.getSize();
        print(BFSPart2(outermost, currentUsedSpace, 70000000 - 30000000));
    }
}
