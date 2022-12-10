import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {
    public static void print (Object object) {
        System.out.println(object);
    }

    public static class Instruction {
        enum Type {
            NOOP,
            ADDX
        }
        private Type type;
        private int inst;
        Type getType() {return type;}
        int getInst() {return inst;}
        Instruction(String line) {
            if (line.split(" ").length == 1) {
                type = Type.NOOP;
            } else {
                type = Type.ADDX;
                inst = Integer.parseInt(line.split(" ")[1]);
            }
        }
    }

    public static class Program {
        List<Instruction> instructions = new ArrayList<>();
        List<DuringCycleListener> listeners = new ArrayList<>();
        int cycle = 0;
        int valueInX = 1;
        Program(List<String> lines) {
            for (String line : lines) {
                instructions.add(new Instruction(line));
            }
        }
        void addListener(DuringCycleListener listener) {
            this.listeners.add(listener);
        }
        void callListeners() {
            for (DuringCycleListener listener : listeners) {
                listener.listen(this.cycle, this.valueInX);
            }
        }
        void advanceCycle() {
            cycle += 1;
            callListeners();
        }
        void runProgram() {
            for (Instruction instruction : instructions) {
                advanceCycle();
                switch (instruction.getType()) {
                    case NOOP:
                        break;
                    case ADDX:
                        advanceCycle();
                        this.valueInX += instruction.getInst();
                        break;
                }
            }
        }
    }

    interface DuringCycleListener {
        void listen(int cycle, int currentValueInX);
    }

    // Listener for part 1:
    static class SignalStrengthListener implements DuringCycleListener {
        int signalStrength = 0;
        SignalStrengthListener() {}
        @Override
        public void listen(int cycle, int currentValueInX) {
            if ((cycle - 20) % 40 == 0 && cycle <= 220) {
                signalStrength += cycle * currentValueInX;
            }
        }
        public int getSignalStrength() {
            return signalStrength;
        }
    }

    // Listener for part 2:
    static class CRTListener implements DuringCycleListener {
        List<String> screen = new ArrayList<>();
        CRTListener() {
            // initialize an empty screen with width 40:
            for (int i = 0; i<6; i++) {
                screen.add("                                        ");
            }
        }
        @Override
        public void listen(int cycle, int currentValueInX) {
            // indexing of CRT starts at 1:
            cycle -= 1;
            Set<Integer> sprite = new HashSet<>();
            sprite.add(currentValueInX);
            sprite.add(currentValueInX-1);
            sprite.add(currentValueInX+1);
            if (sprite.contains(cycle%40)) {
                String currentLine = screen.get(cycle/40);
                StringBuilder builder = new StringBuilder(currentLine);
                builder.setCharAt(cycle % 40, '#');
                screen.set(cycle/40, builder.toString());
            }
        }
        public void printCRT() {
            for (String line : screen) {
                print(line);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("src/10.in");
        List<String> lines = Files.readAllLines(file.toPath());
        Program program = new Program(lines);

        SignalStrengthListener signalStrengthListener = new SignalStrengthListener();
        CRTListener crtListener = new CRTListener();

        program.addListener(signalStrengthListener);
        program.addListener(crtListener);

        program.runProgram();
        // part 1:
        print(signalStrengthListener.getSignalStrength());
        // part 2 (note that you need to look at the print out and decipher the capital letters):
        crtListener.printCRT();
    }

}
