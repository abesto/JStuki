package net.abesto.jstuki.elements;

public class Exceptions {
    static public class HandlerNotSetException extends Exception {
        public HandlerNotSetException(String classname) {
            super("Handler not set for class '".concat(classname));
        }
    }

    static public class ChildNotFoundException extends Exception {}
}
