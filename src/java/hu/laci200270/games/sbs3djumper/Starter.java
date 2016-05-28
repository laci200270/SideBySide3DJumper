package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.models.ModelLoaderRegistry;
import hu.laci200270.games.sbs3djumper.obj.ObjLoader;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;
import hu.laci200270.games.sbs3djumper.threading.AnimationThread;
import hu.laci200270.games.sbs3djumper.threading.WorldTickingThread;
import hu.laci200270.games.sbs3djumper.utils.CommonUtils;
import hu.laci200270.games.sbs3djumper.world.*;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

/**
 * Created by Laci on 2016. 01. 29..
 */
public class Starter {

    final static String classPath = System.getProperty("java.class.path", ".");

    final static String[] classPathElements = classPath.split(System.getProperty("path.separator"));

    public static long window;
    public static boolean shouldRun = true;
    private static long lastFrame;

    public static void main(String[] args) throws IOException {
       // System.out.println(CommonUtils.genSystemInfo());
        CommonUtils.setupLwjgl();
        MainRenderManager manager=new MainRenderManager();

        System.out.println(Constants.lwjgl_natives_dir.exists()+" "+Constants.lwjgl_natives_dir.isDirectory());
        System.setProperty("org.lwjgl.librarypath", Constants.lwjgl_natives_dir.getAbsolutePath()+File.separator);
        GLFWErrorCallback errorCallback;
        errorCallback= new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                System.out.println("E "+error+"D "+description);
            }
        };

        glfwSetErrorCallback(errorCallback);

        manager.init();

        manager.getMainShader().setUniformInteger("tex", 0);
        ModelLoaderRegistry.registerModelLoader(new ObjLoader());
        Camera camera = new Camera();

        camera.init(manager.getWindow());
        GL11.glEnable(GL_DEPTH_TEST);
        World world=new World(manager.getMainShader());
        Bunny bunny1=new Bunny();
        bunny1.setWorldPos(new Vector3f(-0f, -0.25f, -4f));
        bunny1.setScaling(new Vector3f(1f));


        world.addLight(new Light(new Vector3f(-0f,0f,-3f)));
        world.addWorldPart(bunny1);
        AnimationThread animationThread = new AnimationThread(world);
        WorldTickingThread worldTickingThread = new WorldTickingThread(world);
        animationThread.start();
        worldTickingThread.start();

        manager.addTitleInfoProvider(camera);

        System.out.println(GL20.glGetProgramInfoLog(manager.getMainShader().getProgramId()));
        while (GLFW.glfwWindowShouldClose(manager.getWindow()) == GL11.GL_FALSE && shouldRun) {
            manager.render(world,camera,animationThread);

        }
        GLFW.glfwDestroyWindow(manager.getWindow());
        System.exit(0);
    }






}
