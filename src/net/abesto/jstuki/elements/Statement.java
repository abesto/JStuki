package net.abesto.jstuki.elements;

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
    protected IPseudoContainer parent;

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

    public IPseudoContainer getParent() {
        return parent;
    }

    public void setParent(IPseudoContainer parent) {
        this.parent = parent;
    }
}
