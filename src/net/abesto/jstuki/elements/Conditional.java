package net.abesto.jstuki.elements;

import java.util.ArrayList;
import java.util.Collections;
import net.abesto.jstuki.elements.Exceptions.ChildNotFoundException;

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

    public void replace(Statement victim, Statement target)
        throws ChildNotFoundException, UnsupportedOperationException {
        try {
            ConditionalCase tcase = (ConditionalCase) target;
            ConditionalCase vcase = (ConditionalCase) victim;

            int index = cases.indexOf(vcase);
            if (index != -1) {
                tcase.parent = this;
                cases.set(index, tcase);
                tcase.addChildren(vcase.getChildren());
            } else {
                throw new Exceptions.ChildNotFoundException();
            }
        } catch (ClassCastException e) {
            // The cast of victim or target to ConditionalCase failed;
            // we want to replace this Conditional, not one of it's children.
            parent.replace(this, target);
        }
    }
}
