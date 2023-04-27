package gateshead.bluspur.terminalApp;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * A configuration class that holds the input and output streams for the terminal.
 * This is so that we can easily pass the input and output streams around without
 * having to pass two separate objects. Useful for unit testing.
 * @author craig
 */
public class TerminalIO {
    public final Scanner input;
    public final PrintStream output;
    public TerminalIO(InputStream input, PrintStream output) {
        this.input = new Scanner(input);
        this.output = output;
    }
}
