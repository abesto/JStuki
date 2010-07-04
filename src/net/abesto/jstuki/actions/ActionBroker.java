package net.abesto.jstuki.actions;

import java.util.ArrayList;
import java.util.EnumMap;

import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;

import net.abesto.jstuki.elements.*;

/**
 * Generate list of possible actions based on selected elements
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 */
class ActionBroker extends ElementHandler {

    /**
     * Action types explicitly enabled by one of the selected elements
     */
    private EnumMap<ActionType, Boolean> enabled;
    /**
     * Action types explicitly disabled by one of the selected elements
     */
    private EnumMap<ActionType, Boolean> disabled;

    public ActionBroker() {
        enabled = new EnumMap<ActionType, Boolean>(ActionType.class);
        disabled = new EnumMap<ActionType, Boolean>(ActionType.class);
        setHandlers(new StatementHandler(), new IterationHandler());
    }

    public void select(Statement... statements) throws HandlerNotSetException {
        for (ActionType key : ActionType.values()) {
            enabled.put(key, false);
            disabled.put(key, false);
        }
        for (Statement statement : statements) {
            handle(statement);
        }
    }


    private class StatementHandler implements IHandler<Statement> {

        public void call(Statement statement) {
            enable(
                ActionType.SET_LABEL,
                ActionType.INSERT_BEFORE, ActionType.INSERT_AFTER,
                ActionType.TO_ITERATION);
        }
    }

    private class IterationHandler extends StatementHandler {

        public void call(Iteration iteration) {
            super.call(iteration);
            disable(ActionType.TO_ITERATION);
            enable(ActionType.TO_STATEMENT);
        }
    }

    private void disable(ActionType... types) {
        for (ActionType type : types) {
            disabled.put(type, true);
        }
    }

    private void enable(ActionType... types) {
        for (ActionType type : types) {
            enabled.put(type, true);
        }
    }

    public ArrayList<ActionType> getEnabledActionTypes() {
        ArrayList<ActionType> ret = new ArrayList<ActionType>();

        for (ActionType type : ActionType.values()) {
            if (isEnabled(type)) {
                ret.add(type);
            }
        }

        return ret;
    }

    public boolean isEnabled(ActionType type) {
        return enabled.get(type) && !disabled.get(type);
    }
}
