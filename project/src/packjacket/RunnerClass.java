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
package packjacket;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.x4juli.handlers.RollingFileHandler;
import org.x4juli.handlers.rolling.FixedWindowRollingPolicy;
import org.x4juli.handlers.rolling.SizeBasedTriggeringPolicy;
import packjacket.gui.MainFrame;

/**
 * Initializes a logger, and calls to set up the GUI.
 * @author Amandeep Grewal
 * @author Manodasan Wignarajah
 */
public class RunnerClass {

    public static MainFrame mf;
    public static String homedir = System.getProperty("user.home") + "/.packjacket/";
    public static Logger logger;
    public static RollingFileHandler nfh;
    public static String VERSION = "0.5";

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        try {
            //Sets up logger
            logger = Logger.getLogger("packjacket");
            nfh = new RollingFileHandler("pj2file");
            //Rolls to a new log file, when triggered
            FixedWindowRollingPolicy rp = new FixedWindowRollingPolicy(homedir + "pj%i.log.gz");
            rp.setMinIndex(1);
            rp.setMaxIndex(2);
            rp.activateOptions();
            //Trigger when max size reached
            SizeBasedTriggeringPolicy tp = new SizeBasedTriggeringPolicy(5242880);
            tp.activateOptions();
            nfh.setRollingPolicy(rp);
            nfh.setTriggeringPolicy(tp);
            nfh.setAppend(true);
            nfh.setBufferedIO(true);
            nfh.setBufferSize(8);
            nfh.setFile(homedir + "pj.log");
            nfh.setFormatter(new SimpleFormatter());
            nfh.activateOptions();
            logger.addHandler(nfh);
            //Give some user info
            logger.info("\n"
                    + "--------------------------------- NEW LOGFILE ---------------------------------\n"
                    + "os.name: " + System.getProperty("os.name") + "\n"
                    + "os.arch: " + System.getProperty("os.arch") + "\n"
                    + "os.version: " + System.getProperty("os.version") + "\n"
                    + "java.version: " + System.getProperty("java.version") + "\n"
                    + "java.vendor: " + System.getProperty("java.vendor") + "\n"
                    + "PackJacket.version: " + VERSION);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        //Set LAF
        try {
            String laf = UIManager.getSystemLookAndFeelClassName();
            boolean useNimbus = false;
            if (laf.equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel") || laf.equals(UIManager.getCrossPlatformLookAndFeelClassName()))
                for (LookAndFeelInfo li : UIManager.getInstalledLookAndFeels())
                    if (li.getClassName().equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel")) {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                        useNimbus = true;
                        break;
                    }
            if (!useNimbus)
                UIManager.setLookAndFeel(laf);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    mf = new MainFrame();
                    mf.setVisible(true);
                    //If pjc passed in, open it
                    if (args.length != 0)
                        mf.open(new File(args[0]), false);
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
