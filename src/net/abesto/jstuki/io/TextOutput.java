package net.abesto.jstuki.io;

import java.text.MessageFormat;
import java.util.Iterator;

import net.abesto.jstuki.elements.*;
import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;

/**
 * Generate text of a given algorithm with a given syntax
 *
 * @author Nagy Zoltán (abesto0@gmail.com)
 */
public class TextOutput extends ElementHandler {

    public enum Template {

        INDENT, PROCEDURE, ENDPROCEDURE, LOOP, ENDLOOP, IF, ELSEIF, ELSE, ENDIF, NULL
    };
    private EnumXML syntax;
    private StringBuilder builder;
    private int depth;

    public TextOutput(EnumXML s) {
        super();

        syntax = s;
        depth = 0;
        builder = new StringBuilder();

        setHandlers(
            new ProcedureHandler(), new StatementHandler(),
            new ConditionalHandler(), new BinaryConditionalHandler(),
            new IterationHandler());
    }

    private void indent() {
        for (int i = 0; i < depth; i++) {
            builder.append(syntax.getTemplate(Template.INDENT));
        }
    }

    private void format(Template t, String s) {
        String template = syntax.getTemplate(t);
        Object[] args = {s};
        builder.append(MessageFormat.format(template, args));
    }

    public String renderProcedure(Procedure p) throws HandlerNotSetException {
        handle(p);
        return builder.toString();
    }

    private class BinaryConditionalHandler implements IHandler<BinaryConditional> {

        public void call(BinaryConditional conditional) throws HandlerNotSetException {
            indent();
            format(Template.IF, conditional.getLabel());
            depth++;
            renderContainerStatement(conditional.getTrueCase(), Template.NULL, Template.NULL);
            depth--;

            renderContainerStatement(conditional.getFalseCase(), Template.ELSE, Template.NULL);

            indent();
            format(Template.ENDIF, conditional.getLabel());
        }
    }

    private class IterationHandler implements IHandler<Iteration> {

        public void call(Iteration iteration) throws HandlerNotSetException {
            renderContainerStatement((ContainerStatement) iteration, Template.LOOP, Template.ENDLOOP);

        }
    }

    private class ConditionalHandler implements IHandler<Conditional> {

        public void call(Conditional conditional) throws HandlerNotSetException {
            Iterator<ContainerStatement> cases = conditional.getCases().iterator();
            renderContainerStatement(cases.next(), Template.IF, Template.NULL);
            while (cases.hasNext()) {
                renderContainerStatement(cases.next(), Template.ELSEIF, Template.NULL);
            }
            indent();
            format(Template.ENDIF, "");


        }
    }

    private class StatementHandler implements IHandler<Statement> {

        public void call(Statement statement) {
            indent();
            builder.append(statement.getLabel());
            builder.append("\n");

        }
    }

    private class ProcedureHandler implements IHandler<Procedure> {

        public void call(Procedure procedure) throws HandlerNotSetException {
            builder.delete(0, builder.length());
            format(Template.PROCEDURE, procedure.getLabel());
            depth++;
            for (Statement s : procedure.getChildren()) {
                handle(s);
            }
            depth--;
            format(Template.ENDPROCEDURE, procedure.getLabel());

        }
    }

    private void renderContainerStatement(ContainerStatement container, Template open, Template close)
        throws HandlerNotSetException {
        if (open != Template.NULL) {
            indent();
        }

        format(open, container.getLabel());
        if (open != Template.NULL) {
            depth++;
        }

        for (Statement child : container.getChildren()) {
            handle(child);
        }

        if (open != Template.NULL) {
            depth--;
        }

        if (close != Template.NULL) {
            indent();
        }
        format(close, container.getLabel());
    }
}
