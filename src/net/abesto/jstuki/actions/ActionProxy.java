package net.abesto.jstuki.actions;

import java.util.ArrayList;
import java.util.Arrays;

import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;
import net.abesto.jstuki.elements.Statement;

/**
 * Implement the actions called by the UI, use ActionBroker to select
 * and check for enabled actions.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 */
public class ActionProxy {

    public static class ActionDisabledException extends Exception {
        public ActionDisabledException(ActionType type) {
            super("Tried to call disabled action ".concat(type.toString()));
        }
    }

    private ActionBroker broker;
    private ArrayList<Statement> targets;

    public ActionProxy() {
        broker = new ActionBroker();
        targets = new ArrayList<Statement>();
    }

    public void select(Statement... statements) throws HandlerNotSetException {
        broker.select(statements);
        targets = new ArrayList(Arrays.asList(statements));
    }

    private void checkActionEnabled(ActionType type) throws ActionDisabledException {
        if (broker.isEnabled(type) == false) {
            throw new ActionDisabledException(type);
        }
    }

    /******************************
     * And now the actual actions *
     ******************************/
    public void setLabel(String label) throws ActionDisabledException {
        checkActionEnabled(ActionType.SET_LABEL);
        for (Statement target : targets) {
            target.setLabel(label);
        }
    }
}
