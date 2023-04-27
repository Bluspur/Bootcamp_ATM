package gateshead.bluspur.terminalApp.state;

import gateshead.bluspur.Command;
import gateshead.bluspur.terminalApp.TerminalSession;

/**
 * Abstract class for the state of the terminal session.
 */
public abstract class State {
    final TerminalSession context;
    public State(TerminalSession session) {
        this.context = session;
    }
    /**
     * Prints contextual information for the state.
     */
    public abstract void printContextualInformation();

    /**
     * Performs an action based on the command.
     * @param command The command to evaluate.
     */
    public abstract void handleCommand(Command command);
}
