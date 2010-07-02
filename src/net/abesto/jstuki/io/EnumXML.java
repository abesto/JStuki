package net.abesto.jstuki.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * (Enum, value)<-->XML serialization
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 */
public class EnumXML<E> {

    protected Properties props;
    protected Class enumClass;

    /**
     * Thrown when a @ref TextSyntax instance has one of its templates missing.
     */
    public static class IncompleteEnumXMLException extends IOException {

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

    public EnumXML(Class enumClass) {
        props = new Properties();
        this.enumClass = enumClass;
    }

    public void load(File file)
        throws FileNotFoundException, IOException, IncompleteEnumXMLException {
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
     * @throws net.abesto.jstuki.io.JStukiIOException.IncompleteTextSyntaxException if not
     */
    protected void checkComplete() throws IncompleteEnumXMLException {
        for (Object t : enumClass.getEnumConstants()) {
            if (getTemplate((E) t) == t.toString()) {
                throw new IncompleteEnumXMLException(t);
            }
        }
    }

    public String getTemplate(E key) {
        return props.getProperty(key.toString(), key.toString());
    }

    protected void setTemplate(E key, String value) {
        props.setProperty(key.toString(), value);
    }
}
