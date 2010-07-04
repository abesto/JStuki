/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.abesto.jstuki.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import net.abesto.jstuki.io.EnumXML.IncompleteEnumXMLException;
import net.abesto.jstuki.io.EnumXML.NoNameInEnumException;
import net.abesto.jstuki.io.EnumXML.NotAnEnumException;

/**
 *
 * @author abesto
 */
public class EnumXMLDir<E> {

    private Class enumClass;
    private ArrayList<EnumXML<E>> list;

    public EnumXMLDir(Class enumClass) {
        this.enumClass = enumClass;
        list = new ArrayList<EnumXML<E>>();
    }

    public void load(File dir) throws FileNotFoundException, IOException,
        NoNameInEnumException, IncompleteEnumXMLException, NotAnEnumException {
        list.clear();
        FilenameFilter xmlFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.matches(".*");
            }
        };

        for (String name : dir.list(xmlFilter)) {
            try {
                EnumXML<E> current = new EnumXML<E>(enumClass);
                current.load(new File(dir, name));
                list.add(current);
            } catch (IncompatibleClassChangeError e) {
                // An error just means we don't want this file
            }
        }
    }

    public ArrayList<EnumXML<E>> list() {
        return list;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (EnumXML<E> xml : list) {
            names.add(xml.getName());
        }
        return names;
    }

    public EnumXML<E> get(int index) {
        return list.get(index);
    }
}
