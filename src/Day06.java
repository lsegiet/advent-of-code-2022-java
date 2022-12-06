import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class Day06 {
    public static void print (Object object) {
        System.out.println(object);
    }
    private static boolean hasRepeated(List<Character> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i+1; j < list.size(); j++) {
                if (list.get(i) == list.get(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int markerStartIndex(String signal, int markerLength) {
        LinkedList<Character> buffer = new LinkedList<>();
        int count = 0;
        for (char c : signal.toCharArray()) {
            count += 1;
            buffer.add(c);
            if (buffer.size() == markerLength) {
                if (hasRepeated(buffer)) {
                    buffer.removeFirst();
                } else {
                    return(count);
                }
            }
        }
        return -1;
    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/06.in");
        List<String> lines = Files.readAllLines(file.toPath());
        String signal = lines.get(0);
        // part 1:
        print(markerStartIndex(signal, 4));
        // part 2:
        print(markerStartIndex(signal, 14));
    }
}
