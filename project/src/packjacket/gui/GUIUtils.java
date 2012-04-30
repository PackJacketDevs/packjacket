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

import com.l2fprod.common.swing.JDirectoryChooser;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.VerticalLayout;
import packjacket.RunnerClass;
import packjacket.xml.Pack;
import packjacket.xml.XFile;

/**
 * Class that has utilities for GUI things.
 * @author Amandeep Grewal
 * @author Manodasan Wignarajah
 */
public class GUIUtils {

    private static JFileChooser fc = new JFileChooser();
    private static JDirectoryChooser dc = new JDirectoryChooser();

    /**
     * Ask to save a file from user
     * @param filterL which files allowed to be saved
     * @return file chosen
     */
    public static File save(FileNameExtensionFilter filterL) {
        //Adds the parameter passed filter to the filters list
        ArrayList<FileFilter> filters = new ArrayList<FileFilter>();
        filters.add(filterL);

        JFileChooser chooser = fc;
        //Traverses through the filters in the JFileChooser to remove them if it isn't the accept all file filter
        for (FileFilter filter : chooser.getChoosableFileFilters())
            //Checks whether it is not the accept all file filter
            if (!filter.equals(chooser.getAcceptAllFileFilter()))
                chooser.removeChoosableFileFilter(filter);
        //Removes default filter and adds custom filters if there are any
        if (filters != null && !filters.isEmpty()) {
            //Traveses through the elements in filters to add them as file filter for the JFileChooser
            for (FileFilter filter : filters)
                try {
                    chooser.addChoosableFileFilter(filter);
                } catch (java.lang.NullPointerException e) {
                }
            //Removes the accept all file filter
            chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
            try {
                //Sets the file filter as the one the first one in the filters list, to fix a bug with OpenJDK in linux
                chooser.setFileFilter(filters.iterator().next());
            } catch (java.lang.NullPointerException e) {
            }
        }
        int state;
        try {
            //If on a GTK theme, then dont use the GTK file-chooser, rather use the metal one
            if (UIManager.getLookAndFeel().toString().contains("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")) {
                boolean useNimbus = false;
                for (LookAndFeelInfo li : UIManager.getInstalledLookAndFeels())
                    if (li.getClassName().equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel")) {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                        useNimbus = true;
                        break;
                    }
                if (!useNimbus)
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(chooser);
                chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
                state = chooser.showSaveDialog(RunnerClass.mf);
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else
                state = chooser.showSaveDialog(RunnerClass.mf);
        } catch (Exception e) {
            state = chooser.showSaveDialog(RunnerClass.mf);
        }
        //Adds the accept all file filter if the accept all file filter was removed before
        if (filters != null && !filters.isEmpty())
            chooser.addChoosableFileFilter(chooser.getAcceptAllFileFilter());
        //If the JFileChooser returned an error, it displays an error message, and reopens the JFileChooser
        if (state == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(RunnerClass.mf, "An error occured while trying to save that file!", "Error", JOptionPane.ERROR_MESSAGE);
            return save(filterL);
            //If the JFileChooser returns an non error, it checks various things before saving
        } else if (state == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            //Adds the extension to the end of the file if it wasn't already there
            if (!f.getAbsolutePath().endsWith("." + filterL.getExtensions()[0]))
                f = new File(f.getAbsolutePath() + "." + filterL.getExtensions()[0]);
            //Checks whether the file already exists, and if it does it displays a messages asking if they want to overwrite
            if (f.exists()) {
                int option = JOptionPane.showConfirmDialog(RunnerClass.mf, "This file exists.\nWould you like to overwrite it?", "Overwrite", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                //Does the appropriate action depending on what the user wanted to do
                if (option == JOptionPane.YES_OPTION) {
                } else if (option == JOptionPane.NO_OPTION)
                    return save(filterL);
                else
                    return null;
            }
            return f;
        } else
            return null;
    }

    /**
     * Prompts user for file to open. Ensures file exists.
     * @param filters the filters to use when selecting the file
     * @param isFC if the file-chooser to be shown is a file chooser or a directory chooser
     * @return the file selected by user
     */
    public static File open(Collection<FileFilter> filters, boolean isFC) {
        JFileChooser chooser;
        if (isFC)
            chooser = GUIUtils.fc;
        else
            chooser = dc;
        //Traves through all the file filters in the JFileChooser and removes them if it isnt the AcceptAllFileFilter
        for (FileFilter filter : chooser.getChoosableFileFilters())
            if (!filter.equals(chooser.getAcceptAllFileFilter()))
                chooser.removeChoosableFileFilter(filter);
        //Removes default filter and adds custom filters if there are any
        if (filters != null && !filters.isEmpty()) {
            for (FileFilter filter : filters)
                chooser.addChoosableFileFilter(filter);
            chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
            try {
                chooser.setFileFilter(filters.iterator().next());
            } catch (java.lang.NullPointerException e) {
            }
        }
        int state;
        try {
            //If on a GTK theme, then dont use the GTK file-chooser, rather use the metal one
            if (isFC && UIManager.getLookAndFeel().toString().contains("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")) {
                boolean useNimbus = false;
                for (LookAndFeelInfo li : UIManager.getInstalledLookAndFeels())
                    if (li.getClassName().equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel")) {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                        useNimbus = true;
                        break;
                    }
                if (!useNimbus)
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(chooser);
                chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
                state = chooser.showOpenDialog(RunnerClass.mf);
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else
                state = chooser.showOpenDialog(RunnerClass.mf);
        } catch (Exception e) {
            state = chooser.showOpenDialog(RunnerClass.mf);
        }
        //Adds the accept all file filter if it was removed earlier
        if (filters != null && !filters.isEmpty())
            chooser.addChoosableFileFilter(chooser.getAcceptAllFileFilter());
        //Depending on the value returned by the JFileChooser, appropriate action is done
        if (state == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(RunnerClass.mf, "An error occured while trying to open that file!", "Error", JOptionPane.ERROR_MESSAGE);
            return open(filters, isFC);
        } else if (state == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            //If file doesn't exist message is given to the user and asks to reselect a file
            if (!f.exists()) {
                JOptionPane.showMessageDialog(RunnerClass.mf, "That file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return open(filters, isFC);
                //If the file has a not accepted extension, message is given to user
            } else if (!accept(f, filters)) {
                JOptionPane.showMessageDialog(RunnerClass.mf, "The file type is currently not supported or is not an accepted file type!", "Error", JOptionPane.ERROR_MESSAGE);
                return open(filters, isFC);
            }
            return f;
        } else
            return null;
    }

    /**
     * Determines whether this file is accepted by the specified collection of filters
     * @param f The file to determine whether to accept
     * @param filters The filtesr to check whether they accept the file
     * @return boolean representation of whether any of the filters accept the file
     */
    public static boolean accept(File f, Collection<FileFilter> filters) {
        //If there is no filters, or is null true is returned meaning to accept the file
        if (filters == null || filters.isEmpty())
            return true;
        //Goes through all the fitlers and sees whether any accepts the file, if it does true is returned
        for (FileFilter filter : filters)
            if (filter.accept(f))
                return true;
        return false;
    }

    /**
     * Prompts user for file to open.
     * @param filters the filters to use when selecting the file
     * @param tc where the path will be displayed
     */
    public static void open(Collection<FileFilter> filters, JTextComponent tc) {
        File f = open(filters, true);
        if (f != null)
            tc.setText(f.getPath());
    }

    /**
     * Prompts user for file to open.
     * @param filter what files can be opened
     * @return file chosen
     */
    public static File open(FileFilter[] filter) {
        ArrayList<FileFilter> filters = new ArrayList<FileFilter>();
        for (FileFilter f : filter)
            filters.add(f);
        return open(filters, true);
    }

    /**
     * Prompts user for file to open.
     * @param filters the filters to use when selecting the file
     * @param tc where the path will be displayed
     */
    public static void open(FileFilter filter, JTextComponent tc) {
        ArrayList<FileFilter> filters = new ArrayList<FileFilter>();
        filters.add(filter);
        open(filters, tc);
    }

    /**
     * Prompts user for file to open.
     * @param ext type of files to allow, either <code>images</code>, <code>text</code>, or <code>all</code>
     * @param tc where the path will be displayed
     */
    public static void open(String ext, JTextComponent tc) {
        //Calls the open method with the appropriate parameters depending on what ext is
        if (ext.equalsIgnoreCase("images"))
            open(new javax.swing.filechooser.FileNameExtensionFilter("Images (.bmp, .gif, .jpg, .png)", new String[]{"bmp", "gif", "jpeg", "jpg", "png"}), tc);
        else if (ext.equalsIgnoreCase("text"))
            open(new javax.swing.filechooser.FileNameExtensionFilter("Text (.txt, .html)", new String[]{"txt", "html", "htm"}), tc);
        else if (ext.equalsIgnoreCase("all"))
            open(new ArrayList<FileFilter>(), tc);
    }

    /**
     * Ask user for directory to open
     * @param tc the field to be filled in
     */
    public static void openDir(JTextComponent tc) {
        File f = open(null, false);
        if (f != null)
            tc.setText(f.getPath());
    }

    /**
     * Makes a panel with components that are always shown, and some that only shown when suer wants to, and are initially hidden
     * @param shown containing all components that are always shown
     * @param hidden containing all components that only shown when user wants to
     * @param okBtnListener the listener on the OK button
     * @param cancelBtnListener the listener on the Cancel button
     * @return the panel with the mandatory shown, and a button to show optional
     */
    public static JPanel makeCollapserPanel(JComponent shown, JComponent hidden, ActionListener okBtnListener, ActionListener cancelBtnListener) {
        return makeCollapserPanel(shown, hidden, true, okBtnListener, cancelBtnListener);
    }

    /**
     * Makes a panel with components that are always shown, and some that only shown when suer wants to, and are initially hidden
     * @param shown containing all components that are always shown
     * @param hidden containing all components that only shown when user wants to
     * @param addOK to add the buttons or not
     * @param okBtnListener the listener on the OK button
     * @param cancelBtnListener the listener on the Cancel button
     * @return the panel with the mandatory shown, and a button to show optional
     */
    public static JPanel makeCollapserPanel(JComponent shown, JComponent hidden, boolean addOK, ActionListener okBtnListener, ActionListener cancelBtnListener) {
        JXCollapsiblePane cp = new JXCollapsiblePane();
        cp.add(hidden);
        final JPanel entire = new JPanel();
        class BooleanCustom {

            public boolean data;
        }
        final BooleanCustom okToPack = new BooleanCustom();
        okToPack.data = false;
        cp.setCollapsed(true);
        cp.addPropertyChangeListener(JXCollapsiblePane.ANIMATION_STATE_KEY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("propertychange-collapser " + evt.getNewValue());
                RunnerClass.mf.fitContentResize();
                if (evt.getNewValue().equals("expanded") || evt.getNewValue().equals("collapsed")) {
                    okToPack.data = false;
                    RunnerClass.mf.setMinimumSize(RunnerClass.mf.getPreferredSize());
                } else if (Math.abs(entire.getPreferredSize().hashCode() - RunnerClass.mf.getContentPane().getSize().hashCode()) < 5000)
                    okToPack.data = true;
            }
        });

        // get the built-in toggle action
        Action tog = cp.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);
        // use the collapse/expand icons from the JTree UI
        tog.putValue(JXCollapsiblePane.COLLAPSE_ICON, UIManager.getIcon("Tree.expandedIcon"));
        tog.putValue(JXCollapsiblePane.EXPAND_ICON, UIManager.getIcon("Tree.collapsedIcon"));

        //Set up the button to show/hide the optional panel
        JButton collapseBtn = new JButton(tog);
        collapseBtn.setHorizontalAlignment(SwingConstants.LEFT);
        collapseBtn.setText("Optional advanced features");
        collapseBtn.setFont(collapseBtn.getFont().deriveFont(11f));
        collapseBtn.setBorderPainted(false);
        collapseBtn.setFocusable(false);


        //Add everything to the bottom panel, in order
        JPanel bottom = new JPanel() {

            @Override
            public void repaint() {
                super.repaint();
                if (okToPack.data)
                    RunnerClass.mf.pack();
            }
        };
        bottom.setLayout(new VerticalLayout());
        bottom.add(new JSeparator(), BorderLayout.NORTH);
        bottom.add(collapseBtn);
        bottom.add(cp);
        if (addOK) {
            //OK/Cancel Button, under all
            JPanel okBar = new JPanel();
            okBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
            okBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            JButton okBtn = new JButton("OK");
            okBtn.setIcon(new javax.swing.ImageIcon(RunnerClass.mf.getClass().getResource("/resources/ok.png")));
            okBtn.addActionListener(okBtnListener);
            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setIcon(new javax.swing.ImageIcon(RunnerClass.mf.getClass().getResource("/resources/cancel.png")));
            cancelBtn.addActionListener(cancelBtnListener);
            okBar.add(okBtn);
            okBar.add(cancelBtn);
            bottom.add(okBar);
        }

        //Sets up the large panel with everything on it
        entire.setLayout(new BorderLayout());
        entire.add(shown, BorderLayout.CENTER);
        entire.add(bottom, BorderLayout.SOUTH);

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
//                RunnerClass.mf.setMinimumSize(RunnerClass.mf.getPreferredSize());
//                RunnerClass.mf.pack();
//                RunnerClass.mf.fitContentResize();
            }
        });

        return entire;
    }

    /**
     * Gets the DefaultComboBoxModel for the combo box where the user gets to choose the file, or dir in the added packs
     * @param dirOnly determines whether to add only dirs or only files
     * @return the DefaultComboBoxModel for the combo box
     */
    public static DefaultComboBoxModel getAllFilesForComboBoxModel(boolean dirOnly) {
        ArrayList<String> fileS = new ArrayList<String>();
        fileS.add("$INSTALL_PATH/");
        try {
            //Traverses through all the packs
            for (Pack p : RunnerClass.mf.packsPanel.packs.list)
                //Traverses through all the files in the pack
                for (XFile f : p.file) {
                    //Gets the targetDir for the file or dir
                    String targetDir = f.packs_file_targetdir + (!(f.packs_file_targetdir.endsWith("/") || f.packs_file_targetdir.endsWith("\\")) ? "/" : "");
                    //Checks whether it is a file, and whether user wants file, if both is true the file is added
                    if (f.sourceFileOption && !dirOnly)
                        fileS.add(targetDir + (f.renameTargetFileOption ? f.packs_file_renameTarget : new File(f.packs_file_sourceFile).getName()));
                    //If the XFile is a directory, appropriate action is done depending on whether it is dirOnly or not
                    else if (f.sourceDirOption) {
                        //If it is dirOnly the dir is added
                        if (dirOnly)
                            fileS.add(targetDir + new File(f.packs_file_sourceDir).getName());
                        //Adds the appropriate files/directories in the directory
                        addFiles(new File(f.packs_file_sourceDir), fileS, targetDir + new File(f.packs_file_sourceDir).getName() + "/", dirOnly);
                    }
                }

        } catch (Exception e) {
        }
        return (new DefaultComboBoxModel(fileS.toArray()));
    }

    /**
     * Gets the DefaultComboBoxModel for the combo box
     * @return the DefaultComboBoxModel for the combo box
     */
    public static DefaultComboBoxModel getFilesforComboBoxModel() {
        ArrayList<String> fileS = new ArrayList<String>();
        fileS.add("");
        //Traverses through all the files in the current pack
        for (XFile f : packjacket.RunnerClass.mf.packsPanel.gen.files.list) {
            //Determines the targetDir for the XFile
            String targetDir = f.packs_file_targetdir + (!(f.packs_file_targetdir.endsWith("/") || f.packs_file_targetdir.endsWith("\\")) ? "/" : "");
            //Checks whether it is source file option, if it is, it is added to the list
            if (f.sourceFileOption)
                fileS.add(targetDir + (f.renameTargetFileOption ? f.packs_file_renameTarget : new File(f.packs_file_sourceFile).getName()));
            //If it is a directory, the files in it are added
            else if (f.sourceDirOption)
                addFiles(new File(f.packs_file_sourceDir), fileS, targetDir + new File(f.packs_file_sourceDir).getName() + "/", false);
        }
        return (new DefaultComboBoxModel(fileS.toArray()));
    }

    /**
     * Filters the DefaultComboBox to make sure there are only images in it
     * @return
     */
    public static DefaultComboBoxModel filterModel() {
        DefaultComboBoxModel model = getAllFilesForComboBoxModel(false);
        //Goes through all the elements in the DefaultComboBoxModel
        for (int a = 0; a < model.getSize(); a++) {
            //Gets the element
            String modelElement = model.getElementAt(a).toString();
            //Determines whether it ends with the appropriate image extension for icons, otherwise it is removed
            if (!(modelElement.endsWith(".ico") || modelElement.endsWith(".jpg") || modelElement.endsWith(".png") || modelElement.endsWith(".bmp") || modelElement.endsWith(".pct") || modelElement.endsWith(".gif"))) {
                model.removeElement(model.getElementAt(a));
                a--;
            }
        }
        //If there are no image extensions for icons in the model, the install path is added
        if (model.getSize() == 0)
            model.addElement("$INSTALL_PATH/");
        return model;
    }

    /**
     * Adds the files/directoires in the dir
     * @param dir the dir to look in for the files/directories
     * @param files the list to add the files to
     * @param targetDir the current target dir
     * @param dirOnly Whether it is dirOnly or not
     */
    public static void addFiles(File dir, ArrayList<String> files, String targetDir, boolean dirOnly) {
        //Traverse the files in the dir
        for (File f : dir.listFiles())
            //Checks whether it is a directory
            if (f.isDirectory()) {
                //If it is dirOnly the the dir is added to the list
                if (dirOnly)
                    files.add(targetDir + f.getName());
                //The files/directories are also added recursively
                addFiles(f, files, targetDir + f.getName() + "/", dirOnly);
                //If it is not dirOnly the file is added
            } else if (!dirOnly)
                files.add(targetDir + f.getName());
    }
}
