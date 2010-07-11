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
        setHandlers(
            new ProcedureHandler(),
            new StatementHandler(), new IterationHandler(),
            new ConditionalHandler(), new ConditionalCaseHandler(),
            new BinaryConditionalHandler(), new BinaryConditionalCaseHandler()
            );
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


    private class ProcedureHandler implements IHandler<Procedure> {

        public void call(Procedure procedure) {
            enable(ActionType.SET_LABEL);
            disable(ActionType.MOVE_UP, ActionType.MOVE_DOWN);
        }
    }

    private class StatementHandler implements IHandler<Statement> {

        public void call(Statement statement) {
            enable(
                ActionType.SET_LABEL,
                ActionType.INSERT_BEFORE, ActionType.INSERT_AFTER,
                ActionType.TO_ITERATION);
            if (!statement.getParent().isFirst(statement)) {
                enable(ActionType.MOVE_UP);
            }
            if (!statement.getParent().isLast(statement)) {
                enable(ActionType.MOVE_DOWN);
            }
        }
    }

    private class BinaryConditionalHandler implements IHandler<BinaryConditional> {
        public void call(BinaryConditional binary) throws HandlerNotSetException {
            handle(binary, Statement.class);
        }
    }

    private class BinaryConditionalCaseHandler implements IHandler<BinaryConditionalCase> {
        public void call(BinaryConditionalCase bcase) throws HandlerNotSetException {
            handle((BinaryConditional)bcase.getParent());
        }
    }

    private class ConditionalHandler implements IHandler<Conditional> {
        public void call(Conditional conditional) throws HandlerNotSetException {
            handle(conditional, Statement.class);
            disable(ActionType.SET_LABEL);
        }
    }

    private class ConditionalCaseHandler implements IHandler<ConditionalCase> {
        public void call(ConditionalCase ccase) throws HandlerNotSetException {
            handle(ccase, Statement.class);
            Conditional c = (Conditional) ccase.getParent();
            if (c.isFirst(ccase) && !c.getParent().isFirst(c)) {
                // This is the first cond.case, but we'll move the whole conditional
                enable(ActionType.MOVE_UP);
            }
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
