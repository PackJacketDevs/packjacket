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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import packjacket.RunnerClass;
import packjacket.gui.create.ProgressFrame;

/**
 * Creates a JAR using IzPack
 * @author Amandeep Grewal
 */
public class JarTask implements Task {

    private File jarF;
    private String compression;
    private ProgressFrame fr;
    private Process p;
    private boolean crashed;
    private boolean outOfMemoryError;

    /**
     * Creates the tasks
     * @param jarF output jar file
     * @param compression type of compression to use
     * @param fr frame showing progress
     */
    public JarTask(File jarF, String compression, ProgressFrame fr) {
        this.jarF = jarF;
        this.compression = compression;
        this.fr = fr;
        outOfMemoryError = false;
        crashed = false;
    }

    /**
     * Determines the amount of memory to allocate to Java using the -Xmx paramater without crashing it
     * @param testmem The starting amount of memory (MB) to test
     * @return amount of memory (MB) to use
     * @throws IOException
     */
    public long getXmx(long testMem) throws IOException {
        if (testMem < 0)
            return testMem;
        //Run java with the test memory
        final Process testRun = Runtime.getRuntime().exec(new String[]{"java", "-Xmx" + testMem + "m"});
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    testRun.destroy();
                    RunnerClass.logger.info("Destroyed memory test process, after timout");
                } catch (InterruptedException ex) {
                }
            }
        });
        th.start();
        //Read first line (used to check if it erroed)
        String line = new BufferedReader(new InputStreamReader(testRun.getInputStream())).readLine();
        String lineE = new BufferedReader(new InputStreamReader(testRun.getErrorStream())).readLine();
        //Destroy, as the other lines aren't useful
        testRun.destroy();
        th.interrupt();
        RunnerClass.logger.info("Line for memory test: " + line);
        RunnerClass.logger.info("LineE for memory test: " + lineE);
        //Check if error occured
        if ((line == null && lineE == null) || (line != null && line.equals("Error occurred during initialization of VM") || (lineE != null && lineE.equals("Could not create the Java virtual machine.")))) {
            RunnerClass.logger.info(testMem + " was too much memory.");
            return getXmx(testMem - 100);
        } else
            return testMem;
    }

    /**
     * Creates jar
     * @throws IOException
     */
    @Override
    public void go() throws IOException {
        fr.overallMsg.setText("Creating JAR");
        long memMeg;
        memMeg = ((com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        memMeg /= 1024;
        memMeg /= 1024;
        memMeg *= 0.85;
        memMeg = getXmx(memMeg);
        RunnerClass.logger.info("JarTask memory: " + memMeg);
        if (memMeg < 0) {
            fr.crash("memory below 0");
            return;
        }
        //Starts creating JAR using IzPack
        p = Runtime.getRuntime().exec(new String[]{"java", "-Xmx" + memMeg + "m", "-jar", RunnerClass.homedir + "standalone-compiler.jar", "xml.xml", "-o", jarF.getAbsolutePath(), "-c", compression}, null, new File(RunnerClass.homedir));
        //Gets output streams
        final BufferedReader inps = new BufferedReader(new InputStreamReader(p.getInputStream()));
        final BufferedReader errs = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        //Read normal output stream
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fr.taskMsg.setText("Initializing");
                    String c;
                    long l = System.currentTimeMillis();
                    int numPacks = 0;
                    while ((c = inps.readLine()) != null) {
                        RunnerClass.logger.info((System.currentTimeMillis() - l) + " Input: " + c);
                        l = System.currentTimeMillis();
                        //Shows different messages to user depending on what IzPack is doing
                        if (c.startsWith("Adding")) {
                            fr.setPB(15);
                            fr.taskMsg.setText("Adding resources");
                        } else if (c.startsWith("Writing"))
                            if (c.endsWith(" Packs into installer") || c.endsWith(" Pack into installer"))
                                numPacks = Integer.parseInt(c.replaceFirst("Writing ", "").replaceFirst(" Packs into installer", "").replaceFirst(" Pack into installer", "")); //Changes progress depending on what pack we are currently on, and how many packs to write in total
                            else {
                                int atPack = Integer.parseInt(c.charAt(13) + "");
                                double percD = (double) atPack / (double) numPacks;
                                fr.setPB(30 + (int) (percD * 70));
                                fr.taskMsg.setText("Adding" + c.split(":")[1]);
                            }
                    }
                    if (crashed) {
                        String err = "";
                        if (outOfMemoryError)
                            err = "outOfMemoryError";
                        fr.crash(err);
                    }
                    fr.setPB(100);
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
                        if (c.contains("OutOfMemoryError"))
                            outOfMemoryError = true;
                        if (!(c.contains("Pack200Logger warning") || c.contains("WARNING: skipping")))
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
     * Stops tasks
     */
    @Override
    public void stop() {
        //Kills process, if it was ever running
        if (p != null)
            p.destroy();
        //Deletes output file, just in case it started to be created
        jarF.delete();
    }
}
