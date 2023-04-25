package gateshead.bluspur;

import gateshead.bluspur.data.Database;
import gateshead.bluspur.terminalApp.TerminalIO;
import gateshead.bluspur.terminalApp.TerminalSession;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Try to load database from file, if it doesn't exist, create a new one.
        Database database;
        File databaseFile = new File(System.getProperty("user.home"), "database.ser");
        if (databaseFile.exists()) {
            try {
                database = Database.load(databaseFile);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        } else {
            database = new Database(databaseFile);
            try {
                database.save();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        TerminalIO terminalIO = new TerminalIO(System.in, System.out);
        TerminalSession terminalSession = new TerminalSession(terminalIO, database);
        terminalSession.run();
    }
}