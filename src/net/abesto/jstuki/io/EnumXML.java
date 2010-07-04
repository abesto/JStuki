package net.abesto.jstuki.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * (Enum, value)<-->XML serialization
 *
 * @param <E>
 * @author Nagy Zoltán (abesto0@gmail.com)
 */
public class EnumXML<E> {

    protected Properties props;
    protected Class enumClass;

    /**
     * Thrown when a @ref TextSyntax instance has one of its templates missing.
     */
    public static class IncompleteEnumXMLException extends Exception {

        private Object cause;
        private File file;

        /**
         * @param cause The undefined key
         */
        public IncompleteEnumXMLException(Object cause) {
            this.cause = cause;
            file = null;
        }

        /**
         * @param file The file that was being read when the exception was thrown
         */
        public void setFile(File file) {
            this.file = file;
        }

        @Override
        public String getMessage() {
            StringBuilder s = new StringBuilder("Required template ");
            s.append(cause.toString());
            s.append(" not defined");
            if (file != null) {
                s.append(" in file ");
                s.append(file.getAbsoluteFile());
            }
            return s.toString();
        }
    }

    public static class NoNameInEnumException extends Exception {
        @Override
        public String getMessage() {
            return "Enum passed to EnumXML must contain value 'NAME'";
        }
    }

    public static class NameNotSetException extends Exception {
    }

    public static class NotAnEnumException extends Exception {

        public NotAnEnumException(Class clazz) {
            super("Received argument of class '".concat(clazz.toString().concat("' is not an enumeration")));
        }
    }

    /**
     *
     * @param enumClass
     */
    public EnumXML(Class enumClass) throws NotAnEnumException, NoNameInEnumException {
        if (!enumClass.isEnum()) {
            throw new NotAnEnumException(enumClass);
        }

        // NAME must be there in the enum constants
        boolean found = false;
        for (Object value : enumClass.getEnumConstants()) {
            if (value.toString().equals("NAME")) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoNameInEnumException();
        }

        props = new Properties();
        this.enumClass = enumClass;
    }

    /**
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws net.abesto.jstuki.io.EnumXML.IncompleteEnumXMLException
     */
    public void load(File file) throws FileNotFoundException, IOException, IncompleteEnumXMLException {
        props.loadFromXML(new FileInputStream(file));
        try {
            checkComplete();
        } catch (IncompleteEnumXMLException e) {
            e.setFile(file);
            throw e;
        }
    }

    public void save(File file) throws IOException {
        props.storeToXML(new FileOutputStream(file), null);
    }

    /**
     * Check whether all the templates defined in @ref TextSyntax.Template are
     * assigned a template string.
     *
     * @throws net.abesto.jstuki.io.EnumXML.IncompleteEnumXMLException
     */
    protected void checkComplete() throws IncompleteEnumXMLException {
        for (Object t : enumClass.getEnumConstants()) {
            if (getProperty((E) t).equals(t.toString())) {
                throw new IncompleteEnumXMLException(t);
            }
        }
    }

    public String getProperty(E key) {
        return props.getProperty(key.toString(), key.toString());
    }

    protected void setProperty(E key, String value) {
        props.setProperty(key.toString(), value);
    }

    public String getName() { return props.getProperty("NAME"); }
    public void setName(String name) { props.setProperty("NAME", name); }
}
