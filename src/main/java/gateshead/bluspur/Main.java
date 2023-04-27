package gateshead.bluspur;

import gateshead.bluspur.data.Database;
import gateshead.bluspur.terminalApp.TerminalIO;
import gateshead.bluspur.terminalApp.TerminalSession;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Database database;
        File databaseFile = new File(System.getProperty("user.home"), "database.ser");

        try {
            database = Database.load(databaseFile);
        } catch (IOException | ClassNotFoundException e) {
            database = new Database(databaseFile);
            try {
                database.save();
            } catch (IOException i) {
                i.printStackTrace();
                return;
            }
        }

        TerminalIO terminalIO = new TerminalIO(System.in, System.out);
        TerminalSession terminalSession = new TerminalSession(terminalIO, database);
        terminalSession.run();
    }
}