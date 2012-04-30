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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import packjacket.RunnerClass;
import packjacket.gui.create.ProgressFrame;

/**
 * The following code is a conversion of the phyton code for izpack2exe from Izpack utilities to java
 * Wraps a JAR in a LZMA-ed EXE
 * @author Manodasan Wingarajah
 */
public class Izpack2Exe {

    private File jarF, exeF;
    private ProgressFrame fr;
    private boolean crashed, stopped;
    private Process p;

    /**
     * Creates the task
     * @param jarF input jar file
     * @param exeF output exe file
     * @param fr frame showing progress
     */
    public Izpack2Exe(File jarF, File exeF, ProgressFrame fr) {
        //Sets the values
        this.jarF = jarF;
        this.exeF = exeF;
        this.fr = fr;
    }

    /**
     * Generates the exe
     * @throws IOException When an IOException occurs, it is thrown
     */
    public void generateExe() throws IOException {
        //Deletes config and 7z files, just in case they are left over, as it will error if the 7z file already exists
        new File(System.getProperty("java.io.tmpdir") + "/config.txt").delete();
        new File(System.getProperty("java.io.tmpdir") + "/installer.7z").delete();
        //Runs 7za
        if (System.getProperty("os.name").startsWith("Windows"))
            p = Runtime.getRuntime().exec(new String[]{RunnerClass.homedir + "utils/wrappers/izpack2exe/7za.exe", "a", "-t7z", "-mx=9", "-ms=off", System.getProperty("java.io.tmpdir") + "/installer.7z", jarF.getAbsolutePath()}, null, new File(RunnerClass.homedir + "utils/wrappers/izpack2exe"));
        else
            p = Runtime.getRuntime().exec(new String[]{"7za", "a", "-t7z", "-mx=9", "-ms=off", System.getProperty("java.io.tmpdir") + "/installer.7z", jarF.getAbsolutePath()}, null, new File(RunnerClass.homedir + "utils/wrappers/izpack2exe"));
        //Gets streams
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
                    if (crashed)
                        fr.crash();
                    jarF.delete();
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
        //Catch is empty because when process is killed, thats good
        try {
            p.waitFor();
        } catch (InterruptedException ex) {
        }
        //If the creation of the exe has already been cancelled, it doesn't continue
        if (stopped)
            return;
        //Writes out config file, is always the same, except for the input file, which can be different, but never actually is
        PrintWriter out = new PrintWriter(new FileWriter(System.getProperty("java.io.tmpdir") + "/config.txt"));
        out.println(";!@Install@!UTF-8!");
        out.println("Title=\"Installer\"");
        out.println("InstallPath=\"%temp%\\\\packjacket-installer\"");
        out.println("ExtractDialogText=\"Extracting installer\"");
        out.println("Progress=\"yes\"");
        out.println("GUIFlags=\"4+32\"");
        out.println("GUIMode=\"1\"");
        out.println("ExecuteFile=\"" + jarF.getName() + "\"");
        out.println(";!@InstallEnd@!");
        out.close();

        //Deletes output file if it already exists, cause we are appending
        exeF.delete();
        //Writes three files consecutively
        FileOutputStream outFile = new FileOutputStream(new File(exeF.getAbsolutePath()), true);
        writeFile(new FileInputStream(new File(RunnerClass.homedir + "utils/wrappers/izpack2exe/7zS.sfx")), outFile);
        writeFile(new FileInputStream(new File(System.getProperty("java.io.tmpdir") + "/config.txt")), outFile);
        writeFile(new FileInputStream(new File(System.getProperty("java.io.tmpdir") + "/installer.7z")), outFile);
        outFile.close();
        fr.setPB(100);
        fr.taskPB.setIndeterminate(false);
        fr.taskMsg.setText("Completed");
        //Deletes config and 7z file, as will error next time if they exist
        new File(System.getProperty("java.io.tmpdir") + "/config.txt").delete();
        new File(System.getProperty("java.io.tmpdir") + "/installer.7z").delete();
    }

    /**
     * Gets data from input file, and appends it to output
     * @param inFile the input file to read from
     * @param outFile the output file to write to
     * @throws IOException When an IOException occurs, it is thrown
     */
    private void writeFile(FileInputStream inFile, FileOutputStream outFile) throws IOException {
        //Buffer is 2 kilobytes
        byte[] buf = new byte[2048];
        int read;
        //Reads buffering, and stops if should
        while ((read = inFile.read(buf)) > 0 && !stopped)
            outFile.write(buf, 0, read);
        inFile.close();
    }

    /**
     * Stops the process
     */
    public void stop() {
        stopped = true;
        //If the process already started it is destroyed
        if (p != null)
            p.destroy();
        //Deletes config and 7z file, as will error next time if they exist
        new File(System.getProperty("java.io.tmpdir") + "/config.txt").delete();
        new File(System.getProperty("java.io.tmpdir") + "/installer.7z").delete();
    }
}
