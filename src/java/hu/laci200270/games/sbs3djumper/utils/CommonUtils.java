package hu.laci200270.games.sbs3djumper.utils;

import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.world.Bunny;
import hu.laci200270.games.sbs3djumper.world.EnumLightType;
import hu.laci200270.games.sbs3djumper.world.Light;
import hu.laci200270.games.sbs3djumper.world.World;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.codec.digest.DigestUtils;
import org.joml.Vector3f;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Laci on 2016. 01. 23..
 */
public class CommonUtils {
    public static String generateLwjglName() {
        return String.format("lwjgl-%s", Constants.lwjgl_version);
    }

    public static void setupLwjgl(){
        if(Constants.lwjgl_zip.exists()){
            FileInputStream fis;
            try {
                fis = new FileInputStream(Constants.lwjgl_zip);
                boolean isGood=DigestUtils.md5Hex(fis).equals(Constants.lwjglMd5);
                fis.close();
                if(!isGood) {
                    System.out.println("LWJGL zip checksum isn't same as in the code.");
                    System.out.println("Redownloading.");
                    downloadLwjgl();

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            try {
                System.out.println("LWJGL not present.");
                downloadLwjgl();
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            FileUtils.unzipIt(Constants.lwjgl_zip,Constants.lwjgl_dir);
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    public static void downloadLwjgl() throws IOException {
        System.out.println(String.format("Downloading LWJGL from %s to %s",Constants.lwjgl_url,Constants.lwjgl_zip.getAbsolutePath()));
        WebUtils.downloadFile(Constants.lwjgl_url,Constants.lwjgl_zip);
        System.out.println("LWJGL downloaded.");
    }


    public static void fillWorldWithRandom(World world, int bunnyCount, int lightCount, int range) {
        for (int i = 0; i < bunnyCount; i++) {
            Bunny bunny = new Bunny();
            bunny.setWorldPos(new Vector3f(Constants.random.nextFloat() * range, Constants.random.nextFloat() * range, Constants.random.nextFloat() * range));
            bunny.setScaling(new Vector3f(Constants.random.nextFloat(), Constants.random.nextFloat(), Constants.random.nextFloat()));
            world.addWorldPart(bunny);
        }
        for (int i = 0; i < lightCount; i++)
            world.addLight(new Light(new Vector3f(Constants.random.nextFloat(), Constants.random.nextFloat(), Constants.random.nextFloat()), new Vector3f(Constants.random.nextFloat() * getRandomMultiplier(range), Constants.random.nextFloat() * getRandomMultiplier(range), Constants.random.nextFloat() * getRandomMultiplier(range)), Float.MAX_VALUE, EnumLightType.POINT));

    }

    public static int getRandomMultiplier(int range) {
        if (Constants.random.nextBoolean())
            range = -range;
        return range;
    }

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static boolean is64BitOs(){
        return System.getProperty("sun.arch.data.model").contains("64");
    }

    public static String genSystemInfo(){
        String bitText=is64BitOs() ? "64" : "32";
        return String.format("%s bit %s OS",bitText,getOsName());
    }


}
