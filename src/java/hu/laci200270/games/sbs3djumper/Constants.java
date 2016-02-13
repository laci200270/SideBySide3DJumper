package hu.laci200270.games.sbs3djumper;



import hu.laci200270.games.sbs3djumper.utils.CommonUtils;
import hu.laci200270.games.sbs3djumper.utils.FileUtils;
import org.lwjgl.opengl.GL20;


import java.io.File;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by Laci on 2016. 01. 23..
 */
public class Constants {

    //LWJGL
    public static final String lwjgl_url="http://downloads.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.9.3/lwjgl-2.9.3.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjava-game-lib%2Ffiles%2FOfficial%2520Releases%2FLWJGL%25202.9.3%2F&ts=1453574719&use_mirror=netix";
    public static final String lwjgl_version="2.9.3";
    public static final File lwjgl_zip=new File(FileUtils.getTempDir(),"lwjgl.zip");
    public static final File lwjgl_dir=new File(FileUtils.getRootDir(),"lwjgl");
    public static final File lwjgl_unpacked_dir=new File(lwjgl_dir, CommonUtils.generateLwjglName());
    public static final File lwjgl_natives_dir=new File(FileUtils.getRootDir(),"natives");

    //COMMON
    public static final String gameVer="{developer_version}";
    public static final String releaseType="{developer_release}";
    public static final String devName="laci200270";
    public static final String gameName="Side By Side 3D Jumper";
    public static final String gameCodeName="SbS3DJumper";
    public static Random random=new Random();

    //Log4J
    public static final Logger logger= Logger.getLogger("console");

    //RENDER
    public static boolean useVBOs=false;
    public static final int programID= GL20.glCreateProgram();


}
