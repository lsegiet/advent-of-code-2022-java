import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day01 {
    public static void print (Object object) {
        System.out.println(object);
    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/01.in");
        Scanner scanner = new Scanner(file);
        int max = 0;
        int current = 0;
        ArrayList<Integer> list = new ArrayList<>();
        while(scanner.hasNext()) {
            String temp = scanner.nextLine();
            if (temp.length() == 0) {
                if (current > max) {
                    max = current;
                }
                list.add(current);
                current = 0;
            } else {
                current += Integer.valueOf(temp);
            }
        }
        // part 1:
        print(max);
        // part 2:
        Collections.sort(list);
        print(list.get(list.size()-3) + list.get(list.size()-2) + list.get(list.size()-1));
        scanner.close();
    }
}
