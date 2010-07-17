package net.abesto.jstuki.elements;

import net.abesto.jstuki.elements.Exceptions.ChildNotFoundException;

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

    public void replace(Statement victim, Statement target) throws ChildNotFoundException {
        BinaryConditionalCase bcase = (BinaryConditionalCase) target;
        if (trueCase.equals(victim)) {
            bcase.addChildren(trueCase.getChildren());
            bcase.parent = this;
            trueCase = bcase;
        } else if (falseCase.equals(victim)) {
            bcase.addChildren(falseCase.getChildren());
            bcase.parent = this;
            falseCase = bcase;
        } else {
            throw new Exceptions.ChildNotFoundException();
        }

    }
}
