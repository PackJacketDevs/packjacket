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
package packjacket.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import packjacket.StaticUtils;

/**
 * Class to allowing slecting of packs, and different types of packs, and their order
 * @author Amandeep Grewal
 * @author Manodasan Wignarajah
 */
public class PanelsPanel extends javax.swing.JPanel {

    public DefaultListModel panelsModel;

    public PanelsPanel() {
        initComponents();
        //Add all default packs
        panelsModel.addElement("CheckedHelloPanel");
        panelsModel.addElement("InfoPanelfalse");
        panelsModel.addElement("LicencePanelfalse");
        panelsModel.addElement("PacksPanel");
        panelsModel.addElement("TargetPanel");
        panelsModel.addElement("InstallPanel");
        panelsModel.addElement("UserInputPanelfalse");
        panelsModel.addElement("ProcessPanelfalse");
        panelsModel.addElement("SimpleFinishPanel");
        //Adds a custom cell renderer for disabling and enabling panels grahpically
        panelsList.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (StaticUtils.countOccurrencesOf("false", value.toString()) + StaticUtils.countOccurrencesOf("true", value.toString()) > 1)
                    if (panelsModel.get(index).toString().charAt(panelsModel.get(index).toString().length() - 2) == 's') {
                        panelsModel.set(index, panelsModel.get(index).toString().replaceAll("true", "").replaceAll("false", "") + "false");
                        //System.out.println("changed from big to f");
                        return comp;
                    } else {
                        panelsModel.set(index, panelsModel.get(index).toString().replaceAll("true", "").replaceAll("false", "") + "true");
                        //System.out.println("changed from big to t");
                        return comp;
                    }
                if (value.toString().contains("false")) {
                    comp.setForeground(Color.GRAY);
                    setText(value.toString().replaceAll("false", ""));
                } else if (value.toString().contains("true")) {
//                    if (isSelected)
//                        comp.setForeground(Color.WHITE);
//                    else
                    comp.setForeground(Color.BLACK);
                    setText(value.toString().replaceAll("true", ""));
                }

                return comp;
            }
        });


        //Add choices for each type
        helloPanelChoice.addItem("CheckedHelloPanel");
        helloPanelChoice.addItem("HelloPanel");
        helloPanelChoice.addItem("HTMLHelloPanel");
        packsPanelChoice.addItem("PacksPanel");
        packsPanelChoice.addItem("ImgPacksPanel");
        packsPanelChoice.addItem("TreePacksPanel");
        targetPanelChoice.addItem("TargetPanel");
        targetPanelChoice.addItem("DefaultTargetPanel");
        finishPanelChoice.addItem("SimpleFinishPanel");
        finishPanelChoice.addItem("FinishPanel");
        removeAllPans();

        licenceEnabled.setSelected(false);
        infoEnabled.setSelected(false);
    }

    /**
     * Gets the index in which the elemnt which contains partial is in
     * @param partial the part of element to search
     * @return the index of the element which contains partial
     */
    public int getIndex(String partial) {
        //Traverses through the panelsModel
        for (int x = 0; x < panelsModel.size(); x++)
            if (panelsModel.get(x).toString().contains(partial))
                return x;
        return -1;
    }

    /**
     * Gets the item in which partial is part of
     * @param partial the part of the item returned
     * @return the item which contains part of partial
     */
    public String getItem(String partial) {
        return panelsModel.get(getIndex(partial)).toString();
    }

    //Adjusts the gui depending on what's chosen
    public void adjustGUI() {
        helloEnabled.setSelected(getItem("Hello").contains("true"));
        helloPanelChoice.setSelectedItem(getItem("Hello").replace("true", "").replace("false", ""));
        htmlBrowse.setVisible(getItem("Hello").replace("true", "").replace("false", "").equals("HTMLHelloPanel"));
        jLabel4.setVisible(getItem("Hello").replace("true", "").replace("false", "").equals("HTMLHelloPanel"));
        htmlField.setVisible(getItem("Hello").replace("true", "").replace("false", "").equals("HTMLHelloPanel"));
        infoEnabled.setSelected(getItem("Info").contains("true"));
        licenceEnabled.setSelected(getItem("Licence").contains("true"));
        packsEnabled.setSelected(getItem("Packs").contains("true"));
        packsPanelChoice.setSelectedItem(getItem("Packs").replace("true", "").replace("false", ""));
        targetEnabled.setSelected(getItem("Target").contains("true"));
        targetPanelChoice.setSelectedItem(getItem("Target").replace("true", "").replace("false", ""));
        finishPanelChoice.setSelectedItem(getItem("Finish").replace("true", "").replace("false", ""));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelsModel=new DefaultListModel();
        panelsList = new DList(panelsModel);
        helloPan = new javax.swing.JPanel();
        helloPanelChoice = new javax.swing.JComboBox();
        helloEnabled = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        htmlField = new javax.swing.JTextField();
        htmlBrowse = new javax.swing.JButton();
        infoPan = new javax.swing.JPanel();
        infoEnabled = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        infoFileField = new javax.swing.JTextField();
        infoBrowse = new javax.swing.JButton();
        licencePan = new javax.swing.JPanel();
        licenceEnabled = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        licenceFileField = new javax.swing.JTextField();
        licenceBrowse = new javax.swing.JButton();
        packsPan = new javax.swing.JPanel();
        packsPanelChoice = new javax.swing.JComboBox();
        packsEnabled = new javax.swing.JCheckBox();
        targetPan = new javax.swing.JPanel();
        targetPanelChoice = new javax.swing.JComboBox();
        targetEnabled = new javax.swing.JCheckBox();
        finishPan = new javax.swing.JPanel();
        finishPanelChoice = new javax.swing.JComboBox();
        finishEnabled = new javax.swing.JCheckBox();

        panelsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                panelsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(panelsList);

        helloPanelChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helloPanelChoiceActionPerformed(evt);
            }
        });

        helloEnabled.setSelected(true);
        helloEnabled.setText("Enabled");
        helloEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helloEnabledActionPerformed(evt);
            }
        });

        jLabel4.setText("HTML File");

        htmlField.setBackground(new java.awt.Color(255, 254, 254));
        htmlField.setEditable(false);

        htmlBrowse.setText("Browse");
        htmlBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                htmlBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout helloPanLayout = new javax.swing.GroupLayout(helloPan);
        helloPan.setLayout(helloPanLayout);
        helloPanLayout.setHorizontalGroup(
            helloPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helloPanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(helloPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(helloEnabled)
                    .addComponent(helloPanelChoice, 0, 370, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helloPanLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(htmlField, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(htmlBrowse)))
                .addContainerGap())
        );
        helloPanLayout.setVerticalGroup(
            helloPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helloPanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(helloPanelChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(helloEnabled)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(helloPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(htmlField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(htmlBrowse)
                    .addComponent(jLabel4)))
        );

        infoEnabled.setSelected(true);
        infoEnabled.setText("Enabled");
        infoEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoEnabledActionPerformed(evt);
            }
        });

        jLabel1.setText("Info File");

        infoFileField.setBackground(new java.awt.Color(254, 254, 254));
        infoFileField.setEditable(false);

        infoBrowse.setText("Browse");
        infoBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout infoPanLayout = new javax.swing.GroupLayout(infoPan);
        infoPan.setLayout(infoPanLayout);
        infoPanLayout.setHorizontalGroup(
            infoPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(infoFileField, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(infoBrowse))
                    .addComponent(infoEnabled))
                .addContainerGap())
        );
        infoPanLayout.setVerticalGroup(
            infoPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(infoPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(infoFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoEnabled)
                .addGap(11, 11, 11))
        );

        licenceEnabled.setSelected(true);
        licenceEnabled.setText("Enabled");
        licenceEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licenceEnabledActionPerformed(evt);
            }
        });

        jLabel2.setText("Licence File");

        licenceFileField.setBackground(new java.awt.Color(255, 254, 254));
        licenceFileField.setEditable(false);

        licenceBrowse.setText("Browse");
        licenceBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licenceBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout licencePanLayout = new javax.swing.GroupLayout(licencePan);
        licencePan.setLayout(licencePanLayout);
        licencePanLayout.setHorizontalGroup(
            licencePanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(licencePanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(licencePanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(licencePanLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(licenceFileField, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(licenceBrowse))
                    .addComponent(licenceEnabled))
                .addContainerGap())
        );
        licencePanLayout.setVerticalGroup(
            licencePanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(licencePanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(licencePanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(licenceFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(licenceBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(licenceEnabled)
                .addGap(47, 47, 47))
        );

        packsPanelChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packsPanelChoiceActionPerformed(evt);
            }
        });

        packsEnabled.setSelected(true);
        packsEnabled.setText("Enabled");
        packsEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packsEnabledActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout packsPanLayout = new javax.swing.GroupLayout(packsPan);
        packsPan.setLayout(packsPanLayout);
        packsPanLayout.setHorizontalGroup(
            packsPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(packsPanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(packsPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(packsEnabled)
                    .addComponent(packsPanelChoice, 0, 370, Short.MAX_VALUE))
                .addContainerGap())
        );
        packsPanLayout.setVerticalGroup(
            packsPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(packsPanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(packsPanelChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(packsEnabled)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        targetPanelChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetPanelChoiceActionPerformed(evt);
            }
        });

        targetEnabled.setSelected(true);
        targetEnabled.setText("Enabled");
        targetEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetEnabledActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout targetPanLayout = new javax.swing.GroupLayout(targetPan);
        targetPan.setLayout(targetPanLayout);
        targetPanLayout.setHorizontalGroup(
            targetPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(targetPanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(targetPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(targetEnabled)
                    .addComponent(targetPanelChoice, 0, 370, Short.MAX_VALUE))
                .addContainerGap())
        );
        targetPanLayout.setVerticalGroup(
            targetPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(targetPanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(targetPanelChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(targetEnabled)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        finishPanelChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishPanelChoiceActionPerformed(evt);
            }
        });

        finishEnabled.setSelected(true);
        finishEnabled.setText("Enabled");
        finishEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishEnabledActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout finishPanLayout = new javax.swing.GroupLayout(finishPan);
        finishPan.setLayout(finishPanLayout);
        finishPanLayout.setHorizontalGroup(
            finishPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(finishPanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(finishPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(finishEnabled)
                    .addComponent(finishPanelChoice, 0, 370, Short.MAX_VALUE))
                .addContainerGap())
        );
        finishPanLayout.setVerticalGroup(
            finishPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(finishPanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(finishPanelChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(finishEnabled)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        finishEnabled.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(helloPan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(licencePan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoPan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(packsPan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(targetPan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(finishPan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(helloPan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(packsPan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(targetPan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(finishPan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(infoPan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(licencePan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Hides all panel configurations
     */
    private void removeAllPans() {
        helloPan.setVisible(false);
        packsPan.setVisible(false);
        infoPan.setVisible(false);
        licencePan.setVisible(false);
        targetPan.setVisible(false);
        finishPan.setVisible(false);
    }

    private void panelsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_panelsListValueChanged
        removeAllPans();
        //Zap is just temporary value, I dont know why its needed, but rather not change it
        String o = "zap";
        try {
            o = panelsList.getSelectedValue().toString();
        } catch (Exception e) {
        }

        //Ifs to show the correct configurations for each panel type.
        if (o.contains("Hello"))
            helloPan.setVisible(true);
        else if (o.contains("Pack"))
            packsPan.setVisible(true);
        else if (o.contains("Info"))
            infoPan.setVisible(true);
        else if (o.contains("Licence"))
            licencePan.setVisible(true);
        else if (o.contains("Target"))
            targetPan.setVisible(true);
        else if (o.contains("Finish"))
            finishPan.setVisible(true);
        else if (o.contains("Install")) {
        }
}//GEN-LAST:event_panelsListValueChanged

    //Following methods take what the user selects from the combobox and changes it in the Jlist, keeping the diabled or enables portion.
    private void helloPanelChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helloPanelChoiceActionPerformed
        panelsModel.set(StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "HelloPanel", "CheckedHelloPanel", "HTMLHelloPanel", "HelloPaneltrue", "CheckedHelloPaneltrue", "HTMLHelloPaneltrue", "HelloPanelfalse", "CheckedHelloPanelfalse", "HTMLHelloPanelfalse"})),
                helloPanelChoice.getSelectedItem().toString() + helloEnabled.isSelected());
        htmlBrowse.setVisible(helloPanelChoice.getSelectedItem().toString().equals("HTMLHelloPanel"));
        jLabel4.setVisible(helloPanelChoice.getSelectedItem().toString().equals("HTMLHelloPanel"));
        htmlField.setVisible(helloPanelChoice.getSelectedItem().toString().equals("HTMLHelloPanel"));
    }//GEN-LAST:event_helloPanelChoiceActionPerformed

    private void packsPanelChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packsPanelChoiceActionPerformed
        panelsModel.set(StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "PacksPanel", "ImgPacksPanel", "TreePacksPanel", "PacksPaneltrue", "ImgPacksPaneltrue", "TreePacksPaneltrue", "PacksPanelfalse", "ImgPacksPanelfalse", "TreePacksPanelfalse",})),
                packsPanelChoice.getSelectedItem().toString() + packsEnabled.isSelected());
    }//GEN-LAST:event_packsPanelChoiceActionPerformed

    private void targetPanelChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetPanelChoiceActionPerformed
        panelsModel.set(StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "DefaultTargetPanel", "TargetPanel", "DefaultTargetPaneltrue", "TargetPaneltrue", "DefaultTargetPanelfalse", "TargetPanelfalse"})),
                targetPanelChoice.getSelectedItem().toString() + targetEnabled.isSelected());
    }//GEN-LAST:event_targetPanelChoiceActionPerformed

    private void finishPanelChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishPanelChoiceActionPerformed
        panelsModel.set(StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "SimpleFinishPanel", "FinishPanel", "SimpleFinishPaneltrue", "FinishPaneltrue", "SimpleFinishPanelfalse", "FinishPanelfalse"})),
                finishPanelChoice.getSelectedItem().toString() + finishEnabled.isSelected());
    }//GEN-LAST:event_finishPanelChoiceActionPerformed

    private void helloEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helloEnabledActionPerformed
        int i = StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "HelloPanel", "CheckedHelloPanel", "HTMLHelloPanel", "HelloPaneltrue", "CheckedHelloPaneltrue", "HTMLHelloPaneltrue", "HelloPanelfalse", "CheckedHelloPanelfalse", "HTMLHelloPanelfalse"}));
        panelsModel.set(i, panelsList.getSelectedValue().toString() + helloEnabled.isSelected());
    }//GEN-LAST:event_helloEnabledActionPerformed

    private void packsEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packsEnabledActionPerformed
        int i = StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "PacksPanel", "ImgPacksPanel", "TreePacksPanel", "PacksPaneltrue", "ImgPacksPaneltrue", "TreePacksPaneltrue", "PacksPanelfalse", "ImgPacksPanelfalse", "TreePacksPanelfalse",}));
        panelsModel.set(i, panelsList.getSelectedValue().toString() + packsEnabled.isSelected());
    }//GEN-LAST:event_packsEnabledActionPerformed

    private void targetEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetEnabledActionPerformed
        int i = StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "DefaultTargetPanel", "TargetPanel", "DefaultTargetPaneltrue", "TargetPaneltrue", "DefaultTargetPanelfalse", "TargetPanelfalse"}));
        panelsModel.set(i, panelsList.getSelectedValue().toString() + targetEnabled.isSelected());
    }//GEN-LAST:event_targetEnabledActionPerformed

    private void finishEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishEnabledActionPerformed
        int i = StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "SimpleFinishPanel", "FinishPanel", "SimpleFinishPaneltrue", "FinishPaneltrue", "SimpleFinishPanelfalse", "FinishPanelfalse"}));
        panelsModel.set(i, panelsList.getSelectedValue().toString() + finishEnabled.isSelected());
    }//GEN-LAST:event_finishEnabledActionPerformed

    private void infoEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoEnabledActionPerformed
        int i = StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "InfoPanel", "InfoPaneltrue", "InfoPanelfalse"}));
        panelsModel.set(i, panelsList.getSelectedValue().toString() + infoEnabled.isSelected());
    }//GEN-LAST:event_infoEnabledActionPerformed

    private void licenceEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licenceEnabledActionPerformed
        int i = StaticUtils.max(StaticUtils.indexesOf(panelsModel, new String[]{
                    "LicencePanel", "LicencePaneltrue", "LicencePanelfalse"}));
        panelsModel.set(i, panelsList.getSelectedValue().toString() + licenceEnabled.isSelected());
    }//GEN-LAST:event_licenceEnabledActionPerformed

    //Following methods open the JFileChooser, and assigns the value to the fileField
    private void infoBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoBrowseActionPerformed
        GUIUtils.open("text", infoFileField);
    }//GEN-LAST:event_infoBrowseActionPerformed

    private void licenceBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licenceBrowseActionPerformed
        GUIUtils.open("text", licenceFileField);
    }//GEN-LAST:event_licenceBrowseActionPerformed

    private void htmlBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_htmlBrowseActionPerformed
        GUIUtils.open("text", htmlField);
    }//GEN-LAST:event_htmlBrowseActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox finishEnabled;
    public javax.swing.JPanel finishPan;
    public javax.swing.JComboBox finishPanelChoice;
    public javax.swing.JCheckBox helloEnabled;
    public javax.swing.JPanel helloPan;
    public javax.swing.JComboBox helloPanelChoice;
    public javax.swing.JButton htmlBrowse;
    public javax.swing.JTextField htmlField;
    public javax.swing.JButton infoBrowse;
    public javax.swing.JCheckBox infoEnabled;
    public javax.swing.JTextField infoFileField;
    public javax.swing.JPanel infoPan;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JButton licenceBrowse;
    public javax.swing.JCheckBox licenceEnabled;
    public javax.swing.JTextField licenceFileField;
    public javax.swing.JPanel licencePan;
    public javax.swing.JCheckBox packsEnabled;
    public javax.swing.JPanel packsPan;
    public javax.swing.JComboBox packsPanelChoice;
    public javax.swing.JList panelsList;
    public javax.swing.JCheckBox targetEnabled;
    public javax.swing.JPanel targetPan;
    public javax.swing.JComboBox targetPanelChoice;
    // End of variables declaration//GEN-END:variables
}
