package net.abesto.jstuki.ui;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import net.abesto.jstuki.actions.ActionProxy;
import net.abesto.jstuki.actions.ActionProxy.ActionDisabledException;
import net.abesto.jstuki.actions.ActionType;
import net.abesto.jstuki.actions.ElementActionListener;
import net.abesto.jstuki.elements.Exceptions.HandlerNotSetException;
import net.abesto.jstuki.elements.Statement;

public class ActionsPanel extends javax.swing.JPanel {

    private ActionProxy proxy;
    private static EnumMap<ActionType, LinkedList<javax.swing.JComponent>> controls;

    /** Creates new form ActionsPanel */
    public ActionsPanel() {
        proxy = new ActionProxy();
        initComponents();

        controls = new EnumMap<ActionType, LinkedList<JComponent>>(ActionType.class);
        setControls(ActionType.SET_LABEL, this.btnSetLabel, this.txtLabel);
        setControls(ActionType.MOVE_UP, this.btnMoveUp);
        setControls(ActionType.MOVE_DOWN, this.btnMoveDown);
        setControls(ActionType.TO_ITERATION, this.btnToIteration);
    }

    private void setControls(ActionType action, JComponent... components) {
        LinkedList<JComponent> list = new LinkedList<JComponent>();
        for (JComponent component : components) {
            list.push(component);
        }
        controls.put(action, list);
    }

    public boolean removeActionListener(ElementActionListener e) {
        return proxy.removeActionListener(e);
    }

    public boolean addActionListener(ElementActionListener e) {
        return proxy.addActionListener(e);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtLabel = new javax.swing.JTextField();
        btnSetLabel = new javax.swing.JButton();
        btnMoveUp = new javax.swing.JButton();
        btnMoveDown = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDebug = new javax.swing.JTextArea();
        btnToIteration = new javax.swing.JButton();

        btnSetLabel.setText("Set Label");
        btnSetLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetLabelActionPerformed(evt);
            }
        });

        btnMoveUp.setText("Move up");
        btnMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveUpActionPerformed(evt);
            }
        });

        btnMoveDown.setText("Move down");
        btnMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveDownActionPerformed(evt);
            }
        });

        txtDebug.setColumns(20);
        txtDebug.setRows(5);
        jScrollPane1.setViewportView(txtDebug);

        btnToIteration.setText("Iteration");
        btnToIteration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToIterationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSetLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMoveUp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoveDown))
                    .addComponent(btnToIteration))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSetLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoveUp)
                    .addComponent(btnMoveDown))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnToIteration)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSetLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetLabelActionPerformed
        try {
            proxy.call(ActionType.SET_LABEL, txtLabel.getText());
            debug("Set label action");
        } catch (ActionDisabledException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_btnSetLabelActionPerformed

    private void btnMoveUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveUpActionPerformed
        try {
            proxy.call(ActionType.MOVE_UP);
            debug("Move up action");
        } catch (ActionDisabledException ex) {
            Logger.getLogger(ActionsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMoveUpActionPerformed

    private void btnMoveDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveDownActionPerformed
        try {
            proxy.call(ActionType.MOVE_DOWN);
            debug("Move down action");
        } catch (ActionDisabledException ex) {
            Logger.getLogger(ActionsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMoveDownActionPerformed

    private void btnToIterationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToIterationActionPerformed
        try {
            proxy.call(ActionType.TO_ITERATION);
            debug("To iteration action");
        } catch (ActionDisabledException ex) {
            Logger.getLogger(ActionsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnToIterationActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoveDown;
    private javax.swing.JButton btnMoveUp;
    private javax.swing.JButton btnSetLabel;
    private javax.swing.JButton btnToIteration;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDebug;
    private javax.swing.JTextField txtLabel;
    // End of variables declaration//GEN-END:variables

    private void debug(String msg) {
        txtDebug.append(msg);
    }

    public void update(Statement selection) {
        if (selection == null) {
            return;
        }
        try {
            proxy.select(selection);
            debug(selection.getClass().getSimpleName().concat(" selected. Enabled actions: "));
            for (ActionType action : ActionType.values()) {
                boolean enabled = proxy.isEnabled(action);
                if (enabled) {
                    debug(action.toString().concat(", "));
                }
                if (controls.containsKey(action)) {
                    for (JComponent component : controls.get(action)) {
                        component.setEnabled(enabled);
                    }
                }
            }

            debug("\n");
            txtLabel.setText(selection.getLabel());
        } catch (HandlerNotSetException ex) {
            Logger.getLogger(ActionsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
