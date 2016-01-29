package hu.laci200270.games.sbs3djumper.utils;

import hu.laci200270.games.sbs3djumper.Constants;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

/**
 * Created by Laci on 2016. 01. 23..
 */
public class FileUtils {
    public static void unzipIt(File zipFile, File outputFolder) {

        try {
            ZipFile file = new ZipFile(zipFile);
            file.extractAll(outputFolder.getAbsolutePath());
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static File getTempDir() {
        File tempDir = new File(getRootDir(), "temp");
        tempDir.mkdirs();
        return tempDir;
    }

    public static File getRootDir() {
        File rootDir = new File(System.getProperty("java.user.home"), String.format("%s/%s", Constants.devName,Constants.gameCodeName));
        rootDir.mkdirs();

        return rootDir;
    }
}
