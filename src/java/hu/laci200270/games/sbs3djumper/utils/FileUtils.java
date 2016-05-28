package hu.laci200270.games.sbs3djumper.utils;

import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.ResourceLocation;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Laci on 2016. 01. 23..
 */
public class FileUtils {
    public static void unzipIt(File zipFile, File outputFolder) throws ZipException {


        ZipFile file = new ZipFile(zipFile);
        file.extractAll(outputFolder.getAbsolutePath());

    }

    public static void unzipIt(InputStream stream, File outputFolder) throws IOException {

        outputFolder.mkdirs();
        ZipInputStream zin = new ZipInputStream(stream);
        ZipEntry currentEntry = null;
        currentEntry = zin.getNextEntry();
        while (currentEntry != null) {
            if (currentEntry.isDirectory())
                new File(outputFolder, currentEntry.getName()).mkdirs();
            else {
                FileOutputStream fos = new FileOutputStream(new File(outputFolder, currentEntry.getName()));
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fos.write(c);
                }
                fos.close();
                zin.closeEntry();
            }
            zin.close();
        }

    }

    public static File getTempDir() {
        File tempDir = new File(getRootDir(), "temp");
        tempDir.mkdirs();
        return tempDir;
    }

    public static File getRootDir() {
        File rootDir = new File(System.getProperty("java.user.home"), String.format("%s%s%s", Constants.devName, File.separator, Constants.gameCodeName));
        rootDir.mkdirs();

        return rootDir;
    }

    public static String readFile(File file) throws IOException {
        String str = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        while (line != null) {
            str += line + '\n';
            line = reader.readLine();
        }

        return str;
    }

    public static List<String> readAllLines(ResourceLocation loc) {
        List<String> returnable=new ArrayList<>();
        BufferedReader reader=new BufferedReader(new InputStreamReader(loc.getInputStream()));
        String line= null;
        try {
            line = reader.readLine();

        while(line!=null){
            returnable.add(line);
            line=reader.readLine();
        }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnable;
    }
}
