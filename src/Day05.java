import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class Day05 {
    public static void print (Object object) {
        System.out.println(object);
    }
    private static class Instruction {
        private int count;
        private int from;
        private int to;
        public int getCount() {return count;}
        public int getFrom() {return from;}
        public int getTo() {return to;}
        Instruction(String line) {
            String array [] = line.split(" ");
            if (array.length == 6) {
                count = Integer.valueOf(array[1]);
                from = Integer.valueOf(array[3]);
                to =  Integer.valueOf(array[5]);
            }
        }
    }
    private static Map<Integer,Stack<Character>> getStacks(Stack<String> lines) {
        Map<Integer,Stack<Character>> map = new HashMap<>();
        // use linesTemp to recover the original lines in lines
        Stack<String> linesTemp = new Stack<>();
        while (!lines.empty()) {
            String line = lines.pop();
            linesTemp.push(line);
            for (int i = 1; i < line.length() ; i+= 4) {
                if (!map.containsKey(i/4+1)) {
                    map.put(i/4+1, new Stack<>());
                }
                if (!line.substring(i, i+1).trim().isEmpty()) {
                    map.get(i/4+1).add(line.charAt(i));
                }
            }
        }
        // recover the lines
        while(!linesTemp.empty()){
            lines.push(linesTemp.pop());
        }
        return map;
    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/05.in");
        List<String> lines = Files.readAllLines(file.toPath());
        Stack<String> inputStack = new Stack<>();
        List<Instruction> instructions = new Vector<>();
        for(String line : lines) {
            if (line.length() > 0 && line.charAt(0) == '[') {
                inputStack.add(line);
            }
            if (line.length() > 0 && line.charAt(0) == 'm') {
                instructions.add(new Instruction(line));
            }
        }
        Map<Integer,Stack<Character>> stacksPart1 = getStacks(inputStack);
        Map<Integer,Stack<Character>> stacksPart2 = getStacks(inputStack);
        for (Instruction instruction : instructions) {
            int count = instruction.getCount();
            int from = instruction.getFrom();
            int to = instruction.getTo();
            // part 1
            for (int i = 0; i<count; i++) {
                Character temp = stacksPart1.get(from).pop();
                stacksPart1.get(to).push(temp);
            }
            // part 2
            Stack<Character> tempStack = new Stack<>();
            for (int i = 0; i<count; i++) {
                tempStack.push(stacksPart2.get(from).pop());
            }
            for (int i = 0; i<count; i++) {
                stacksPart2.get(to).push(tempStack.pop());
            }

        }
        // part 1:
        String part1 = "";
        for (int i = 1; i <= stacksPart1.size(); i++) {
            part1 = part1 + Character.toString(stacksPart1.get(i).peek());
        }
        // part 1:
        print(part1);

        // part 2:
        String part2 = "";
        for (int i = 1; i <= stacksPart2.size(); i++) {
            part2 = part2 + Character.toString(stacksPart2.get(i).peek());
        }
        // part 2:
        print(part2);
    }
}
