import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
public class Day13 {
    public static void print (Object object) {
        System.out.println(object);
    }
    static class Packet {
        private String key;
        private final List<Packet> nested = new ArrayList<>();
        private int value = -1;
        String getKey() {return key;}
        int getIntEndIndex(String string, int startIndex) {
            List<Integer> candidates = new ArrayList<>();
            candidates.add(string.substring(startIndex).indexOf(","));
            candidates.add(string.substring(startIndex).indexOf("]"));
            candidates.removeIf(c -> {return c == -1;});
            Collections.sort(candidates);
            return candidates.get(0)+startIndex;
        }
        int findClosingBracketInd(String string, int startIndex) {
            int count = 1;
            int i = startIndex + 1;
            while (count > 0) {
                switch (string.charAt(i)) {
                    case ']':
                        count -= 1;
                        break;
                    case '[':
                        count += 1;
                        break;
                }
                i+=1;
            }
            return i;
        }
        @Override
        public String toString() {
            if (value != -1) {
                return String.format("%d", value);
            }
            return nested.stream().map(n -> n.toString())
            .collect(Collectors.joining(",","[","]"));
        }
        Packet(int value) {
            this.value = value;
        }
        Packet(String line) {
            key = line;
            for (int i = 1; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case '[':
                        this.nested.add(new Packet(line.substring(i, 
                            findClosingBracketInd(line, i))));
                        i = findClosingBracketInd(line, i);
                        break;
                    case ']':
                        i = line.length();
                        break;
                    case ',':
                        break;
                    default:
                        this.nested.add(
                            new Packet(Integer.parseInt(line.substring(i,getIntEndIndex(line,i)))));
                }
            }
        }
        int rightOrder(Packet right) {
            Packet left = this;
            // both integers: 
            if (left.value > -1 && right.value > -1) {
                if (left.value < right.value) {
                    return 1;
                } else if (left.value > right.value) {
                    return 0;
                } 
                return -1;
            } else if (left.value == -1 && right.value == -1) {
            // both lists:
                for (int i = 0; i < Math.max(left.nested.size(), right.nested.size()); i++) {
                    if (i >= left.nested.size()) {
                        return 1;
                    } else if (i >= right.nested.size()) {
                        return 0;
                    } else {
                        if (left.nested.get(i).rightOrder(right.nested.get(i)) != -1) {
                            return left.nested.get(i).rightOrder(right.nested.get(i));
                        }
                    }
                }
            } else if (left.value == -1) {
            // left value is an integer:
                right.nested.add(new Packet(right.value));
                right.value = -1;
                return left.rightOrder(right);
            } else {
            // right value is an integer:
                left.nested.add(new Packet(left.value));
                left.value = -1;
                return left.rightOrder(right);
            }
            // left the loop when both are lists
            return -1;
        }
    }
    static class Packets {
        List<Packet> packets = new ArrayList<>();
        Packets(List<String> lines) {
            for (String line : lines) {
                if (line.length() > 0) {
                    packets.add(new Packet(line));
                }
            }
        }
        // Solves part 1:
        int sumRightIndices() {
            int sum = 0;
            for (int i = 0; i < packets.size()/2; i++) {
                if (packets.get(2*i).rightOrder(packets.get(2*i+1))==1) {
                    sum += i + 1; // indexed at 1
                }
            }
            return sum;
        }
        void addPacket(Packet packet) {
            packets.add(packet);
        }
        void sort() {
            packets.sort((left, right) -> left.rightOrder(right) == 1 ? -1 : 1);
        }
        int getIndex(String key) {
            for (int i = 0; i < packets.size(); i++) {
                if (packets.get(i).getKey().equals(key)) {
                    return i+1;
                }
            }
            return -1;
        }

    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/13.in");
        List<String> lines = Files.readAllLines(file.toPath());
        Packets packets = new Packets(lines);
        // part 1:
        print(packets.sumRightIndices());
        // part 2:
        String dividerLine1 = "[[2]]";
        String dividerLine2 = "[[6]]";
        packets.addPacket(new Packet(dividerLine1));
        packets.addPacket(new Packet(dividerLine2));
        packets.sort();
        print(packets.getIndex(dividerLine1)*packets.getIndex(dividerLine2));
    }

}
