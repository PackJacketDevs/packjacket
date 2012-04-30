

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Main {

    public static void copy(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            copyDirectory(src, dest);
        } else {
            copyFile(src, dest);
        }
    }

    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        for (File f : srcDir.listFiles()) {
            String filePath = f.getAbsolutePath();
            filePath = filePath.replace(srcDir.getAbsolutePath(), destDir.getAbsolutePath());
            copy(f, new File(filePath));
        }
    }

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

    public static void deleteFiles(File toDelete) {
        if (toDelete.isDirectory()) {
            for (File f : toDelete.listFiles()) {
                deleteFiles(f);
            }
        }
        toDelete.delete();
    }

    public static void main(String[] paramArrayOfString) throws IOException {
        String str = System.getProperty("user.home") + "/.packjacket/";

        if (new File(str).exists()) {
            deleteFiles(new File(str));
        }

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
        for (File localFile : new File(str + "launch4j/bin/").listFiles()) {
            localFile.setExecutable(true);
        }
    }
}