/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.abesto.jstuki.elements;

/**
 *
 * @author abesto
 */
public class BinaryConditionalCase extends ContainerStatement {

    BinaryConditionalCase(String label) {
        super(label);
    }

    @Override
    public void setLabel(String label) {
        parent.setLabel(label);
    }

    @Override
    public String getLabel() {
        return parent.getLabel();
    }
}
