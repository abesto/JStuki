package net.abesto.jstuki;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.abesto.jstuki.io.*;
import net.abesto.jstuki.io.JStukiIOException.IncompleteTextSyntaxException;
import net.abesto.jstuki.io.JStukiIOException.SyntaxFileException;
import net.abesto.jstuki.statements.*;

/**
 * Simple test that generates valid python code
 */
public class Main {

    public static void main(String[] args) {
        TextSyntax syntax = new TextSyntax();
        TextOutput out = new TextOutput(syntax);
        try {
            syntax.load(new File("io/syntax/python.ssx"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SyntaxFileException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncompleteTextSyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Procedure p = new Procedure("PrintEvenFibonacci(limit)");

        p.addChild(new Statement("a = 1"));
        p.addChild(new Statement("b = 1"));

        Iteration i = new Iteration("a < limit");
        Conditional mod = new Conditional();
        mod.addCase("a % 2 == 0");
        mod.addCase("a % 2 == 1");
        mod.getCase("a % 2 == 0").addChild(new Statement("print 'Printed from conditional: ',a"));
        mod.getCase("a % 2 == 1").addChild(new Statement("pass"));

        i.addChild(mod);

        BinaryConditional binmod = new BinaryConditional("a % 2 == 0");
        binmod.getTrueCase().addChild(new Statement("print 'Printed from binary conditional: ',a"));
        binmod.getFalseCase().addChild(new Statement("pass"));

        i.addChild(binmod);

        i.addChild(new Statement("c = a"));
        i.addChild(new Statement("a = b"));
        i.addChild(new Statement("b = a+b"));

        p.addChild(i);

        System.out.println(out.renderProcedure(p));

    }
}
