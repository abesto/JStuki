package net.abesto.jstuki.elements;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A construct with a label and an arbitrary number of child statements,
 * where order of children matters
 *
 * Equivalent to a loop
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class ContainerStatement extends Statement implements IPseudoContainer {

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

    public boolean isFirst(Statement statement) {
        return (children.indexOf(statement) == 0);
    }

    public boolean isLast(Statement statement) {
        return (children.indexOf(statement) == children.size()-1);
    }

    public void moveUp(Statement statement) {
        int id = children.indexOf(statement);
        Collections.swap(children, id-1, id);
    }

    public void moveDown(Statement statement) {
        int id = children.indexOf(statement);
        Collections.swap(children, id, id+1);
    }
}
