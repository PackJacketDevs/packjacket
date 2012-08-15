/*
 * PackJacket - GUI frontend to IzPack to make Java-based installers
 * Copyright (C) 2008 - 2012  Amandeep Grewal, Manodasan Wignarajah
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Sets up PackJacket resource files
 * @author Amandeep Grewal
 * @author Manodasan Wignarajah
 */
public class Main {

    /*
     * Determines whether the file a directory, or file and calls appropriate helper function
     * @param src source file to copy from
     * @param dest destination file to copy to
     * @throws IOException 
     */
    public static void copy(File src, File dest) throws IOException {
        // Check for directory, or file
        if (src.isDirectory()) {
            copyDirectory(src, dest);
        } else {
            copyFile(src, dest);
        }
    }

    /**
     * Copies specified source directory to destination directory
     * @param srcDir the source directory to copy
     * @param destDir the destination directory to copy to
     * @throws IOException
     */
    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        destDir.mkdirs();
        for (File f : srcDir.listFiles()) {
            String filePath = f.getAbsolutePath();
            filePath = filePath.replace(srcDir.getAbsolutePath(), destDir.getAbsolutePath());
            copy(f, new File(filePath));
        }
    }

    /**
     * Copies specified file from source to destination
     * @param srcFile the source file to copy
     * @param destFile the destination to copy file to
     * @throws IOException 
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(srcFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();

            long count = 0;
            long size = source.size();
            while ((count += destination.transferFrom(source, count, size - count)) < size);
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    /**
     * Recursively deletes all file if directory, or deletes file
     * @param toDelete file to delete
     */
    public static void deleteFiles(File toDelete) {
        // checks if file is directory to recursively delete
        if (toDelete.isDirectory()) {
            for (File f : toDelete.listFiles()) {
                deleteFiles(f);
            }
        }
        
        // deletes file
        toDelete.delete();
    }

    /**
     * Main function - deletes existing resource files, and copies new ones
     * @param paramArrayOfString contains directory to copy files from
     * @throws IOException 
     */
    public static void main(String[] paramArrayOfString) throws IOException {
        String str = System.getProperty("user.home") + "/.packjacket/";

        // deletes existing files
        if (new File(str).exists()) {
            deleteFiles(new File(str));
        }

        // copies new resource files
        new File(str + "launch4j").mkdirs();
        copy(new File(paramArrayOfString[0] + "standalone-compiler.jar"), new File(str + "standalone-compiler.jar"));
        copy(new File(paramArrayOfString[0] + "launch4j.xml"), new File(str + "launch4j.xml"));
        copy(new File(paramArrayOfString[0] + "utils"), new File(str + "utils"));
        copy(new File(paramArrayOfString[0] + "a.jar"), new File(str + "a.jar"));
        new File(str + "utils").setWritable(true, false);

        for (File localFile : new File(paramArrayOfString[0] + "l4j").listFiles()) {
            copy(localFile, new File(str + "launch4j/" + localFile.getName()));
        }
        for (File localFile : new File(paramArrayOfString[0] + "l4j-common").listFiles()) {
            copy(localFile, new File(str + "launch4j/" + localFile.getName()));
        }
        
        // makes required resource files executable
        for (File localFile : new File(str + "launch4j/bin/").listFiles()) {
            localFile.setExecutable(true);
        }
    }
}