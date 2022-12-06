import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day03 {
    public static void print (Object object) {
        System.out.println(object);
    }
    public static int getValue(char c) {
        return c - (int) 'a' >= 0 ? c - (int) 'a' + 1 : c - (int) 'A' + 27;
    }
    public static char findCommon (Map<Integer,Set<Character>> map) {
        Set<Character> intersection = map.get(0);
        for (int i : map.keySet()) {
            intersection.retainAll(map.get(i));
        }
        return (char) intersection.toArray()[0];
    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/03.in");
        Scanner scanner = new Scanner(file);
        int sum = 0;
        // part 1:
        List<String> lines = Files.readAllLines(file.toPath());
        for(String rucksack : lines) {
            Set<Character> elements = new HashSet<>();        
            for (int i = 0; i < rucksack.length()/2; i++) {
                elements.add(rucksack.charAt(i));
            }
            for (int i = rucksack.length()/2; i < rucksack.length(); i++) {
                char c = rucksack.charAt(i);
                if (elements.contains(c)) {
                    sum += getValue(c);
                    i = rucksack.length();
                }
            }
        }
        print(sum);
        // part 2:
        sum = 0;
        int groupInd = 0;
        Map<Integer,Set<Character>> group = new HashMap<>(); 
        for(String rucksack : lines) {
            if (groupInd == 0) {
                group = new HashMap<>();
            }
            group.put(groupInd, new HashSet<>());
            for(char c : rucksack.toCharArray()) {
                group.get(groupInd).add(c);
            }
            if (groupInd == 2) {
                sum += getValue(findCommon(group));
            }
            groupInd += 1;
            groupInd %= 3;
        }
        print(sum);
        // part 2:
        scanner.close();
    }
}
