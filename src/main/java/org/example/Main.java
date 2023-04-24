package org.example;

import org.example.data.Database;
import org.example.terminalApp.TerminalIO;
import org.example.terminalApp.TerminalSession;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        TerminalIO terminalIO = new TerminalIO(System.in, System.out);
        TerminalSession terminalSession = new TerminalSession(terminalIO, database);
        terminalSession.run();
    }
}