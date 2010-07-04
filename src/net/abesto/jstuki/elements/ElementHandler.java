package net.abesto.jstuki.elements;

import java.util.HashMap;
import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;

/**
 * Maintain and use a Class-IHandler mapping. The parameter type of @ref process
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
    public interface IHandler<E> {

        /**
         * @param elem
         * @throws net.abesto.jstuki.elements.Exceptions.HandlerNotSetException
         * Throw specified here so that handlers can call each other via ElementHandler.handle
         * and re-throw the exception
         */
        public void call(E elem) throws HandlerNotSetException;
    }
    private HashMap<Class, IHandler> processors;

    public ElementHandler() {
        processors = new HashMap<Class, IHandler>();
    }

    /**
     * Store handlers and associate them with their argument type
     *
     * @param handlers
     */
    protected void setHandlers(IHandler... handlers) {
        for (IHandler handler : handlers) {
            Class clazz = handler.getClass().getMethods()[0].getParameterTypes()[0];
            processors.put(clazz, handler);
        }
    }

    protected void handle(Statement s) throws HandlerNotSetException {
        Class c = s.getClass();
        IHandler handler = processors.get(c);
        if (handler == null) {
            throw new HandlerNotSetException(c.getName());
        }
        handler.call(s);
    }
}
