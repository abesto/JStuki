package net.abesto.jstuki.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.abesto.jstuki.elements.Exceptions.ChildNotFoundException;

import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;
import net.abesto.jstuki.elements.Iteration;
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
    public void call(ActionType type, Object... params) throws ActionDisabledException {
        checkActionEnabled(type);
        ArrayList<Statement> results = new ArrayList<Statement>();
        // TODO Refactor this
        for (Statement target : targets) {
            switch (type) {
                case SET_LABEL:
                    results.add(setLabel(target, (String) params[0]));
                    break;
                case MOVE_UP:
                    results.add(moveUp(target));
                    break;
                case MOVE_DOWN:
                    results.add(moveDown(target));
                    break;
                case TO_ITERATION:
                    results.add(toIteration(target));
                    break;
            }
        }
        targets = results;
        fireActionEvent(type);
    }

    public Statement setLabel(Statement target, String label) {
        target.setLabel(label);
        return target;
    }

    public Statement moveUp(Statement target) {
        target.getParent().moveUp(target);
        return target;
    }

    public Statement moveDown(Statement target) {
        target.getParent().moveDown(target);
        return target;
    }

    public Statement toIteration(Statement target) {
        Iteration i = new Iteration(target.getLabel());
        try {
            target.getParent().replace(target, i);
            if (i.getChildren().isEmpty()) {
                i.addChild(new Statement("SKIP"));
            }
            return i;
        } catch (ChildNotFoundException ex) {
            Logger.getLogger(ActionProxy.class.getName()).log(Level.SEVERE, null, ex);
            return target;
        }
    }
}
