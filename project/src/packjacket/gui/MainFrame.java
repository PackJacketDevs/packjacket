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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import packjacket.RunnerClass;
import packjacket.xml.Author;
import packjacket.xml.Laf;
import packjacket.xml.Locale;
import packjacket.xml.Pack;
import packjacket.xml.Shortcut;
import packjacket.xml.UserInput;
import packjacket.xml.XFile;
import packjacket.xml.XML;
import packjacket.xml.XProcess;
import packjacket.xmlcreation.XMLUtils;

/**
 * The main JFrame class
 * @author Amandeep Grewal
 * @author Manodasan Wignarajah
 */
public class MainFrame extends JFrame {

    public final int OLD_VERSION = 0, NEW_VERSION = 1, SAME_VERSION = 2, UNKNOWN_VERSION = 3;
    public GridBagConstraints gbc;
    public Stack<ElementPanel> panelsStack;
    public MainPanel tabs;
    public JMenuBar mb;
    public JMenu fileMenu, insertMenu, aboutMenu;
    public JMenuItem quitItem, saveItem, saveAsItem, openItem, newItem, submitBugItem, aboutItem, checkVersionItem, insertPackItem;
    public GeneralPanel generalPanel;
    public GeneralOptional generalOptionalPanel;
    public AuthorsPanel authorsPanel;
    public GUIPanel guiPanel;
    public LanguagePanel languagePanel;
    public PanelsPanel panelsPanel;
    public PacksPanel packsPanel;
    public PackagingPanel packagingPanel;
    public ShortcutsPanel shortcutsPanel;
    public UserInputPanel userInputPanel;
    public ProcessesPanel processesPanel;
    public File pjc;
    public XML pjcxml;
    public Collection<String> dependsErr;
    public boolean isMaximized, pjResized;
    public int userWidth, userHeight;

    /**
     * Constructs a new JFrame
     * @throws IOException
     */
    public MainFrame() throws IOException {
        //Sets up all variables
        tabs = new MainPanel();
        new FileDrop(tabs, BorderFactory.createEmptyBorder(), new FileDrop.Listener() {

            @Override
            public void filesDropped(java.io.File[] files) {
                if (files.length != 1)
                    return;
                try {
                    if (!checkClose())
                        return;
                } catch (IOException ex) {
                    RunnerClass.logger.log(Level.SEVERE, null, ex);
                }
                open(files[0], false);
            }
        });
        panelsStack = new Stack<ElementPanel>();
        mb = new JMenuBar();
        fileMenu = new JMenu("File");
        insertMenu = new JMenu("Insert");
        aboutMenu = new JMenu("Help");
        insertPackItem = new JMenuItem("Pack");
        submitBugItem = new JMenuItem("Submit Bug Report");
        aboutItem = new JMenuItem("About");
        quitItem = new JMenuItem("Quit");
        newItem = new JMenuItem("New");
        saveItem = new JMenuItem("Save");
        saveAsItem = new JMenuItem("Save As...");
        openItem = new JMenuItem("Open...");
        checkVersionItem = new JMenuItem("Check For New Version");
        //Sets up actions
        saveItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save();
                } catch (IOException ex) {
                    RunnerClass.logger.log(Level.SEVERE, null, ex);
                }
            }
        });
        saveAsItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAs();
                } catch (IOException ex) {
                    RunnerClass.logger.log(Level.SEVERE, null, ex);
                }
            }
        });
        newItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populateTabs();
                } catch (IOException ex) {
                    RunnerClass.logger.log(Level.SEVERE, null, ex);
                }
            }
        });
        openItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    open();
                } catch (IOException ex) {
                    RunnerClass.logger.log(Level.SEVERE, null, ex);
                }
            }
        });
        quitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        submitBugItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Desktop.isDesktopSupported())
                        Desktop.getDesktop().browse(new URI("https://sourceforge.net/tracker/?func=add&group_id=235519&atid=1096880"));
                } catch (Exception ex) {
                    RunnerClass.logger.log(Level.SEVERE, null, ex);
                }
            }
        });
        aboutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                About a = new About();
                a.setIconImages(getIconImages());
                a.setTitle("About PackJacket");
                a.setLocationRelativeTo(null);
                a.setVisible(true);
            }
        });
        checkVersionItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int v = checkVersion();
                if (v == NEW_VERSION)
                    JOptionPane.showMessageDialog(MainFrame.this, "You have a version that is not publicly released yet", "Version Check", JOptionPane.INFORMATION_MESSAGE);
                else if (v == SAME_VERSION)
                    JOptionPane.showMessageDialog(MainFrame.this, "You have the latest version", "Version Check", JOptionPane.INFORMATION_MESSAGE);
                else if (v == UNKNOWN_VERSION)
                    JOptionPane.showMessageDialog(MainFrame.this, "PackJacket cannot determine the latest version because there is a most likely a problem with your internet connection", "Version Check", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        CursorController.createListener(this, checkVersionItem);
        insertPackItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File f = GUIUtils.open(new FileNameExtensionFilter[]{new javax.swing.filechooser.FileNameExtensionFilter("PJC PackJacket Configuration (.pjc)", new String[]{"pjc"}), new javax.swing.filechooser.FileNameExtensionFilter("XML pack configuration file (.xml)", new String[]{"xml"})});
                if (f != null)
                    try {
                        if (f.getCanonicalPath().endsWith(".pjc"))
                            try {
                                ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(f)));
                                XML x = (XML) ois.readObject();
                                ois.close();
                                InsertPack ip = new InsertPack((ArrayList<Pack>) x.packs);
                                ip.setIconImages(getIconImages());
                                ip.setLocationRelativeTo(RunnerClass.mf);
                                ip.setVisible(true);
                            } catch (IOException ex) {
                                RunnerClass.logger.log(Level.SEVERE, null, ex);
                            } catch (ClassNotFoundException ex) {
                                RunnerClass.logger.log(Level.SEVERE, null, ex);
                            }
                        else if (f.getCanonicalPath().endsWith(".xml")) {
                            Pack p = new Pack();
                            p.xmlFile = f.getCanonicalPath();
                            RunnerClass.mf.packsPanel.packs.add(p);
                        }
                    } catch (IOException ee) {
                    }
            }
        });
        //Sets up accelerators
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        //Sets up icons
        openItem.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/resources/open.png"))));
        newItem.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/resources/new.png"))));
        saveItem.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/resources/save.png"))));
        saveAsItem.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/resources/saveas.png"))));
        quitItem.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/resources/quit.png"))));
        submitBugItem.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/resources/bug.png"))));
        aboutItem.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/resources/about.png"))));

        //Adds menu items -> menus -> menu bar -> frame
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(quitItem);
        insertMenu.add(insertPackItem);
        aboutMenu.add(submitBugItem);
        aboutMenu.add(checkVersionItem);
        aboutMenu.add(aboutItem);
        mb.add(fileMenu);
        mb.add(insertMenu);
        mb.add(aboutMenu);
        setJMenuBar(mb);
        //Sets the icons for the frame, multiple sizes for different location on window manager
        List<Image> icons = new LinkedList<Image>();
        for (int x = 512; x >= 8; x /= 2)
            icons.add(ImageIO.read(getClass().getResource("/resources/logo" + x + ".png")));
        setIconImages(icons);

        remove(tabs);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                closing();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        checkVersion();
                    }
                }).start();
            }
        });

        addWindowStateListener(new WindowAdapter() {

            @Override
            public void windowStateChanged(WindowEvent e) {
                int state = e.getNewState();
                if (state == Frame.MAXIMIZED_BOTH || state == Frame.MAXIMIZED_HORIZ || state == Frame.MAXIMIZED_VERT)
                    isMaximized = true;
                else if (state == 0)
                    isMaximized = false;
                RunnerClass.logger.finest("resized MAX " + isMaximized + state);
                System.out.println("resized MAX " + isMaximized + state);
            }
        });

        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent evt) {
                Component c = (Component) evt.getSource();

                RunnerClass.logger.finest(pjResized + "resized " + c);
                System.out.println(pjResized + "resized " + c);

                if (!pjResized) {
                    userWidth = c.getWidth();
                    userHeight = c.getHeight();
                }
                pjResized = false;
            }
        });
        isMaximized = pjResized = false;
        userWidth = userHeight = -1;

        populateTabs();
        //Adds listener for tab changes, for process panel for the autocompleter
        tabs.tabs.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (((JTabbedPane) e.getSource()).getSelectedIndex() == tabs.tabs.getTabCount() - 1) {
                    String item = RunnerClass.mf.processesPanel.logFilePath.getSelectedItem().toString();
                    RunnerClass.mf.processesPanel.logFilePath.setModel(GUIUtils.getAllFilesForComboBoxModel(true));
                    RunnerClass.mf.processesPanel.logFilePath.setSelectedItem(item);
                    setMinimumSize(getPreferredSize());
                }
            }
        });
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                pack();
            }
        });
    }

    /**
     * Adds new tabs
     * @throws IOException
     */
    public void populateTabs() throws IOException {
        if (!checkClose())
            return;
        setTitle("PackJacket");
        //Removes old tabs
        tabs.tabs.removeAll();
        //Makes new tabs
        generalPanel = new GeneralPanel();
        generalOptionalPanel = new GeneralOptional();
        authorsPanel = new AuthorsPanel();
        guiPanel = new GUIPanel();
        languagePanel = new LanguagePanel();
        panelsPanel = new PanelsPanel();
        packsPanel = new PacksPanel();
        packagingPanel = new PackagingPanel();
        shortcutsPanel = new ShortcutsPanel();
        userInputPanel = new UserInputPanel();
        processesPanel = new ProcessesPanel();

        //Adds deafult tabs
        tabs.tabs.addTab("General", GUIUtils.makeCollapserPanel(generalPanel, generalOptionalPanel, false, null, null));
        tabs.tabs.addTab("Authors", authorsPanel);
        tabs.tabs.addTab("Language", languagePanel);
        tabs.tabs.addTab("Panels", panelsPanel);
        tabs.tabs.addTab("Packs", packsPanel);

        //Adds optional tabs if optional tab are wanted by user
        if (tabs.optionalTabs.isSelected()) {
            tabs.tabs.addTab("GUI", RunnerClass.mf.guiPanel);
            tabs.tabs.addTab("Packaging", RunnerClass.mf.packagingPanel);
            tabs.tabs.addTab("Shortcuts", RunnerClass.mf.shortcutsPanel);
            tabs.tabs.addTab("User Input", RunnerClass.mf.userInputPanel);
            tabs.tabs.addTab("Processes", RunnerClass.mf.processesPanel);
        }
        pjc = null;
        pjcxml = getXML();
        //Removes all panels
        while (!panelsStack.isEmpty())
            removeP();
        //Adds new tabbed pane
        addP(tabs);
    }

    /**
     * Checks for new version online
     * @return result of check
     */
    public int checkVersion() {
        //Date this version released on
        final int YEAR = 2012, MONTH = 1, DAY = 1;
        int versionCheck;
        try {
            //Collects info about user
            StringBuffer os = new StringBuffer();
            os.append(System.getProperty("os.name").replace(' ', '_'));
            os.append("-");
            os.append(System.getProperty("os.arch").replace(' ', '_'));
            os.append("-");
            os.append(System.getProperty("os.version").replace(' ', '_'));
            //Asks for new version release date, and send anonymous user data
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://packjacket.sourceforge.net/vers.php?v=" + RunnerClass.VERSION + "&os=" + os.toString()).openStream()));

            //Gets new release date
            int year = Integer.parseInt(in.readLine());
            int month = Integer.parseInt(in.readLine());
            int day = Integer.parseInt(in.readLine());

            //Sets up two calendars to compare
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = (Calendar) c1.clone();
            c2.setTime(c1.getTime());
            c1.set(YEAR, MONTH - 1, DAY);
            c2.set(year, month - 1, day);

            //Infos out the release dates
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM d, yyyy");
            RunnerClass.logger.info("This version was released on: " + sdf.format(c1.getTime()));
            RunnerClass.logger.info("The latest version was released on: " + sdf.format(c2.getTime()));

            //Comapres dates
            if (c1.before(c2)) {
                RunnerClass.logger.info("You do not have the latest version.");
                versionCheck = OLD_VERSION;
            } else if (c1.after(c2)) {
                RunnerClass.logger.info("You have a version that is not publicly released yet.");
                versionCheck = NEW_VERSION;
            } else {
                RunnerClass.logger.info("You have the latest version.");
                versionCheck = SAME_VERSION;
            }
        } catch (Exception ex) {
            versionCheck = UNKNOWN_VERSION;
            RunnerClass.logger.info("PackJacket cannot determine the latest version because there is a most likely a problem "
                    + "with your internet conection. The error is the following:");
            RunnerClass.logger.log(Level.SEVERE, null, ex);
        }
        //Asks to get new version
        if (versionCheck == OLD_VERSION) {
            int response = JOptionPane.showConfirmDialog(null,
                    "You do not have the latest version.\nWould you like to download the new version?", "Version Check",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION)
                if (Desktop.isDesktopSupported())
                    try {
                        Desktop.getDesktop().browse(new URI("http://packjacket.sourceforge.net/download/"));
                    } catch (Exception ex) {
                        RunnerClass.logger.log(Level.SEVERE, null, ex);
                    }
        }
        return versionCheck;
    }

    /**
     * Asks user to open PJC
     * @throws IOException
     */
    public void open() throws IOException {
        if (!checkClose())
            return;
        File f = GUIUtils.open(new FileNameExtensionFilter[]{new javax.swing.filechooser.FileNameExtensionFilter("PJC PackJacket Configuration (.pjc)", new String[]{"pjc"})});
        open(f, true);
    }

    /**
     * Reads/opens PJC file
     * @param f file to be opened
     * @param fileChooser is user has already passed through checks in the file chooser (i.e. args)
     */
    public void open(File f, boolean fileChooser) {
        try {
            if (f != null) {
                //Check for if it exists, and if it is a PJC file
                if (!fileChooser)
                    if (!f.exists()) {
                        JOptionPane.showMessageDialog(RunnerClass.mf, "The specified file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (!new javax.swing.filechooser.FileNameExtensionFilter("PJC PackJacket Configuration (.pjc)", new String[]{"pjc"}).accept(f)) {
                        JOptionPane.showMessageDialog(RunnerClass.mf, "The specified file is not a PJC PackJacket Configuration (.pjc) file!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                //Reads
                ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(f)));
                XML x = (XML) ois.readObject();
                setXML(x);
                pjc = f;
                pjcxml = x;
                setTitle("PackJacket - " + pjc.getName().substring(0, pjc.getName().length() - 4));
                ois.close();
            }
            //Error occured when loading
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(RunnerClass.mf, "The specified file could not be loaded as a PackJacket configuration file (.pjc)!", "Error", JOptionPane.ERROR_MESSAGE);
            RunnerClass.logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Saves file
     * @param checkClose to check for unsaved changes (false, if the calling class is checkClose())
     * @throws IOException
     */
    public void save(boolean checkClose) throws IOException {
        //If none open, ask where to save
        if (pjc == null) {
            saveAs(checkClose);
            return;
        }
        //Writes out
        XML x = getXML();
        ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(pjc)));
        oos.writeObject(x);
        oos.close();
        pjcxml = getXML();
    }

    /**
     * Saves file
     * @throws IOException
     */
    public void save() throws IOException {
        save(false);
    }

    /**
     * Asks user where to save, then saves there
     * @throws IOException
     */
    public void saveAs() throws IOException {
        saveAs(true);
    }

    /**
     * Asks user where to save, then saves there
     * @param checkClose to check for unsaved changes (false, if the calling class is checkClose())
     * @throws IOException
     */
    public void saveAs(boolean checkClose) throws IOException {
        if (checkClose)
            if (!checkClose())
                return;
        //Asks user where to save
        File f = GUIUtils.save(new javax.swing.filechooser.FileNameExtensionFilter("PJC PackJacket Configuration (.pjc)", new String[]{"pjc"}));
        //If any selected
        if (f != null) {
            if (!f.getName().endsWith(".pjc"))
                f = new File(f.getPath() + ".pjc");
            pjc = f;
            setTitle("PackJacket - " + pjc.getName().substring(0, pjc.getName().length() - 4));
            //Writes
            save();
        }
    }

    /**
     * Asks user to save if any files open (less annoyance)
     * @return if user continues or clicks cancel (true = continue, flase = cancel)
     * @throws IOException
     */
    public boolean checkClose() throws IOException {
        //If just starting app, no changes made obviously
        if (pjcxml == null)
            return true;
        //Checks if any changes made
        boolean changesMade = !pjcxml.equals(getXML());
        //If no changes made, continue
        if (!changesMade)
            return true;
        //Asks to save changes
        int option;
        //Show file being edited if any open, otherwise just ask without saying the file name
        if (pjc != null)
            option = JOptionPane.showConfirmDialog(RunnerClass.mf, "Do you want to save the changes made to\n" + pjc.getCanonicalPath() + "?",
                    "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        else
            option = JOptionPane.showConfirmDialog(RunnerClass.mf, "Do you want to save the changes made?",
                    "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION)
            save(false);
        else if (option == JOptionPane.NO_OPTION) {
        } else
            return false;
        return true;
    }

    /**
     * When closing, check for save, then close
     */
    public void closing() {
        try {
            if (!checkClose())
                return;
        } catch (IOException ex) {
            RunnerClass.logger.log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }

    /**
     * Resizes the window to the heuristically determined user size
     */
    public void fitContentResize() {
        System.out.println("fitContentResize - general");
        RunnerClass.logger.finest("fitContentResize - general");
        setMinimumSize(getPreferredSize());
        if (!isMaximized)
            if (userWidth != getWidth() || userHeight != getHeight()) {
                System.out.println("fitContentResize - change size");
                RunnerClass.logger.finest("fitContentResize - change size");
                pjResized = true;
                setSize(userWidth, userHeight);
            }
        //Updates all GUI
        validate();
        repaint();
    }

    /**
     * Adds panel "on top" of old
     * @param panel panel to add
     */
    public void addP(JComponent panel) {
       System.out.println("doub?: "+ this.isDoubleBuffered());
        //Gets size of old panel, if there was one
        if (!panelsStack.isEmpty())
//            panelsStack.peek().setHeight(getHeight());
//            panelsStack.peek().setWidth(getWidth());
            remove(panelsStack.peek().getPanel());
        panelsStack.push(new ElementPanel(panel));
        add(panel, gbc);
        fitContentResize();
    }

    /**
     * Removes top most panel
     */
    public void removeP() {
        //Removes the panel
        remove(panelsStack.pop().getPanel());
        //If the stack isn't empty the panel next in the stack is added, and size of JFrame is setf
        if (!panelsStack.isEmpty()) {
            add(panelsStack.peek().getPanel(), gbc);
            panelsStack.peek().getPanel().repaint();
        }
        fitContentResize();
        tabs.repaint();
    }

    /**
     * Gets errors related to the ordering of panels
     * @return errors related to the ordering of panels
     */
    public String getOrderErrors() {
        //Gets indexes of each differnet type of panel
        int hello = panelsPanel.getIndex("HelloPanel");
        int info = panelsPanel.getIndex("InfoPanel");
        int lic = panelsPanel.getIndex("LicencePanel");
        int pack = panelsPanel.getIndex("PacksPanel");
        int target = panelsPanel.getIndex("TargetPanel");
        int install = panelsPanel.getIndex("InstallPanel");
        int ui = panelsPanel.getIndex("UserInputPanel");
        int process = panelsPanel.getIndex("ProcessPanel");
        int finish = panelsPanel.getIndex("FinishPanel");

        StringBuffer sb = new StringBuffer();

        //Compiles errors
        if (panelsPanel.helloEnabled.isSelected())
            if (hello != 0)
                for (int x = hello - 1; x >= 0; x--)
                    if (panelsPanel.panelsModel.get(x).toString().endsWith("true")) {
                        sb.append("    A hello panel must be first\n");
                        break;
                    }
        if (finish != panelsPanel.panelsModel.size() - 1)
            for (int x = finish + 1; x <= panelsPanel.panelsModel.size() - 1; x++)
                if (panelsPanel.panelsModel.get(x).toString().endsWith("true")) {
                    sb.append("    A finish panel must be last\n");
                    break;
                }
        if (panelsPanel.targetEnabled.isSelected())
            if (target > install)
                sb.append("    The install panel must be before the target panel\n");
        if (panelsPanel.packsEnabled.isSelected())
            if (pack > install)
                sb.append("    The packs panel must be before the target panel\n");
        if (panelsPanel.licenceEnabled.isSelected())
            if (lic > install)
                sb.append("    The licence panel must be before the target panel\n");
        if (panelsPanel.infoEnabled.isSelected())
            if (info > install)
                sb.append("    The info panel must be before the target panel\n");
        return sb.toString();
    }

    /**
     * Shows user errors, if there were any (Checks warnings first though)
     * @return true if there was error, false otherwise
     */
    public boolean error() {
        //Check for any warnings, if thre are, then dont go on
        if (!warnings())
            return true;
        //Gets errors related to missing fields
        StringBuffer sb = new StringBuffer();
        if (generalPanel.name.getText().trim().equals(""))
            sb.append("    General - Application Name\n");
        if (generalPanel.version.getText().trim().equals(""))
            sb.append("    General - Application Version\n");
        if (authorsPanel.authors.list.isEmpty())
            sb.append("    Authors\n");
        if (guiPanel.advancedPanel.headerOption.isSelected())
            if (guiPanel.advancedPanel.headingImage.getText().trim().equals(""))
                sb.append("    Advanced GUI - Heading Image\n");
        if (languagePanel.langs.list.isEmpty())
            sb.append("    Languages\n");
        if (panelsPanel.helloEnabled.isSelected())
            if (panelsPanel.helloPanelChoice.getSelectedItem().toString().equals("HTMLHelloPanel") && panelsPanel.htmlField.getText().trim().equals(""))
                sb.append("    Panels - HelloPanel HTML File\n");
        if (panelsPanel.infoEnabled.isSelected())
            if (panelsPanel.infoFileField.getText().trim().equals(""))
                sb.append("    Panels - Info File\n");
        if (panelsPanel.licenceEnabled.isSelected())
            if (panelsPanel.licenceFileField.getText().trim().equals(""))
                sb.append("    Panels - Licence File\n");
        if (packsPanel.packs.list.isEmpty())
            sb.append("    Packs\n");
        if (packagingPanel.multiVolumeOption.isSelected()) {
            if (packagingPanel.firstvolumefreespace.getText().trim().equals(""))
                sb.append("    Packaging - Free Space on First Volume\n");
            if (packagingPanel.volumeSize.getText().trim().equals(""))
                sb.append("    Packaging - Size of Each Volume\n");
        }
        if (!shortcutsPanel.shortcuts.list.isEmpty())
            if (shortcutsPanel.defaultName.getText().trim().equals(""))
                sb.append("    Shortcuts - Group Structure\n");
        if (userInputPanel.userInputOption.isSelected()) {
            if (userInputPanel.title.getText().trim().equals(""))
                sb.append("    User Input - Title\n");
            if (userInputPanel.quickxport.getSelectedItem().toString().startsWith("List"))
                if (userInputPanel.qxName.getText().trim().equals(""))
                    sb.append("    User Input - QuickXport Filename\n");
        }
        //Errors for duplicate pack anmes
        String packs = "";
        for (Pack p : packsPanel.packs.list)
            for (Pack p2 : packsPanel.packs.list)
                if (XMLUtils.isPack(p) && XMLUtils.isPack(p2) && p != p2 && p.pack_name.equals(p2.pack_name) && !packs.contains("    " + p.pack_name + "\n"))
                    packs += "    " + p.pack_name + "\n";
        //Errors for circular parents
        String parentMsg = "";
        for (Pack p : packsPanel.packs.list)
            if (XMLUtils.isPack(p)) {
                String s = findParentCircularReferences(p, p, new ArrayList<Pack>());
                if (s != null) {
                    s = "    " + s.substring(0, s.length() - 2);
                    if (!isDuplicate(Arrays.asList(parentMsg.split("\n")), s, true))
                        parentMsg += s + "\n";
                }
            }
        //Errors for circular depends
        StringBuffer dependsError = new StringBuffer();
        for (Pack p : packsPanel.packs.list)
            if (XMLUtils.isPack(p)) {
                findDependsCircularReferences(p.pack_name, p.pack_depends, new ArrayList<String>(), true);
                if (dependsErr != null) {
                    String errorD = "    ";
                    for (String depends : dependsErr)
                        errorD += depends + ", ";
                    errorD = errorD.substring(0, errorD.length() - 2);
                    if (!isDuplicate(Arrays.asList(dependsError.toString().split("\n")), errorD, false))
                        dependsError.append(errorD + "\n");
                    dependsErr = null;
                }
            }
        
        StringBuffer invalidError = new StringBuffer();
        if(!(generalOptionalPanel.javaVersion.getText().trim().matches("([0-9](\\.[0-9](\\.[0-9](_[0-9]+)?)?)?)?")&&generalOptionalPanel.javaVersion.getText().trim().length()<=10))
            invalidError.append("    General - Minimum Java Version\n");
        
        //Checks for all erorrs
        boolean showError = false;
        String missings = sb.toString();
        if (missings.length() != 0)
            showError = true;
        String invalid = invalidError.toString();
        if (invalid.length() != 0)
            showError = true;
        String fileErrors = getFileErrors().toString();
        if (fileErrors.length() != 0)
            showError = true;
        String ordererrors = getOrderErrors().toString();
        if (ordererrors.length() != 0)
            showError = true;
        if (packs.length() != 0)
            showError = true;
        if (parentMsg.length() != 0)
            showError = true;
        String dependsE = dependsError.toString();
        if (dependsE.length() != 0)
            showError = true;
        //Shows errors, if any exist
        if (showError) {
            //Sets up text pane, formatted
            JTextPane ta = new JTextPane();
            StyledDocument doc = ta.getStyledDocument();
            Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
            //Regular font
            Style regular = doc.addStyle("regular", def);
            //Bold font
            Style style = doc.addStyle("bold", regular);
            StyleConstants.setBold(style, true);
            //Adds the messages to show to the user
            try {
                if (missings.length() != 0) {
                    doc.insertString(doc.getLength(), "The following information is missing: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), missings, doc.getStyle("regular"));
                }
                if (invalid.length() != 0) {
                    doc.insertString(doc.getLength(), "The following field has data in an unrecognized format: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), invalid, doc.getStyle("regular"));
                }
                if (fileErrors.length() != 0) {
                    if (countLines(fileErrors) == 2)
                        doc.insertString(doc.getLength(), "The following file doesn't exist: \n", doc.getStyle("bold"));
                    else
                        doc.insertString(doc.getLength(), "The following files don't exist: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), fileErrors, doc.getStyle("regular"));
                }
                if (ordererrors.length() != 0) {
                    if (countLines(ordererrors) == 2)
                        doc.insertString(doc.getLength(), "The following pack is in the wrong order: \n", doc.getStyle("bold"));
                    else
                        doc.insertString(doc.getLength(), "The following packs are in the wrong order: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), ordererrors, doc.getStyle("regular"));
                }
                if (packs.length() != 0) {
                    if (countLines(packs) == 2)
                        doc.insertString(doc.getLength(), "You have multiple packs with the following name: \n", doc.getStyle("bold"));
                    else
                        doc.insertString(doc.getLength(), "You have multiple packs with the following names: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), packs, doc.getStyle("regular"));
                }
                if (parentMsg.length() != 0) {
                    if (countLines(parentMsg) == 2)
                        doc.insertString(doc.getLength(), "The following pack list specify how the parents are in a circular fashion: \n", doc.getStyle("bold"));
                    else
                        doc.insertString(doc.getLength(), "The following pack lists specify how their parents are in a circular fashion: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), parentMsg, doc.getStyle("regular"));
                }
                if (dependsE.length() != 0) {
                    if (countLines(dependsE) == 2)
                        doc.insertString(doc.getLength(), "The following pack list specify how the depends are in a circular fashion: \n", doc.getStyle("bold"));
                    else
                        doc.insertString(doc.getLength(), "The following pack lists specify how their depends are in a circular fashion: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), dependsE, doc.getStyle("regular"));
                }
                doc.insertString(doc.getLength(), "\nFix those errors and try again.", doc.getStyle("bold"));
            } catch (BadLocationException ex) {
                RunnerClass.logger.log(Level.SEVERE, null, ex);
            }
            ta.setEditable(false);
            ta.setOpaque(false);
            ta.setBorder(BorderFactory.createEmptyBorder());
            JScrollPane sp = new JScrollPane();
            sp.setViewportView(ta);
            sp.setOpaque(false);
            sp.setViewportBorder(BorderFactory.createEmptyBorder());
            sp.setBorder(BorderFactory.createEmptyBorder());
            //Expands 50 right to make less wrapping
            sp.setPreferredSize(new Dimension(
                    ta.getPreferredSize().width + 50 < 700 ? ta.getPreferredSize().width + 50 : 700,
                    ta.getPreferredSize().height < 500 ? ta.getPreferredSize().height : 500));
            ta.setSelectionStart(0);
            ta.setSelectionEnd(0);
            //Shows message
            JOptionPane.showMessageDialog(this, sp, "Incomplete Data", JOptionPane.ERROR_MESSAGE);
            return true;
        } else
            return false;
    }

    /**
     * Counts the amount of lines in a given String
     * @param s string to count lines in
     * @return amount of lines
     */
    public int countLines(String s) {
        char ca[] = s.toCharArray();
        if (s.length() == 0)
            return 0;
        int i = 1;
        for (char c : ca)
            if (c == '\n')
                i++;
        return i;
    }

    /**
     * Looks for circular parent references in packs
     * @param initial the pack started off with in the current circular list
     * @param p the current Pack
     * @param previous the packs which are referenced so far by other packs as parents
     * @return A string with the circular reference or null if none
     */
    private String findParentCircularReferences(Pack initial, Pack p, Collection<Pack> previous) {
        //Checks whether it has a parent
        if (!p.pack_parent.equals(""))
            //Checks whether this pack was already in the list of previously visited packs, if it is a circular reference has been found
            if (previous.contains(p))
                return p.equals(initial) ? "" : null;
            else {
                //Continues on searching for a circular reference
                previous.add(p);
                String s = findParentCircularReferences(initial, getPack(p.pack_parent), previous);
                return s != null ? p.pack_name + ", " + s : null;
            }
        return null;
    }

    /**
     * Looks for circular depends references in packs
     * @param name the initial pack name
     * @param depends the depends for the current pack
     * @param list the packs which are referenced so far
     * @param start whether this is the initial call or not
     */
    private void findDependsCircularReferences(String name, Collection<String> depends, Collection<String> list, boolean start) {
        //Adds the inital pack name
        if (start)
            list.add(name);
        //Traverses the depends
        for (String s : depends)
            //If any error has still not been found, it continues
            if (dependsErr == null)
                //Checks whether a circular reference has been found by checking if list contains s or not
                if (!list.contains(s)) {
                    //Continues searching for a circular reference if it has not been found
                    list.add(s);
                    Pack p = getPack(s);
                    findDependsCircularReferences(p.pack_name, p.pack_depends, list, false);
                } else
                    dependsErr = list;
    }

    /**
     * Gets a pack by its name (sequential search)
     * @param packName the name of the pack to search for
     * @return the Pack object corresponding to name
     */
    private Pack getPack(String packName) {
        //Traverses through the packs added
        for (Pack p : packsPanel.packs.list)
            //Checks whether the name of the pack matches the one being searched for
            if (XMLUtils.isPack(p) && p.pack_name.equals(packName))
                return p;
        return null;
    }

    /**
     * Checks whether current is already in t
     * @param t The list of strings already added
     * @param current the current string being checked if in list
     * @param parent whether this is for parent of depends
     * @return
     */
    private boolean isDuplicate(Collection<String> t, String current, boolean parent) {
        //Converts the current string to a Set by converting it to a lsit
        Set currentList = new TreeSet(Arrays.asList(current.trim().split(", ")));
        //Goes through all the elements in t
        for (String p : t) {
            //Converts the current string to a Set by converting it to a lsit
            Set list = new TreeSet(Arrays.asList(p.trim().split(", ")));
            //Does the appropriate action depending on whether it is for parent or depends
            if (parent) {
                if (currentList.containsAll(list) && list.containsAll(currentList))
                    return true;
            } else if (currentList.containsAll(list))
                return true;
        }
        return false;
    }

    /**
     * Errors related to non-existent files references
     * @return errors related to non-existent files references
     */
    public StringBuffer getFileErrors() {
        StringBuffer errors = new StringBuffer();
        if (panelsPanel.helloEnabled.isSelected() && panelsPanel.helloPanelChoice.getSelectedItem().toString().equals("HTMLHelloPanel") && !panelsPanel.htmlField.getText().trim().equals("") && !new File(panelsPanel.htmlField.getText().trim()).exists())
            errors.append("    Panels - HelloPanel HTML File\n");
        if (panelsPanel.infoEnabled.isSelected() && !panelsPanel.infoFileField.getText().trim().equals("") && !new File(panelsPanel.infoFileField.getText().trim()).exists())
            errors.append("    Panels - InfoPanel file\n");
        if (panelsPanel.licenceEnabled.isSelected() && !panelsPanel.licenceFileField.getText().trim().equals("") && !new File(panelsPanel.licenceFileField.getText().trim()).exists())
            errors.append("    Panels - LicencePanel file\n");
        for (Pack p : packsPanel.packs.list)
            if (XMLUtils.isPack(p)) {
                if (!p.pack_packImgId.equals("") && !new File(p.pack_packImgId).exists())
                    errors.append("    Pack: " + p.pack_name + " - Pack Screenshot\n");
                for (XFile f : p.file)
                    if (!new File(f.sourceFileOption ? f.packs_file_sourceFile : f.packs_file_sourceDir).exists())
                        errors.append("    Pack: " + p.pack_name + " - File: " + (f.sourceFileOption ? f.packs_file_sourceFile : f.packs_file_sourceDir) + "\n");
            }
        if (!guiPanel.langSelLogo.getText().trim().equals("") && !new File(guiPanel.langSelLogo.getText().trim()).exists())
            errors.append("    GUI - Logo image File\n");
        if (!guiPanel.sideImage.getText().trim().equals("") && !new File(guiPanel.sideImage.getText().trim()).exists())
            errors.append("    GUI - Side image File\n");
        if (guiPanel.advancedPanel.headerOption.isSelected() && !guiPanel.advancedPanel.headingImage.getText().trim().equals("") && !new File(guiPanel.advancedPanel.headingImage.getText().trim()).exists())
            errors.append("    GUI - Advanced - Header Image\n");
        return errors;
    }

    /**
     * Gets Packs with pack imgs, and with no ImgPacksPanel selected
     * @return packs with pack imgs, and with no ImgPacksPanel selected
     */
    public String getPackImgWarning() {
        StringBuffer sb = new StringBuffer();
        //Checks whether the user didn't select ImgPacksPaanel
        if (!panelsPanel.getItem("Pack").equals("ImgPacksPaneltrue"))
            //Traverses through all the packs added
            for (Pack p : packsPanel.packs.list)
                if (XMLUtils.isPack(p))
                    //Checks whether the pack has a packImgId, if it does error message is appended
                    if (!p.pack_packImgId.equals(""))
                        sb.append("    " + p.pack_name + "\n");
        return sb.toString();
    }

    /**
     * Gets packs with parents, and no TreePacksPanel selected
     * @return packs with parents, and no TreePacksPanel selected
     */
    public String getParentWarning() {
        StringBuffer sb = new StringBuffer();
        //Checks whether the user didn't select the TreePacksPanel
        if (!panelsPanel.getItem("Pack").equals("TreePacksPaneltrue"))
            //Traverses through all the packs added
            for (Pack p : packsPanel.packs.list)
                if (XMLUtils.isPack(p))
                    //Checks whether the pack has a parent, if it does error message is appended
                    if (!p.pack_parent.equals(""))
                        sb.append("    " + p.pack_name + "\n");
        return sb.toString();
    }

    /**
     * Shows warnings if any
     * @return true, if continue (no warnings, or suer chooses to ignore them), false otherwise
     */
    public boolean warnings() {
        boolean showWarning = false;
        //Gets warnings for pack img
        String imgWarn = getPackImgWarning();
        if (imgWarn.length() != 0)
            showWarning = true;
        //Gets warnings for parents
        String parentsWarn = getParentWarning();
        if (parentsWarn.length() != 0)
            showWarning = true;

        if (showWarning) {
            JTextPane ta = new JTextPane();
            StyledDocument doc = ta.getStyledDocument();
            Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
            Style regular = doc.addStyle("regular", def);
            Style style = doc.addStyle("bold", regular);
            StyleConstants.setBold(style, true);
            //Adds the warnings if there is any
            try {
                if (imgWarn.length() != 0) {
                    if (countLines(imgWarn) == 2)
                        doc.insertString(doc.getLength(), "The following pack contains a pack screenshot, but ImgPacksPanel isn't used.\n"
                                + "You will not be able to see the screenshot in the installer: \n", doc.getStyle("bold"));
                    else
                        doc.insertString(doc.getLength(), "The following packs contains pack screenshots, but ImgPacksPanel isn't used.\n"
                                + "You will not be able to see the screenshots in the installer: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), imgWarn, doc.getStyle("regular"));
                }
                if (parentsWarn.length() != 0) {
                    if (countLines(parentsWarn) == 2)
                        doc.insertString(doc.getLength(), "The following pack specifies a parent, but TreePacksPanel isn't used.\n"
                                + "You will not see the pack hierarcy in the installer: \n", doc.getStyle("bold"));
                    else
                        doc.insertString(doc.getLength(), "The following packs specify parents, but TreePacksPanel isn't used.\n"
                                + "You will not see the pack hierarcy in the installer: \n", doc.getStyle("bold"));
                    doc.insertString(doc.getLength(), parentsWarn, doc.getStyle("regular"));
                }
                doc.insertString(doc.getLength(), "\nWould you like to continue, and dismiss these warnings?", doc.getStyle("bold"));
            } catch (BadLocationException ex) {
                RunnerClass.logger.log(Level.SEVERE, null, ex);
            }
            ta.setEditable(false);
            ta.setOpaque(false);
            ta.setBorder(BorderFactory.createEmptyBorder());
            JScrollPane sp = new JScrollPane();
            sp.setViewportView(ta);
            sp.setOpaque(false);
            sp.setViewportBorder(BorderFactory.createEmptyBorder());
            sp.setBorder(BorderFactory.createEmptyBorder());
            sp.setPreferredSize(new Dimension(
                    ta.getPreferredSize().width + 50 < 700 ? ta.getPreferredSize().width + 50 : 700,
                    ta.getPreferredSize().height < 500 ? ta.getPreferredSize().height : 500));
            ta.setSelectionStart(0);
            ta.setSelectionEnd(0);

            //Gets whether user clicks yes or no, and does appropriate actions depending on what is selected
            int option = JOptionPane.showConfirmDialog(this, sp, "Incomplete Data", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.YES_OPTION)
                return true;
            else
                return false;
        } else
            return true;
    }

    /**
     * Gets the XML object representing the UI values
     * @return the xml object from the UI values
     */
    public XML getXML() {
        XML xmlObject = new XML();
        xmlObject.info_appname = generalPanel.name.getText().trim();
        xmlObject.info_appsubpath = generalOptionalPanel.subpath.getText().trim();
        xmlObject.info_appversion = generalPanel.version.getText().trim();
        xmlObject.info_javaversion = generalOptionalPanel.javaVersion.getText().trim();
        xmlObject.info_requiresjdk = generalOptionalPanel.isJdk.isSelected();
        xmlObject.info_summarylogfilepath = generalOptionalPanel.pathToLog.getText().trim();
        xmlObject.info_uninstaller_name = generalOptionalPanel.uninstallerName.getText().trim();
        xmlObject.info_uninstaller_write = generalOptionalPanel.isWriteUninstaller.isSelected();
        xmlObject.info_url = generalPanel.website.getText().trim();
        xmlObject.info_webdir = generalOptionalPanel.webInstallDir.getText().trim();
        xmlObject.info_writeinstallationinformation = generalOptionalPanel.isWriteLog.isSelected();
        xmlObject.info_pack200 = generalOptionalPanel.pack200.isSelected();
        xmlObject.info_privellegedAccess = generalOptionalPanel.privellegedAccess.isSelected();
        xmlObject.authors = authorsPanel.authors.list;
        xmlObject.guiprefs_counterOption = guiPanel.advancedPanel.counterOption.isSelected();
        xmlObject.guiprefs_headingBackgroundColor = guiPanel.advancedPanel.backgroundColour.getBackground().getRGB() + "";
        xmlObject.guiprefs_headingFontSize = guiPanel.advancedPanel.fontMultiplier.getValue().toString();
        xmlObject.guiprefs_headingForegroundColor = guiPanel.advancedPanel.fontColour.getBackground().getRGB() + "";
        xmlObject.guiprefs_headingImageOnLeftOrRight = guiPanel.advancedPanel.headingImagePosition.getSelectedItem().toString();
        xmlObject.guiprefs_headingImg = guiPanel.advancedPanel.headingImage.getText().trim();
        xmlObject.guiprefs_headingPanelCounter = guiPanel.advancedPanel.counterType.getSelectedItem().toString();
        xmlObject.guiprefs_headingPanelCounterPos = guiPanel.advancedPanel.counterPosition.getSelectedItem().toString();
        xmlObject.guiprefs_height = ((Integer) guiPanel.height.getValue()).intValue();
        xmlObject.guiprefs_resizable = guiPanel.resizable.isSelected();
        xmlObject.guiprefs_useHeadingPanel = guiPanel.advancedPanel.headerOption.isSelected();
        xmlObject.guiprefs_width = ((Integer) guiPanel.width.getValue()).intValue();
        xmlObject.lafs = guiPanel.advancedPanel.lafs.list;
        xmlObject.langselimg = guiPanel.langSelLogo.getText().trim();
        xmlObject.installerImage = guiPanel.sideImage.getText().trim();
        Collection<Locale> locales = new ArrayList<Locale>();
        //Traverses through the languages added to add them to the database
        for (String s : languagePanel.langs.list) {
            Locale locale = new Locale();
            locale.locale_langpack_default = s;
            locales.add(locale);
        }
        xmlObject.langs = locales;
        xmlObject.modifier_useFlags = languagePanel.useFlags.isSelected();
        xmlObject.modifier_langDisplayType = languagePanel.langDisplayType.getSelectedItem().toString();
        Collection<String> panels = new ArrayList();
        //Traverses through the panels, and determines whether to add them and does appropriate action
        for (int a = 0; a < panelsPanel.panelsModel.getSize(); a++)
            if (!panelsPanel.panelsModel.getElementAt(a).toString().contains("false"))
                panels.add(panelsPanel.panelsModel.getElementAt(a).toString().replace("true", ""));
        xmlObject.panels = panels;
        xmlObject.panelsModel = panelsPanel.panelsModel;
        xmlObject.resources_LicencePanel_license_src_info = panelsPanel.infoFileField.getText().trim();
        xmlObject.resources_LicencePanel_license_src_license = panelsPanel.licenceFileField.getText().trim();
        xmlObject.resources_HTMLHelloPanel_HTML = panelsPanel.htmlField.getText().trim();
        xmlObject.packs = packsPanel.packs.list;
        xmlObject.packagingOption = packagingPanel.multiVolumeOption.isSelected();
        try {
            //Does the conversion from the specified unit to bytes
            BigInteger vs = new BigInteger((packagingPanel.firstvolumefreespace.getText().trim()));
            if (packagingPanel.sizeUnit2.getSelectedItem().toString().equals("Bytes"))
                vs = vs.multiply(new BigInteger("1"));
            else if (packagingPanel.sizeUnit2.getSelectedItem().toString().equals("KB"))
                vs = vs.multiply(new BigInteger("1024"));
            else if (packagingPanel.sizeUnit2.getSelectedItem().toString().equals("MB"))
                vs = vs.multiply(new BigInteger("1048576"));
            else if (packagingPanel.sizeUnit2.getSelectedItem().toString().equals("GB"))
                vs = vs.multiply(new BigInteger("1073741824"));
            else if (packagingPanel.sizeUnit2.getSelectedItem().toString().equals("TB"))
                vs = vs.multiply(new BigInteger("1099511627776"));
            xmlObject.packaging_firstvolumefreespace = vs.toString();
        } catch (java.lang.NumberFormatException e) {
            xmlObject.packaging_firstvolumefreespace = "";
        }
        xmlObject.packaging_firstvolumefreespace_type = packagingPanel.sizeUnit2.getSelectedItem().toString();
        try {
            //Does the conversion from the specified unit to Bytes
            BigInteger vs = new BigInteger((packagingPanel.volumeSize.getText().trim()));
            if (packagingPanel.sizeUnit1.getSelectedItem().toString().equals("Bytes"))
                vs = vs.multiply(new BigInteger("1"));
            else if (packagingPanel.sizeUnit1.getSelectedItem().toString().equals("KB"))
                vs = vs.multiply(new BigInteger("1024"));
            else if (packagingPanel.sizeUnit1.getSelectedItem().toString().equals("MB"))
                vs = vs.multiply(new BigInteger("1048576"));
            else if (packagingPanel.sizeUnit1.getSelectedItem().toString().equals("GB"))
                vs = vs.multiply(new BigInteger("1073741824"));
            else if (packagingPanel.sizeUnit1.getSelectedItem().toString().equals("TB"))
                vs = vs.multiply(new BigInteger("1099511627776"));
            xmlObject.packaging_volumeSize = vs.toString();
        } catch (java.lang.NumberFormatException e) {
            xmlObject.packaging_volumeSize = "";
        }
        xmlObject.packaging_volumeSize_type = packagingPanel.sizeUnit1.getSelectedItem().toString();
        xmlObject.shortcuts = shortcutsPanel.shortcuts.list;
        xmlObject.shortcuts_defaultCurrentUser = shortcutsPanel.defautCurrentUser.isSelected();
        xmlObject.shortcuts_programGroup_defaultName = shortcutsPanel.defaultName.getText().trim();
        xmlObject.shortcuts_programGroup_location = shortcutsPanel.locationShortcut.getSelectedItem().toString();
        xmlObject.DesktopShortcutCheckboxEnabled = shortcutsPanel.DesktopShortcutCheckboxEnabled.isSelected();
        xmlObject.userInputOption = userInputPanel.userInputOption.isSelected();
        xmlObject.userInput_bold = userInputPanel.bold.isSelected();
        xmlObject.userInput_ea = userInputPanel.quickxport.getSelectedItem().toString();
        xmlObject.userInput_ea_file = userInputPanel.qxName.getText().trim();
        xmlObject.userInput_font = ((Integer) userInputPanel.fontSize.getValue()).intValue();
        xmlObject.userInput_italics = userInputPanel.italics.isSelected();
        xmlObject.userInput_title = userInputPanel.title.getText().trim();
        xmlObject.userInputs = userInputPanel.userInput.list;
        xmlObject.process_logFileOption = processesPanel.logFileOption.isSelected();
        Collection<String> logFileModel = new ArrayList<String>();
        //Traverses through the logFilePath ComboBoxModel and adds them to logFileModel
        for (int a = 0; a < processesPanel.logFilePath.getModel().getSize(); a++)
            logFileModel.add(processesPanel.logFilePath.getModel().getElementAt(a).toString());
        xmlObject.process_logFileModel = logFileModel;
        xmlObject.process_logFilePath = processesPanel.logFilePath.getSelectedItem().toString();
        xmlObject.processes = processesPanel.process.list;
        return xmlObject;
    }

    /**
     * Sets the UI to reflect the xml object
     * @param xmlObject the xml object to use to set UI values
     */
    public void setXML(XML xmlObject) {
        generalPanel.name.setText(xmlObject.info_appname);
        generalOptionalPanel.subpath.setText(xmlObject.info_appsubpath);
        generalPanel.version.setText(xmlObject.info_appversion);
        generalOptionalPanel.javaVersion.setText(xmlObject.info_javaversion);
        generalOptionalPanel.isJdk.setSelected(xmlObject.info_requiresjdk);
        generalOptionalPanel.pathToLog.setText(xmlObject.info_summarylogfilepath);
        generalOptionalPanel.uninstallerName.setText(xmlObject.info_uninstaller_name);
        generalOptionalPanel.isWriteUninstaller.setSelected(xmlObject.info_uninstaller_write);
        generalPanel.website.setText(xmlObject.info_url);
        generalOptionalPanel.webInstallDir.setText(xmlObject.info_webdir);
        generalOptionalPanel.isWriteLog.setSelected(xmlObject.info_writeinstallationinformation);
        generalOptionalPanel.pack200.setSelected(xmlObject.info_pack200);
        generalOptionalPanel.privellegedAccess.setSelected(xmlObject.info_privellegedAccess);
        authorsPanel.authors.setList((ArrayList<Author>) xmlObject.authors);
        guiPanel.advancedPanel.counterOption.setSelected(xmlObject.guiprefs_counterOption);
        guiPanel.advancedPanel.backgroundColour.setBackground(new Color(Integer.parseInt(xmlObject.guiprefs_headingBackgroundColor)));
        guiPanel.advancedPanel.fontMultiplier.setValue(new Integer(xmlObject.guiprefs_headingFontSize));
        guiPanel.advancedPanel.fontColour.setBackground(new Color(Integer.parseInt(xmlObject.guiprefs_headingForegroundColor)));
        guiPanel.advancedPanel.headingImagePosition.setSelectedItem(xmlObject.guiprefs_headingImageOnLeftOrRight);
        guiPanel.advancedPanel.headingImage.setText(xmlObject.guiprefs_headingImg);
        guiPanel.advancedPanel.counterType.setSelectedItem(xmlObject.guiprefs_headingPanelCounter);
        guiPanel.advancedPanel.counterPosition.setSelectedItem(xmlObject.guiprefs_headingPanelCounterPos);
        guiPanel.height.setValue(new Integer(xmlObject.guiprefs_height));
        guiPanel.resizable.setSelected(xmlObject.guiprefs_resizable);
        guiPanel.advancedPanel.headerOption.setSelected(xmlObject.guiprefs_useHeadingPanel);
        guiPanel.width.setValue(new Integer(xmlObject.guiprefs_width));
        guiPanel.advancedPanel.lafs.setList((ArrayList<Laf>) xmlObject.lafs);
        guiPanel.langSelLogo.setText(xmlObject.langselimg);
        guiPanel.sideImage.setText(xmlObject.installerImage);
        Collection<String> locales = new ArrayList<String>();
        //Traverses through the languages in the database to add them to the GUI
        for (Locale l : xmlObject.langs)
            locales.add(l.locale_langpack_default);
        languagePanel.langs.setList((ArrayList<String>) locales);
        languagePanel.useFlags.setSelected(xmlObject.modifier_useFlags);
        languagePanel.langDisplayType.setSelectedItem(xmlObject.modifier_langDisplayType);
        panelsPanel.panelsModel.removeAllElements();
        //Traverses through the panels model and adds them
        for (int a = 0; a < xmlObject.panelsModel.size(); a++)
            panelsPanel.panelsModel.addElement(xmlObject.panelsModel.getElementAt(a));
        panelsPanel.infoFileField.setText(xmlObject.resources_LicencePanel_license_src_info);
        panelsPanel.licenceFileField.setText(xmlObject.resources_LicencePanel_license_src_license);
        panelsPanel.htmlField.setText(xmlObject.resources_HTMLHelloPanel_HTML);
        packsPanel.packs.setList((ArrayList<Pack>) xmlObject.packs);
        packagingPanel.multiVolumeOption.setSelected(xmlObject.packagingOption);
        try {
            //Does the appropriate conversion from bytes to the specified unit
            BigInteger vs = new BigInteger(xmlObject.packaging_firstvolumefreespace);
            if (xmlObject.packaging_firstvolumefreespace_type.equals("Bytes"))
                vs = vs.divide(new BigInteger("1"));
            else if (xmlObject.packaging_firstvolumefreespace_type.equals("KB"))
                vs = vs.divide(new BigInteger("1024"));
            else if (xmlObject.packaging_firstvolumefreespace_type.equals("MB"))
                vs = vs.divide(new BigInteger("1048576"));
            else if (xmlObject.packaging_firstvolumefreespace_type.equals("GB"))
                vs = vs.divide(new BigInteger("1073741824"));
            else if (xmlObject.packaging_firstvolumefreespace_type.equals("TB"))
                vs = vs.divide(new BigInteger("1099511627776"));
            packagingPanel.firstvolumefreespace.setText(vs.toString());
        } catch (java.lang.NumberFormatException e) {
            packagingPanel.firstvolumefreespace.setText("");
        }
        packagingPanel.sizeUnit2.setSelectedItem(xmlObject.packaging_firstvolumefreespace_type);
        try {
            //Does the appropriate conversion from bytes to the specified unit
            BigInteger vs = new BigInteger(xmlObject.packaging_volumeSize);
            if (xmlObject.packaging_volumeSize_type.equals("Bytes"))
                vs = vs.divide(new BigInteger("1"));
            else if (xmlObject.packaging_volumeSize_type.equals("KB"))
                vs = vs.divide(new BigInteger("1024"));
            else if (xmlObject.packaging_volumeSize_type.equals("MB"))
                vs = vs.divide(new BigInteger("1048576"));
            else if (xmlObject.packaging_volumeSize_type.equals("GB"))
                vs = vs.divide(new BigInteger("1073741824"));
            else if (xmlObject.packaging_volumeSize_type.equals("TB"))
                vs = vs.divide(new BigInteger("1099511627776"));
            packagingPanel.volumeSize.setText(vs.toString());
        } catch (java.lang.NumberFormatException e) {
            packagingPanel.volumeSize.setText("");
        }
        packagingPanel.sizeUnit1.setSelectedItem(xmlObject.packaging_volumeSize_type);
        shortcutsPanel.shortcuts.setList((ArrayList<Shortcut>) xmlObject.shortcuts);
        shortcutsPanel.defautCurrentUser.setSelected(xmlObject.shortcuts_defaultCurrentUser);
        shortcutsPanel.defaultName.setText(xmlObject.shortcuts_programGroup_defaultName);
        shortcutsPanel.locationShortcut.setSelectedItem(xmlObject.shortcuts_programGroup_location);
        shortcutsPanel.DesktopShortcutCheckboxEnabled.setSelected(xmlObject.DesktopShortcutCheckboxEnabled);
        userInputPanel.userInputOption.setSelected(xmlObject.userInputOption);
        userInputPanel.bold.setSelected(xmlObject.userInput_bold);
        userInputPanel.quickxport.setSelectedItem(xmlObject.userInput_ea);
        userInputPanel.qxName.setText(xmlObject.userInput_ea_file);
        userInputPanel.fontSize.setValue(new Integer(xmlObject.userInput_font));
        userInputPanel.italics.setSelected(xmlObject.userInput_italics);
        userInputPanel.title.setText(xmlObject.userInput_title);
        userInputPanel.userInput.setList((ArrayList<UserInput>) xmlObject.userInputs);
        processesPanel.logFileOption.setSelected(xmlObject.process_logFileOption);
        processesPanel.logFilePath.setModel(new DefaultComboBoxModel(xmlObject.process_logFileModel.toArray()));
        processesPanel.logFilePath.setSelectedItem(xmlObject.process_logFilePath);
        processesPanel.process.setList((ArrayList<XProcess>) xmlObject.processes);
        //Adjusts the GUI depending on the values
        guiPanel.advancedPanel.adjustGUI();
        packagingPanel.adjustGUI();
        userInputPanel.adjustGUI();
        processesPanel.adjustGUI();
        panelsPanel.adjustGUI();
        //Sets the minimum size
        setMinimumSize(getPreferredSize());
    }

    /**
     * Overrides pack to pack and set the minimum size
     */
    @Override
    public void pack() {
        //Whenever pack is called, it means its a pj resize
        System.out.println("mf pack");
        pjResized = true;
        super.pack();
        setMinimumSize(getPreferredSize());
    }

    /**
     * Forcibly stops any code from changing the close operation, as it was too difficult to go through all code and delete any changes.
     * @param i close operator
     */
    @Override
    public void setDefaultCloseOperation(int i) {
    }
}
