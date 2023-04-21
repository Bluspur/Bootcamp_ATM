package org.example.terminalApp.state;

import org.example.Command;
import org.example.terminalApp.TerminalSession;

public abstract class State {
    final TerminalSession context;
    public State(TerminalSession session) {
        this.context = session;
    }
    public abstract void printContextualInformation();
    public abstract void handleCommand(Command command);
}
