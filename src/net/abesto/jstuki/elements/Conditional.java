package net.abesto.jstuki.elements;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A conditional statement with an arbitrary number of cases. Each case is
 * an instance of @ref ConditionalCase.
 *
 * Would be somewhat faster but less clear if implemented by extending LinkedHashMap.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class Conditional extends Statement implements IPseudoContainer {

    private ArrayList<ConditionalCase> cases;

    public Conditional() {
        super();
        cases = new ArrayList<ConditionalCase>();
    }

    public Conditional(String label) throws Exception {
        throw new Exception("Conditional constructor doesn't accept a parameter");
    }

    public void addCase(String condition) {
        ConditionalCase c = new ConditionalCase(condition);
        c.setParent(this);
        cases.add(c);
    }

    public ArrayList<ConditionalCase> getCases() {
        return cases;
    }

    public ConditionalCase getCase(String condition) {
        for (ConditionalCase c : cases) {
            if (c.getLabel().equals(condition)) {
                return c;
            }
        }
        return null;
    }

    public boolean isFirst(Statement statement) {
        return (cases.indexOf(statement) == 0);
    }

    public boolean isLast(Statement statement) {
        return (cases.indexOf(statement) == cases.size() - 1);
    }

    public void moveUp(Statement statement) {
        int id = cases.indexOf(statement);
        if (id > 0) {
            Collections.swap(cases, id - 1, id);
        } else {
            parent.moveUp(this);
        }
    }

    public void moveDown(Statement statement) {
        int id = cases.indexOf(statement);
        Collections.swap(cases, id, id + 1);
    }
}
