package net.abesto.jstuki.io;

import java.text.MessageFormat;
import java.util.ArrayList;
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
        NAME,
        NULL, INDENT,
        PROCEDURE, ENDPROCEDURE,
        STATEMENT,
        LOOP, ENDLOOP,
        IF, ELSEIF, ELSE, ENDIF
    };
    private EnumXML<Template> syntax;
    private StringBuilder builder;
    private ArrayList<Line> lines;
    private int depth;

    public class Line {
        private String string;
        private Statement statement;

        public Line(String string, Statement statement) {
            this.string = string;
            this.statement = statement;
        }

        public Statement getStatement() {
            return statement;
        }

        public String getString() {
            return string;
        }
    }

    public TextOutput(EnumXML<Template> s) {
        super();

        syntax = s;
        depth = 0;
        builder = new StringBuilder();
        lines = new ArrayList<Line>();

        setHandlers(
            new ProcedureHandler(), new StatementHandler(),
            new ConditionalHandler(), new BinaryConditionalHandler(),
            new IterationHandler());
    }

    private void indent() {
        for (int i = 0; i < depth; i++) {
            builder.append(syntax.getProperty(Template.INDENT));
        }
    }

    private void format(Template t, String string, Statement statement) {
        String template = syntax.getProperty(t);
        Object[] args = {string};
        builder.append(MessageFormat.format(template, args));
        lines.add(new Line(builder.toString(), statement));
        builder.delete(0, builder.length());
    }

    public ArrayList<Line> renderProcedure(Procedure p) throws HandlerNotSetException {
        lines.clear();
        handle(p);
        return lines;
    }

    private class BinaryConditionalHandler implements IHandler<BinaryConditional> {

        public void call(BinaryConditional conditional) throws HandlerNotSetException {
            indent();
            format(Template.IF, conditional.getLabel(), conditional.getTrueCase());
            depth++;
            renderContainerStatement(conditional.getTrueCase(), Template.NULL, Template.NULL);
            depth--;

            renderContainerStatement(conditional.getFalseCase(), Template.ELSE, Template.NULL);

            indent();
            format(Template.ENDIF, conditional.getLabel(), conditional);
        }
    }

    private class IterationHandler implements IHandler<Iteration> {

        public void call(Iteration iteration) throws HandlerNotSetException {
            renderContainerStatement((ContainerStatement) iteration, Template.LOOP, Template.ENDLOOP);

        }
    }

    private class ConditionalHandler implements IHandler<Conditional> {

        public void call(Conditional conditional) throws HandlerNotSetException {
            Iterator<ConditionalCase> cases = conditional.getCases().iterator();
            renderContainerStatement(cases.next(), Template.IF, Template.NULL);
            while (cases.hasNext()) {
                renderContainerStatement(cases.next(), Template.ELSEIF, Template.NULL);
            }
            indent();
            format(Template.ENDIF, conditional.getLabel(), conditional);
        }
    }

    private class StatementHandler implements IHandler<Statement> {

        public void call(Statement statement) {
            indent();
            format(Template.STATEMENT, statement.getLabel(), statement);
        }
    }

    private class ProcedureHandler implements IHandler<Procedure> {

        public void call(Procedure procedure) throws HandlerNotSetException {
            format(Template.PROCEDURE, procedure.getLabel(), procedure);
            depth++;
            for (Statement s : procedure.getChildren()) {
                handle(s);
            }
            depth--;
            format(Template.ENDPROCEDURE, procedure.getLabel(), procedure);

        }
    }

    private void renderContainerStatement(ContainerStatement container, Template open, Template close)
        throws HandlerNotSetException {
        if (open != Template.NULL) {
            indent();
            format(open, container.getLabel(), container);
        }

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
            format(close, container.getLabel(), container);
        }
    }
}
