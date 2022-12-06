import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Day04 {
    public static void print (Object object) {
        System.out.println(object);
    }
    private static boolean isContained (int l1, int r1, int l2, int r2) {
        if (l1 <= l2 && r1 >= r2) return true;
        if (l2 <= l1 && r2 >= r1) return true;
        return false;
    }
    private static boolean isBetween(int p, int l, int r) {
        return l <= p && p <= r;
    }
    private static boolean overlaps (int l1, int r1, int l2, int r2) {
        return isBetween(l1, l2, r2) || isBetween(r1, l2, r2)
        || isBetween(r2, l1, r1) || isBetween(l2, l1, r1);
    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/04.in");
        List<String> lines = Files.readAllLines(file.toPath());
        int count1 = 0;
        int count2 = 0;
        for(String line : lines) {
            String temp [] = line.split(",");
            String section1 = temp[0];
            String section2 = temp[1];
            String temp1 [] = section1.split("-");
            String temp2 [] = section2.split("-");
            int left1 = Integer.parseInt(temp1[0]);
            int right1 = Integer.parseInt(temp1[1]);
            int left2 = Integer.parseInt(temp2[0]);
            int right2 = Integer.parseInt(temp2[1]);
            
            if (isContained(left1, right1, left2, right2)) {
                count1 += 1;
            };
            if (overlaps(left1, right1, left2, right2)) count2 += 1;
        }
        // part 1:
        print(count1);
        // part 2:
        print(count2);

    }
}
