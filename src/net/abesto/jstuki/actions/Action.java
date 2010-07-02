package net.abesto.jstuki.actions;

public class Action<E> {
    private ActionType type;
    private boolean enabled;
    private String label;
    private ActionCallable<E> action;

    public interface ActionCallable<E> {
        public void call(E param);
    }

    public Action(ActionType type, String label, ActionCallable<E> action) {
        this.type = type;
        this.label = label;
        this.action = action;
        this.enabled = false;
    }

    public ActionCallable<E> getAction() {
        return action;
    }

    public String getLabel() {
        return (label == null ? type.toString() : label);
    }

    public ActionType getType() {
        return type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
