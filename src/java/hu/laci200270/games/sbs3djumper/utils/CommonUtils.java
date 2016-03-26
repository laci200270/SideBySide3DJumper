package hu.laci200270.games.sbs3djumper.utils;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.world.Bunny;
import hu.laci200270.games.sbs3djumper.world.EnumLightType;
import hu.laci200270.games.sbs3djumper.world.Light;
import hu.laci200270.games.sbs3djumper.world.World;
import net.lingala.zip4j.exception.ZipException;
import org.joml.Vector3f;

import java.io.IOException;

/**
 * Created by Laci on 2016. 01. 23..
 */
public class CommonUtils {
    public static String generateLwjglName() {
        return String.format("lwjgl-%s", Constants.lwjgl_version);
    }

    public static void setupLwjgl() throws ZipException {
        try {
            System.out.println(FileUtils.getRootDir().getAbsolutePath());
            boolean shouldExtract = Constants.lwjgl_zip.exists();
            String hashBefore = "0x00";
            if (shouldExtract) {
                hashBefore = Files.hash(Constants.lwjgl_zip, Hashing.md5()).toString();
            }

            WebUtils.downloadFile(Constants.lwjgl_url, Constants.lwjgl_zip);
            String hashAfter = Files.hash(Constants.lwjgl_zip, Hashing.md5()).toString();
            shouldExtract = hashBefore != hashAfter;
            if (shouldExtract) {
                FileUtils.unzipIt(Constants.lwjgl_zip, Constants.lwjgl_dir);
            }


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exitting now");
        }
    }

    public static void fillWorldWithRandom(World world,int bunnyCount,int lightCount,int range){
        for (int i=0;i<bunnyCount;i++){
            Bunny bunny=new Bunny();
            bunny.setWorldPos(new Vector3f(Constants.random.nextFloat()*range,Constants.random.nextFloat()*range,Constants.random.nextFloat()*range));
            bunny.setScaling(new Vector3f(Constants.random.nextFloat(),Constants.random.nextFloat(),Constants.random.nextFloat()));
            world.addWorldPart(bunny);
        }
        for (int i=0;i<lightCount;i++)
            world.addLight(new Light(new Vector3f(Constants.random.nextFloat(),Constants.random.nextFloat(),Constants.random.nextFloat()),new Vector3f(Constants.random.nextFloat()*getRandomMultiplier(range),Constants.random.nextFloat()*getRandomMultiplier(range),Constants.random.nextFloat()*getRandomMultiplier(range)),Float.MAX_VALUE, EnumLightType.POINT));

    }

    public static int getRandomMultiplier(int range){
        if(Constants.random.nextBoolean())
            range=-range;
        return range;
    }

}
