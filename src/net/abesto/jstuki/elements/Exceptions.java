package net.abesto.jstuki.elements;

public class Exceptions {
    static public class ProcessorNotSetException extends Exception {
        public ProcessorNotSetException(String classname) {
            super("Processor not set for class '".concat(classname));
        }
    }
}
