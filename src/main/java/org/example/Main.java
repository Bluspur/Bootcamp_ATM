package org.example;

import org.example.data.Database;
import org.example.terminalApp.TerminalSession;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        TerminalSession terminalSession = new TerminalSession(database, System.in, System.out);
        terminalSession.run();
    }
}