package net.abesto.jstuki.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumMap;

import net.abesto.jstuki.elements.ElementHandler;
import net.abesto.jstuki.elements.Statement;
import net.abesto.jstuki.io.EnumXML;

public class ActionCollection<E> extends ElementHandler {
    private E target;
    private EnumMap<ActionType, Action<E>> actions;
    private EnumXML<ActionType> actionLabels;

    public ActionCollection(E target, File lang) 
        throws FileNotFoundException, IOException, EnumXML.IncompleteEnumXMLException
    {
        this.target = target;
        actions = new EnumMap<ActionType, Action<E>>(ActionType.class);
        actionLabels = new EnumXML<ActionType>(ActionType.class);
        actionLabels.load(lang);
    }

    public void call(ActionType type) {
        actions.get(type).getAction().call(target);
    }

    public void select(Statement statement) {

    }

    private class StatementHandler implements Handler<Statement> {
        public void call(Statement statement) {

        }
    }
}
