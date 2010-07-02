package net.abesto.jstuki.elements;

import java.util.HashMap;
import net.abesto.jstuki.elements.Exceptions.ProcessorNotSetException;

/**
 * Maintain and use a Class-Handler mapping. The parameter type of @ref process
 * restricts the usable classes to the required class hierarchy. Only exact
 * class matches are used (as opposed to isInstanceOf, for instance (of a bad pun)).
 *
 * @author Nagy Zoltán (abesto0gmail.com)
 */
abstract public class ElementHandler {

    /**
     * Like Callable, only with one parameter
     *
     * @param <E> The parameter type of @c call
     */
    public interface Handler<E> {
        public void call(E elem) throws ProcessorNotSetException;
    }
    private HashMap<Class, Handler> processors;

    public ElementHandler() {
        processors = new HashMap<Class, Handler>();
    }

    /**
     * Add h as a handler and associate it with its parameter type
     *
     * @param h
     */
    protected void addHandler(Handler h) {
        Class clazz = h.getClass().getMethods()[0].getParameterTypes()[0];
        processors.put(clazz, h);
    }

    protected void handle(Statement s) throws ProcessorNotSetException {
        Class c = s.getClass();
        Handler handler = processors.get(c);
        if (handler == null) {
            throw new ProcessorNotSetException(c.getName());
        }
        handler.call(s);
    }
}
