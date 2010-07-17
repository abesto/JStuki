package net.abesto.jstuki.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;
import net.abesto.jstuki.elements.Procedure;
import net.abesto.jstuki.elements.Statement;
import net.abesto.jstuki.io.EnumXML;
import net.abesto.jstuki.io.EnumXML.IncompleteEnumXMLException;
import net.abesto.jstuki.io.EnumXML.NoNameInEnumException;
import net.abesto.jstuki.io.EnumXML.NotAnEnumException;
import net.abesto.jstuki.io.EnumXMLDir;
import net.abesto.jstuki.io.TextOutput;

public class ViewsTabbedPane extends javax.swing.JTabbedPane {

    private ArrayList<CodeListing> codeListings;

    public ViewsTabbedPane() {
        codeListings = new ArrayList<CodeListing>();
    }

    public void loadTabs(File dir) {
        // Create tabs based on loadable syntax descriptor files
        EnumXMLDir<TextOutput.Template> syntaxes = new EnumXMLDir<TextOutput.Template>(TextOutput.Template.class);
        try {
            syntaxes.load(dir);
            for (EnumXML<TextOutput.Template> syntax : syntaxes.list()) {
                javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.BorderLayout());
                CodeListing listing = new CodeListing(syntax);
                codeListings.add(listing);
                panel.add(listing);
                add(syntax.getName(), panel);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewsTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ViewsTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoNameInEnumException ex) {
            Logger.getLogger(ViewsTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncompleteEnumXMLException ex) {
            Logger.getLogger(ViewsTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotAnEnumException ex) {
            Logger.getLogger(ViewsTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Synchronize code listing selections; might be better to do this only on view change?
        addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                CodeListing source = (CodeListing) e.getSource();
                ListSelectionListener[] listeners = source.getListSelectionListeners();
                ArrayList<Statement> statements = source.getSelectedStatements();
                for (CodeListing listing : codeListings) {
                    if (!listing.equals(source)) {
                        listing.removeAllListSelectionListeners();
                        listing.setSelectedStatements(statements);
                        listing.addListSelectionListeners(listeners);
                    }
                }
            }
        });
    }

    /**
     * Render a Procedure into all views. Might be better to render only
     * current view and on view switch?
     *
     * @param p
     */
    public void display(Procedure p) {
        for (CodeListing listing : codeListings) {
            try {
                listing.display(p);
            } catch (HandlerNotSetException ex) {
                Logger.getLogger(ViewsTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addListSelectionListener(javax.swing.event.ListSelectionListener l) {
        for (CodeListing listing : codeListings) {
            listing.addListSelectionListener(l);
        }
    }

    /**
     * Set the selected statements on all views
     *
     * @param statements
     */
    void setSelectedStatements(ArrayList<Statement> statements) {
        for (CodeListing listing : codeListings) {
            listing.setSelectedStatements(statements);
        }
    }
}
