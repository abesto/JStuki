package net.abesto.jstuki.statements;

import java.util.ArrayList;

/**
 * A construct with a label and an arbitrary number of child statements,
 * where order of children matters
 *
 * Equivalent to a loop
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class ContainerStatement extends Statement {

    private ArrayList<Statement> children;

    public ContainerStatement() {
        super();
        children = new ArrayList<Statement>();
    }

    public ContainerStatement(String label) {
        super(label);
        children = new ArrayList<Statement>();
    }

    public void addChild(Statement child) {
        children.add(child);
        child.setParent(this);
    }

    public ArrayList<Statement> getChildren() {
        return children;
    }
}
