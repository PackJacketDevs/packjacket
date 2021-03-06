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

import packjacket.gui.GUIUtils;

/**
 * The panel for File
 * @author Amandeep Grewal
 */
public class FilePanel extends javax.swing.JPanel {

    public FileOptionalPanel fop;

    /** Creates new form File */
    public FilePanel(FileOptionalPanel fop) {
        this.fop = fop;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sourceFile = new javax.swing.JTextField();
        sourceFileOption = new javax.swing.JCheckBox();
        browseFile = new javax.swing.JButton();
        sourceDir = new javax.swing.JTextField();
        sourceDirOption = new javax.swing.JCheckBox();
        browseDir = new javax.swing.JButton();

        sourceFile.setEditable(false);
        sourceFile.setToolTipText("The source file location");

        sourceFileOption.setSelected(true);
        sourceFileOption.setText("Source File");
        sourceFileOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sourceFileOptionActionPerformed(evt);
            }
        });

        browseFile.setText("Browse");
        browseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileActionPerformed(evt);
            }
        });

        sourceDir.setEditable(false);
        sourceDir.setToolTipText("The source directory location");
        sourceDir.setEnabled(false);

        sourceDirOption.setText("Source Directory");
        sourceDirOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sourceDirOptionActionPerformed(evt);
            }
        });

        browseDir.setText("Browse");
        browseDir.setEnabled(false);
        browseDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseDirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sourceFileOption)
                        .addGap(59, 59, 59)
                        .addComponent(sourceFile, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseFile))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sourceDirOption)
                        .addGap(22, 22, 22)
                        .addComponent(sourceDir, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseDir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseFile)
                    .addComponent(sourceFileOption)
                    .addComponent(sourceFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourceDirOption)
                    .addComponent(browseDir)
                    .addComponent(sourceDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sourceFileOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceFileOptionActionPerformed
        // En/Disables the appropriate fields
        if (sourceFileOption.isSelected()) {
            sourceDirOption.setSelected(false);
            sourceDir.setEnabled(false);
            browseDir.setEnabled(false);
            sourceFile.setEnabled(true);
            browseFile.setEnabled(true);
            fop.renameTargetChoice.setEnabled(true);
            if (fop.renameTargetChoice.isSelected())
                fop.renameTarget.setEnabled(true);
            else
                fop.renameTarget.setEnabled(false);
        }
        if (!sourceFileOption.isSelected() && !sourceDirOption.isSelected())
            sourceFileOption.setSelected(true);
}//GEN-LAST:event_sourceFileOptionActionPerformed

    private void sourceDirOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceDirOptionActionPerformed
        // En/Disables the appropriate fields
        if (sourceDirOption.isSelected()) {
            sourceFileOption.setSelected(false);
            sourceFile.setEnabled(false);
            browseFile.setEnabled(false);
            sourceDir.setEnabled(true);
            browseDir.setEnabled(true);
            fop.renameTargetChoice.setEnabled(false);
            fop.renameTarget.setEnabled(false);
        }
        if (!sourceFileOption.isSelected() && !sourceDirOption.isSelected())
            sourceDirOption.setSelected(true);
}//GEN-LAST:event_sourceDirOptionActionPerformed

    /**
     * Adjusts the fields by calling the sourceDirOptionActionPerformed method
     */
    public void perfomInitialAction() {
        sourceDirOptionActionPerformed(null);
    }
//The following methods opens a JFileChooser and assigns the value selected in it to specified field
    private void browseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFileActionPerformed
        GUIUtils.open("all", sourceFile);
    }//GEN-LAST:event_browseFileActionPerformed

    private void browseDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseDirActionPerformed
        GUIUtils.openDir(sourceDir);
    }//GEN-LAST:event_browseDirActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton browseDir;
    public javax.swing.JButton browseFile;
    public javax.swing.JTextField sourceDir;
    public javax.swing.JCheckBox sourceDirOption;
    public javax.swing.JTextField sourceFile;
    public javax.swing.JCheckBox sourceFileOption;
    // End of variables declaration//GEN-END:variables
}
