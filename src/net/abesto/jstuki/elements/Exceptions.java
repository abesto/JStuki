package net.abesto.jstuki.elements;

public class Exceptions {
    static public class HandlerNotSetException extends Exception {
        public HandlerNotSetException(String classname) {
            super("Processor not set for class '".concat(classname));
        }
    }
}
