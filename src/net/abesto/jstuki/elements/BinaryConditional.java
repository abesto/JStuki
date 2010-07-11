package net.abesto.jstuki.elements;

/**
 * A conditional with one condition, a true and a false case.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class BinaryConditional extends Statement implements IPseudoContainer {
    private BinaryConditionalCase trueCase;
    private BinaryConditionalCase falseCase;

    public BinaryConditional(String label) {
        super(label);

        trueCase = new BinaryConditionalCase(label);
        trueCase.setParent(this);

        falseCase = new BinaryConditionalCase(label);
        falseCase.setParent(this);
    }

    public BinaryConditionalCase getFalseCase() {
        return falseCase;
    }

    public BinaryConditionalCase getTrueCase() {
        return trueCase;
    }

    public boolean isFirst(Statement s) {
        return true;
    }

    public boolean isLast(Statement s) {
        return true;
    }

    public void moveUp(Statement s) {
        parent.moveUp(this);
    }

    public void moveDown(Statement s) {
        parent.moveDown(this);
    }
}
