package net.abesto.jstuki.statements;

import java.io.Serializable;

/**
 * Root of the class hierarchy of structogram entities.
 * One instance corresponds to one statement.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class Statement implements Serializable {

    protected String label;
    protected Statement parent;

    public Statement() {
        parent = null;
    }

    public Statement(String label) {
        parent = null;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Statement getParent() {
        return parent;
    }

    public void setParent(Statement parent) {
        this.parent = parent;
    }
}
