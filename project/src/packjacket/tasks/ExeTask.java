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
package packjacket.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import packjacket.RunnerClass;
import packjacket.gui.create.ProgressFrame;

/**
 * Task to create EXE using launch4j (&zip also done here, but essentially delegated to Izpack2Exe
 * @author Amandeep Grewal
 */
public class ExeTask implements Task {

    private File jarF, exeF;
    private ProgressFrame fr;
    private boolean useizpack2exe;
    private Process p;
    private boolean crashed;
    private Izpack2Exe izpack2Exe;

    /**
     * Creates a task
     * @param jarF the jar file to create exe from
     * @param exeF the destination of exe file
     * @param fr progress frame used to show progress
     * @param useizpack2exe true to use 7zip, false if launch4j
     */
    public ExeTask(File jarF, File exeF, ProgressFrame fr, boolean useizpack2exe) {
        this.jarF = jarF;
        this.exeF = exeF;
        this.fr = fr;
        this.useizpack2exe = useizpack2exe;
    }

    /**
     * Changes the launch4j.xml to specify destination exe file
     * @throws IOException
     */
    public void changeXMLFile() throws IOException {
        File orig = new File(RunnerClass.homedir + "launch4j.xml");
        File custom = new File(RunnerClass.homedir + "launch4j2.xml");
        BufferedReader br = new BufferedReader(new FileReader(orig));
        PrintWriter pw = new PrintWriter(new FileWriter(custom));
        int line = 0;
        String s;
        //Reads all lines, and replaces 5th line with new destination file
        while ((s = br.readLine()) != null) {
            line++;
            if (line == 5)
                pw.println("  <outfile>" + exeF.getCanonicalPath() + "</outfile>");
            else if(line == 18)
                pw.println("    <minVersion>"+ getFullVersion(RunnerClass.mf.generalOptionalPanel.javaVersion.getText().trim()) +"</minVersion>");
            else
                pw.println(s);
        }
        br.close();
        pw.close();
        //Deletes original file, renames temp to original
        RunnerClass.logger.info("del: " + orig.delete());
        RunnerClass.logger.info("ren: " + custom.renameTo(orig));
    }
    
    private String getFullVersion(String version)
    {
        if(version.equals(""))
            return "1.0.0";
        else if(version.matches("([0-9])"))
            return version+".0.0";
        else if(version.matches("([0-9]\\.[0-9])"))
            return version+".0";
        else
            return version;
    }

    /**
     * Create the EXE
     * @throws IOException
     */
    @Override
    public void go() throws IOException {
        //Show user status msg
        fr.overallMsg.setText("Creating EXE");

        //If using 7zip, use Izpack2Exe class, and return
        if (useizpack2exe) {
            izpack2Exe = new Izpack2Exe(jarF, exeF, fr);
            izpack2Exe.generateExe();
            return;
        } else {
            changeXMLFile();
            p = Runtime.getRuntime().exec(new String[]{"java", "-jar", RunnerClass.homedir + "launch4j/launch4j.jar", RunnerClass.homedir + "/launch4j.xml"}, null, new File(RunnerClass.homedir));
        }
        //Gets output streams
        final BufferedReader inps = new BufferedReader(new InputStreamReader(p.getInputStream()));
        final BufferedReader errs = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        //Read normal output stream
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fr.taskMsg.setText("Compressing");
                    fr.taskPB.setIndeterminate(true);
                    String c;
                    long l = System.currentTimeMillis();
                    while ((c = inps.readLine()) != null) {
                        RunnerClass.logger.info((System.currentTimeMillis() - l) + " Input: " + c);
                        l = System.currentTimeMillis();
                    }
                    //Check for crash
                    if (crashed)
                        fr.crash();
                    fr.taskPB.setIndeterminate(false);
                    fr.setPB(100);
                    jarF.delete();
                    fr.taskMsg.setText("Completed");
                } catch (IOException ex) {
                    RunnerClass.logger.log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        //Read error stream
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String c;
                    //Stop when anything comes into this stream, meaning error occurred
                    while ((c = errs.readLine()) != null) {
                        crashed = true;
                        RunnerClass.logger.severe("Error: " + c);
                    }
                } catch (IOException ex) {
                    RunnerClass.logger.log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        //Wait for the process to end
        //Catch is empty because when process is killed, tahts good
        try {
            p.waitFor();
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Stop the task
     */
    @Override
    public void stop() {
        //Kill processes
        if (useizpack2exe) {
            if (izpack2Exe != null)
                izpack2Exe.stop();
        } else if (p != null)
            p.destroy();
        //Delete input and output files
        jarF.delete();
        exeF.delete();
    }
}
