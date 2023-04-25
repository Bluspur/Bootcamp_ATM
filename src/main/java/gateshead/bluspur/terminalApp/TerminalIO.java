package gateshead.bluspur.terminalApp;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TerminalIO {
    public final Scanner input;
    public final PrintStream output;
    public TerminalIO(InputStream input, PrintStream output) {
        this.input = new Scanner(input);
        this.output = output;
    }
}
