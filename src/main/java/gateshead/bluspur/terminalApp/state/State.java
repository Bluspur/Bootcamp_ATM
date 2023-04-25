package gateshead.bluspur.terminalApp.state;

import gateshead.bluspur.Command;
import gateshead.bluspur.terminalApp.TerminalSession;

public abstract class State {
    final TerminalSession context;
    public State(TerminalSession session) {
        this.context = session;
    }
    public abstract void printContextualInformation();
    public abstract void handleCommand(Command command);
}
