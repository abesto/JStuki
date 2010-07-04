package net.abesto.jstuki.elements;

import java.util.ArrayList;

/**
 * A conditional statement with an arbitrary number of cases. Each case is
 * an instance of @ref ConditionalCase.
 *
 * Would be somewhat faster but less clear if implemented by extending LinkedHashMap.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class Conditional extends Statement {

    private ArrayList<ConditionalCase> cases;

    public Conditional() {
        super();
        cases = new ArrayList<ConditionalCase>();
    }

    public Conditional(String label) throws Exception
    {
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
}
