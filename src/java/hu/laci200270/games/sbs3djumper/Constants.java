package hu.laci200270.games.sbs3djumper;


import hu.laci200270.games.sbs3djumper.utils.CommonUtils;
import hu.laci200270.games.sbs3djumper.utils.FileUtils;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by Laci on 2016. 01. 23..
 */
public class Constants {

    //LWJGL
    public static final String lwjgl_url = "http://build.lwjgl.org/release/3.0.0b/lwjgl-3.0.0b.zip";
    public static final String lwjglMd5 = "61d15b686bebddfeee5b17774039b236";
    public static final String lwjgl_version = "3.0.0b";
    public static final File lwjgl_zip = new File(FileUtils.getRootDir(), "lwjgl.zip");
    public static final File lwjgl_dir = new File(FileUtils.getRootDir(), "lwjgl");
    public static final File lwjgl_unpacked_dir = new File(lwjgl_dir, CommonUtils.generateLwjglName());
    public static final File lwjgl_natives_dir = new File(lwjgl_dir, "native");

    //COMMON
    public static final String gameVer = "{developer_version}";
    public static final String releaseType = "{developer_release}";
    public static final String devName = "laci200270";
    public static final String gameName = "Side By Side 3D Jumper";
    public static final String gameCodeName = "SbS3DJumper";

    //Log4J
    public static final Logger logger = Logger.getLogger("console");

//    public static final int programID = GL20.glCreateProgram();

    public static Random random = new Random();

    //RENDER
    public static boolean useVBOs = true;

    public static boolean useShaders = true;
    public static int width=640;
    public static int height=480;


}
