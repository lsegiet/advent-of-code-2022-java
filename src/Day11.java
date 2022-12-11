import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day11 {
    public static void print (Object object) {
        System.out.println(object);
    }
    static class Operation {
        enum Operator {
            PLUS,
            MULTIPLY
        }
        enum Operant {
            OLD,
            CONST
        }
        private Operator operator;
        private Operant operant;
        private long constant;
        Operation(char operator, String what) {
            this.operator = operator == '+' ? Operator.PLUS : Operator.MULTIPLY; 
            this.operant = what.equals("old") ? Operant.OLD : Operant.CONST;
            this.constant = what.equals("old") ? 0 : Long.parseLong(what);
        }
        long operate(long value) {
            long operant = this.operant == Operant.OLD ? value : this.constant;
            return this.operator == Operator.PLUS ? value + operant : value * operant;
        }
    }
    static class Monkey {
        private final Queue<Long> items = new LinkedList<>();
        private final Operation operation;
        private final int divisibility;
        private final int indIfTrue;
        private final int indIfFalse;
        private int inspected;
        private int divisibilitiesMultiplied = 1;

        Monkey(List<String> lines) {
            String tempItems [] = lines.get(1).substring(18).split(", ");
            for (int i = 0; i < tempItems.length ; i++) {
                items.add(Long.parseLong(tempItems[i]));
            }
            operation = new Operation(lines.get(2).charAt(23),lines.get(2).substring(25));
            divisibility = Integer.parseInt(lines.get(3).substring(21));
            indIfTrue = Integer.parseInt(lines.get(4).substring(29));
            indIfFalse = Integer.parseInt(lines.get(5).substring(30));
            inspected = 0;
        }

        private void addItem(long value) {
            items.add(value);
        }

        void play(List<Monkey> monkeys, boolean worried) {
            if (divisibilitiesMultiplied == 1) {
                for (Monkey monkey : monkeys) {
                    divisibilitiesMultiplied *= monkey.divisibility;
                }
            }
            while (!items.isEmpty()) {
                long item = items.remove();
                item = operation.operate(item);
                if (!worried) {
                    item /= 3;
                }
                item %= divisibilitiesMultiplied;
                if (item % divisibility == 0) {
                    monkeys.get(indIfTrue).addItem(item);
                } else {
                    monkeys.get(indIfFalse).addItem(item);
                }
                inspected += 1;
            }
        }

        int numOfInspections () {
            return inspected;
        }
    }
    static class Monkeys {
        List<Monkey> monkeys = new ArrayList<>();
        Monkeys(List<String> lines) {
            for (int i = 0; i < (lines.size()+1)/7 ; i++) {
                monkeys.add(new Monkey(lines.subList(i*7, i*7+6)));
            }
        }
        private void playOneRound(boolean worried) {
            for (Monkey monkey : monkeys) {
                monkey.play(monkeys, worried);
            }
        }
        long monkeyBusinessAfterRounds(int rounds, boolean worried) {
            while(rounds > 0) {
                rounds -= 1;
                playOneRound(worried);
            }
            return monkeyBusiness();
        }
        private long monkeyBusiness() {
            List<Integer> inspections = new LinkedList<>();
            for (Monkey monkey : monkeys) {
                inspections.add(monkey.numOfInspections());
            }
            Collections.sort(inspections);
            return (long)inspections.get(inspections.size()-2) * (long)inspections.get(inspections.size()-1);
        }
    }
    public static void main(String[] args) throws Exception {
        File file = new File("src/11.in");
        List<String> lines = Files.readAllLines(file.toPath());
        // part 1:
        Monkeys monkeys = new Monkeys(lines);
        print(monkeys.monkeyBusinessAfterRounds(20, false));
        // part 2:
        monkeys = new Monkeys(lines);
        print(monkeys.monkeyBusinessAfterRounds(10000, true));
    }

}
