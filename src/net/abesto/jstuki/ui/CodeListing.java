package net.abesto.jstuki.ui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;
import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;
import net.abesto.jstuki.elements.Procedure;
import net.abesto.jstuki.elements.Statement;
import net.abesto.jstuki.io.EnumXML;
import net.abesto.jstuki.io.TextOutput;

public class CodeListing extends javax.swing.JList {

    private TextOutput renderer;
    private ArrayList<TextOutput.Line> lines;

    public CodeListing(EnumXML<TextOutput.Template> syntax) {
        renderer = new TextOutput(syntax);
        setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    public void display(Procedure p) throws HandlerNotSetException {
        lines = renderer.renderProcedure(p);
        ArrayList<String> strLines = new ArrayList<String>();
        for (TextOutput.Line line : lines) {
            strLines.add(line.getString());
        }
        setListData(strLines.toArray());
    }

    public void setSelectedStatements(ArrayList<Statement> statements) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < lines.size(); i++) {
            Statement find = lines.get(i).getStatement();
            for (Statement statement : statements) {
                if (find.equals(statement)) {
                    indices.add(i);
                }
            }
        }

        int[] pass = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            pass[i] = indices.get(i);
        }
        setSelectedIndices(pass);
    }

    public Statement getSelectedStatement() {
        int index = getSelectedIndex();
        if (index == -1) {
            return null;
        } else {
            return lines.get(getSelectedIndex()).getStatement();
        }
    }
}
