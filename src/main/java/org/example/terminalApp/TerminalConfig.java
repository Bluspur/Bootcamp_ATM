package org.example.terminalApp;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TerminalConfig {
    public final Scanner input;
    public final PrintStream output;
    public TerminalConfig(InputStream input, PrintStream output) {
        this.input = new Scanner(input);
        this.output = output;
    }
}
