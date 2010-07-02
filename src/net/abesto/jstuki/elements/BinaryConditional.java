package net.abesto.jstuki.elements;

/**
 * A conditional with one condition, a true and a false case.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class BinaryConditional extends Statement {
    private ContainerStatement trueCase;
    private ContainerStatement falseCase;

    public BinaryConditional(String label) {
        super(label);

        trueCase = new ContainerStatement();
        trueCase.setParent(this);

        falseCase = new ContainerStatement();
        falseCase.setParent(this);
    }

    public ContainerStatement getFalseCase() {
        return falseCase;
    }

    public ContainerStatement getTrueCase() {
        return trueCase;
    }
}
