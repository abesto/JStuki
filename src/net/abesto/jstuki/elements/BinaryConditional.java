package net.abesto.jstuki.elements;

/**
 * A conditional with one condition, a true and a false case.
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 * @todo implement writeObject, readObject
 */
public class BinaryConditional extends Statement {
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
}
