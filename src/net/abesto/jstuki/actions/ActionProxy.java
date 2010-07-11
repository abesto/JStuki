package net.abesto.jstuki.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;
import net.abesto.jstuki.elements.Statement;

/**
 * Implement the actions called by the UI, use ActionBroker to select
 * and check for enabled actions.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 */
public class ActionProxy {

    private LinkedList<ElementActionListener> listeners;

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
        listeners = new LinkedList<ElementActionListener>();
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

    public boolean removeActionListener(ElementActionListener l) {
        return listeners.remove(l);
    }

    public boolean addActionListener(ElementActionListener l) {
        return listeners.add(l);
    }

    public boolean isEnabled(ActionType type) {
        return broker.isEnabled(type);
    }

    private void fireActionEvent(ActionType type) {
        for (ElementActionListener l : listeners) {
            ElementActionEvent e = new ElementActionEvent(targets, type.ordinal());
            l.actionPerformed(e);
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
        fireActionEvent(ActionType.SET_LABEL);
    }

    public void moveUp() throws ActionDisabledException {
        checkActionEnabled(ActionType.MOVE_UP);
        for (Statement target : targets) {
            target.getParent().moveUp(target);
        }
        fireActionEvent(ActionType.MOVE_UP);
    }

    public void moveDown() throws ActionDisabledException {
        checkActionEnabled(ActionType.MOVE_DOWN);
        for (Statement target : targets) {
            target.getParent().moveDown(target);
        }
        fireActionEvent(ActionType.MOVE_DOWN);
    }

}
