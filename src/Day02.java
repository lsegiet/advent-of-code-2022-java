import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Please, don't air this. This doesn't represent me. This doesn't represent me.
// TODO(me): use mod 3 arithmetic
public class Day02 {
    public static void print (Object object) {
        System.out.println(object);
    }
    private static Map<String, String> beats = new HashMap<>();
    private static Map<String, String> loses = new HashMap<>();
    private static boolean firstBeatsSecond(String first, String second) {
        if (beats.get(first) == second) {
            return true;
        }
        return false;
    }
    private static String PAPER = "Paper";
    private static String ROCK = "Rock";
    private static String SCISSORS = "Scissors";
    public static void main(String[] args) throws Exception {
        File file = new File("src/02.in");
        Scanner scanner = new Scanner(file);
        beats.put(ROCK, SCISSORS);
        beats.put(PAPER, ROCK);
        beats.put(SCISSORS, PAPER);
        loses.put(SCISSORS, ROCK);
        loses.put(ROCK, PAPER);
        loses.put(PAPER, SCISSORS);
        Map<String,Integer> signToPoints = new HashMap<>();
        signToPoints.put(ROCK, 1);
        signToPoints.put(PAPER, 2);
        signToPoints.put(SCISSORS, 3);
        Map<String,String> letterToSign = new HashMap<>();
        letterToSign.put("A", ROCK);
        letterToSign.put("B", PAPER);
        letterToSign.put("C", SCISSORS);
        letterToSign.put("X", ROCK);
        letterToSign.put("Y", PAPER);
        letterToSign.put("Z", SCISSORS);
        
        Integer score1 = 0;
        Integer score2 = 0;
        while(scanner.hasNext()) {
            String temp [] = scanner.nextLine().split(" ");
            String opponent;
            String me1;
            String me2;
            if (temp.length == 2) {
                opponent = letterToSign.get(temp[0]);
                me1 = letterToSign.get(temp[1]);
                me2 = letterToSign.get(temp[1]);
                // this check added for part 2:
                if (me2 == ROCK) {
                    me2 = beats.get(opponent);
                } else if (me2 == PAPER) {
                    me2 = opponent;
                } else {
                    me2 = loses.get(opponent);
                }
                if (opponent == me1) {
                    score1 += 3;
                } else if (firstBeatsSecond(me1, opponent)) {
                    score1 += 6;
                }
                if (opponent == me2) {
                    score2 += 3;
                } else if (firstBeatsSecond(me2, opponent)) {
                    score2 += 6;
                }
                score1 += signToPoints.get(me1);
                score2 += signToPoints.get(me2);
            }
        }
        print(score1);
        print(score2);
        scanner.close();
    }
}
