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
 * * You should have received a copy of the GNU General Public License
 * along with PackJacket.  If not, see <http://www.gnu.org/licenses/>.
 */
package packjacket.gui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import packjacket.RunnerClass;
import packjacket.xml.Pack;

/**
 * OptionalShortcutsPanel
 * @author Manodasan Wignarajah
 */
public class OptionalShortcutsPanel extends javax.swing.JPanel {

    public ArrayListModel<String> packs;
    protected boolean hover;

    /** Creates new form OptionalShortcutsPanel */
    public OptionalShortcutsPanel() {
        initComponents();
//        jScrollPane1.setVisible(false);
        hover = false;
//        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
//
//            public void eventDispatched(AWTEvent event) {
//                if (SwingUtilities.isDescendingFrom((Component) event.getSource(), createPackPanel) || createForPacksPack.isPopupVisible()) {
//                    if (event.getID() == 504 && !hover) {
//                        hover = true;
//                        createForPackText.setVisible(false);
//                        jScrollPane1.setVisible(true);
//                        updateUI();
//                        ((JPanel) createPackPanel.getParent()).updateUI();
//                        RunnerClass.mf.shortcutsPanel.updateUI();
//                        //       updateUI();
//                        //     revalidate();
//                        //     RunnerClass.mf.setPreferredSize(new Dimension(RunnerClass.mf.getPreferredSize().width, RunnerClass.mf.getPreferredSize().height));
//                    }
//                    if (event.getID() == 505 && hover)
//                        if ((event.getSource() instanceof JPanel && ((Container) event.getSource()).getMousePosition(true) == null) || ((Container) event.getSource()).getParent().getMousePosition(true) == null) {
//                            System.out.println("hm");
//                            hover = false;
//                            createForPackText.setText(packs.toString());
//                            createForPackText.setVisible(true);
//                            jScrollPane1.setVisible(false);
//                            updateUI();
//                            ((JPanel) createPackPanel.getParent()).updateUI();
//                            RunnerClass.mf.shortcutsPanel.updateUI();
//                            //       RunnerClass.mf.setPreferredSize(new Dimension(RunnerClass.mf.getPreferredSize().width, RunnerClass.mf.getPreferredSize().height));
//                        }
//                }
//            }
//        }, AWTEvent.MOUSE_EVENT_MASK);

        packs = new ArrayListModel<String>(createForPackList);
        for (Pack p : RunnerClass.mf.packsPanel.packs.list)
            createForPacksPack.addItem(p.toString());
    }

    /**
    Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

    public void eventDispatched(AWTEvent event) {
    if (SwingUtilities.isDescendingFrom((Component) event.getSource(), createPackPanel)) {
    if (event.getID() == 504 && !hover) {
    hover = true;
    createForPackText.setVisible(false);
    jScrollPane1.setVisible(true);
    ((JPanel) createPackPanel.getParent()).updateUI();
    RunnerClass.mf.setMinimumSize(RunnerClass.mf.getPreferredSize());
    RunnerClass.mf.pack();
    createForPacksPack.setPopupVisible(true);
    }
    if (event.getID() == 505 && hover)
    if ((event.getSource() instanceof JPanel && ((Container) event.getSource()).getMousePosition(true) == null) || ((Container) event.getSource()).getParent().getMousePosition(true) == null) {
    hover = false;
    createForPackText.setVisible(true);
    jScrollPane1.setVisible(false);
    ((JPanel) createPackPanel.getParent()).updateUI();
    RunnerClass.mf.setMinimumSize(RunnerClass.mf.getPreferredSize());
    RunnerClass.mf.pack();
    createForPacksPack.setPopupVisible(false);
    }
    }
    }
    }, AWTEvent.MOUSE_EVENT_MASK);
     */
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        description = new javax.swing.JTextField();
        commandLineOption = new javax.swing.JCheckBox();
        commandLine = new javax.swing.JTextField();
        workingDirectoryOption = new javax.swing.JCheckBox();
        workingDirectory = new javax.swing.JTextField();
        iconOption = new javax.swing.JCheckBox();
        iconFile = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        iconIndex = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0,0,2147483647,1));
        jLabel3 = new javax.swing.JLabel();
        initialState = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        terminal = new javax.swing.JCheckBox();
        sudo = new javax.swing.JCheckBox();
        allUsers = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        createPackPanel = new javax.swing.JPanel();
        createForPacksPack = new javax.swing.JComboBox();
        addBtn = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();
        createForPackText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        createForPackList = new javax.swing.JList();

        jLabel1.setText("Description");

        commandLineOption.setText("Command Line Arguments");
        commandLineOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineOptionActionPerformed(evt);
            }
        });

        commandLine.setEnabled(false);

        workingDirectoryOption.setText("Working Directory");
        workingDirectoryOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workingDirectoryOptionActionPerformed(evt);
            }
        });

        workingDirectory.setEnabled(false);

        iconOption.setText("Icon File");
        iconOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconOptionActionPerformed(evt);
            }
        });

        iconFile.setEditable(true);
        iconFile.setModel(GUIUtils.filterModel());
        AutoCompleteDecorator.decorate(iconFile);
        iconFile.setEnabled(false);

        jLabel2.setText("Icon Index");

        iconIndex.setEnabled(false);

        jLabel3.setText("Initial State");

        initialState.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Show", "Normal", "Maximized", "Minimized" }));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Unix Specific"));

        terminal.setText("Terminal");

        sudo.setText("Requires Sudo");

        allUsers.setText("Create shortcut for all Users");

        jLabel4.setText("User with right permissions");

        userName.setText("root");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(terminal)
                        .addGap(18, 18, 18)
                        .addComponent(sudo))
                    .addComponent(allUsers)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(userName, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(terminal)
                    .addComponent(sudo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allUsers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        createPackPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Create for Pack"));

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/add.png"))); // NOI18N
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        delBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/remove.png"))); // NOI18N
        delBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delBtnActionPerformed(evt);
            }
        });

        createForPackText.setEditable(false);

        jScrollPane1.setViewportView(createForPackList);
        this.updateUI();
        this.validate();

        javax.swing.GroupLayout createPackPanelLayout = new javax.swing.GroupLayout(createPackPanel);
        createPackPanel.setLayout(createPackPanelLayout);
        createPackPanelLayout.setHorizontalGroup(
            createPackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createPackPanelLayout.createSequentialGroup()
                .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
            .addComponent(createForPacksPack, 0, 331, Short.MAX_VALUE)
            .addComponent(createForPackText, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );
        createPackPanelLayout.setVerticalGroup(
            createPackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createPackPanelLayout.createSequentialGroup()
                .addComponent(createForPacksPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createPackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addBtn)
                    .addComponent(delBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createForPackText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(commandLineOption)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(commandLine, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(98, 98, 98)
                                .addComponent(description, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(workingDirectoryOption)
                                    .addComponent(iconOption))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(iconFile, 0, 147, Short.MAX_VALUE)
                                        .addGap(45, 45, 45))
                                    .addComponent(workingDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                    .addComponent(iconIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(initialState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(createPackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commandLineOption)
                    .addComponent(commandLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(workingDirectoryOption)
                    .addComponent(workingDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iconOption)
                    .addComponent(iconFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(iconIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(initialState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createPackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Adjusts the fields on the GUI depending on what is selected
     */
    public void adjustFields() {
        commandLineOptionActionPerformed(null);
        workingDirectoryOptionActionPerformed(null);
        iconOptionActionPerformed(null);
    }

    private void commandLineOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineOptionActionPerformed
        //Enables the command line text field if chosen
        commandLine.setEnabled(commandLineOption.isSelected());
    }//GEN-LAST:event_commandLineOptionActionPerformed

    private void workingDirectoryOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workingDirectoryOptionActionPerformed
        //Enables the working directory text field if chosen
        workingDirectory.setEnabled(workingDirectoryOption.isSelected());
    }//GEN-LAST:event_workingDirectoryOptionActionPerformed

    private void iconOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iconOptionActionPerformed
        //Enables the icon file combo box and icon index JSpinner if chosen
        iconFile.setEnabled(iconOption.isSelected());
        iconIndex.setEnabled(iconOption.isSelected());
    }//GEN-LAST:event_iconOptionActionPerformed

    private void delBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delBtnActionPerformed
        packs.remove();
    }//GEN-LAST:event_delBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        if (packs.list.contains(createForPacksPack.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(RunnerClass.mf, "You have already added that pack!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        packs.add(createForPacksPack.getSelectedItem().toString());
    }//GEN-LAST:event_addBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    public javax.swing.JCheckBox allUsers;
    public javax.swing.JTextField commandLine;
    public javax.swing.JCheckBox commandLineOption;
    private javax.swing.JList createForPackList;
    public javax.swing.JTextField createForPackText;
    private javax.swing.JComboBox createForPacksPack;
    private javax.swing.JPanel createPackPanel;
    private javax.swing.JButton delBtn;
    public javax.swing.JTextField description;
    public javax.swing.JComboBox iconFile;
    public javax.swing.JSpinner iconIndex;
    public javax.swing.JCheckBox iconOption;
    public javax.swing.JComboBox initialState;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JCheckBox sudo;
    public javax.swing.JCheckBox terminal;
    public javax.swing.JTextField userName;
    public javax.swing.JTextField workingDirectory;
    public javax.swing.JCheckBox workingDirectoryOption;
    // End of variables declaration//GEN-END:variables
}
