package net.abesto.jstuki.elements;

import java.util.ArrayList;

/**
 * A conditional statement with an arbitrary number of cases. Each case is
 * an instance of @ref ContainerStatement.
 *
 * Would be somewhat faster but less clear if implemented by extending LinkedHashMap.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class Conditional extends Statement {

    private ArrayList<ContainerStatement> cases;

    public Conditional() {
        super();
        cases = new ArrayList<ContainerStatement>();
    }

    public Conditional(String label) throws Exception
    {
        throw new Exception("Conditional constructor doesn't accept a parameter");
    }

    public void addCase(String condition) {
        ContainerStatement c = new ContainerStatement(condition);
        c.setParent(this);
        cases.add(c);
    }

    public ArrayList<ContainerStatement> getCases() {
        return cases;
    }

    public ContainerStatement getCase(String condition) {
        for (ContainerStatement c : cases) {
            if (c.getLabel().equals(condition)) {
                return c;
            }
        }
        return null;
    }
}
