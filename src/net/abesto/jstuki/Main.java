package net.abesto.jstuki;

import java.io.IOException;
import net.abesto.jstuki.actions.ActionProxy.ActionDisabledException;
import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;
import net.abesto.jstuki.io.EnumXML.NoNameInEnumException;
import net.abesto.jstuki.io.EnumXML.NotAnEnumException;
import net.abesto.jstuki.ui.MainFrame;

/**
 * Simple test that generates valid python code (and then some)
 */
public class Main {

    public static void main(String[] args) throws HandlerNotSetException, IOException, ActionDisabledException, NotAnEnumException, NoNameInEnumException {
        MainFrame main = new MainFrame();
        main.main(args);
    }
}
