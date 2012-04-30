/*
 * PackJacket - GUI frontend to IzPack to make Java-based installers
 * Copyright (C) 2008 - 2009  Amandeep Grewal, Manodasan Wignarajah
 *
 * PackJacket is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PackJacket is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PackJacket.  If not, see <http://www.gnu.org/licenses/>.
 */
package packjacket.gui.packs;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import packjacket.RunnerClass;
import packjacket.gui.GUIUtils;

/**
 * The panel for executable
 * @author Amandeep Grewal
 */
public class ExecutablePanel extends javax.swing.JPanel {

    /** Creates new form Executable */
    public ExecutablePanel() {
        initComponents();
        targetFileExec.setModel(GUIUtils.getFilesforComboBoxModel());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel41 = new javax.swing.JLabel();
        failureCombo = new javax.swing.JComboBox();
        clas = new javax.swing.JTextField();
        clasLabel = new javax.swing.JLabel();
        stageCombo = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        keepOption = new javax.swing.JCheckBox();
        targetFileExec = new javax.swing.JComboBox();

        jLabel41.setText("File");

        failureCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Abort Installation", "Ask User", "Warn User and Continue" }));

        clasLabel.setText("Main Class in Jar");

        stageCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Never", "Post-Install", "Uninstall" }));

        jLabel44.setText("Failure Action");

        jLabel43.setText("Stage to Launch");

        keepOption.setText("Keep file after execution");

        targetFileExec.setEditable(true);
        AutoCompleteDecorator.decorate(targetFileExec);
        targetFileExec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                targetFileExecItemStateChanged(evt);
            }
        });
        targetFileExec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                targetFileExecFocusLost(evt);
            }
        });
        targetFileExec.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                targetFileExecInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                targetFileExecCaretPositionChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel41))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(keepOption, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clasLabel)
                            .addComponent(jLabel43)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clas, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(stageCombo, 0, 190, Short.MAX_VALUE)
                            .addComponent(failureCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(targetFileExec, 0, 190, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(targetFileExec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clasLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(stageCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(failureCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keepOption)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        clas.setVisible(false);
        clasLabel.setVisible(false);
    }// </editor-fold>//GEN-END:initComponents
//The following methods calls the classVisible method to adjusts it field, since an action has been performed on targetFileExec field
    private void targetFileExecItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_targetFileExecItemStateChanged
        classVisible();
    }//GEN-LAST:event_targetFileExecItemStateChanged

    private void targetFileExecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_targetFileExecFocusLost
        classVisible();
    }//GEN-LAST:event_targetFileExecFocusLost

    private void targetFileExecCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_targetFileExecCaretPositionChanged
        classVisible();
    }//GEN-LAST:event_targetFileExecCaretPositionChanged

    private void targetFileExecInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_targetFileExecInputMethodTextChanged
        classVisible();
    }//GEN-LAST:event_targetFileExecInputMethodTextChanged

    /**
     * Adjusts the class field depending on the value of targetFileExec
     */
    public void classVisible() {
        if (targetFileExec.getSelectedItem().toString().trim().toLowerCase().endsWith(".jar")) {
            if (RunnerClass.mf.getContentPane().getSize().height < this.getPreferredSize().height)
                RunnerClass.mf.pack();
            clasLabel.setVisible(true);
            clas.setVisible(true);
        } else {
            clasLabel.setVisible(false);
            clas.setVisible(false);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField clas;
    public javax.swing.JLabel clasLabel;
    public javax.swing.JComboBox failureCombo;
    public javax.swing.JLabel jLabel41;
    public javax.swing.JLabel jLabel43;
    public javax.swing.JLabel jLabel44;
    public javax.swing.JCheckBox keepOption;
    public javax.swing.JComboBox stageCombo;
    public javax.swing.JComboBox targetFileExec;
    // End of variables declaration//GEN-END:variables
}
