package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.models.ModelLoaderRegistry;
import hu.laci200270.games.sbs3djumper.models.Terrain;
import hu.laci200270.games.sbs3djumper.obj.ObjLoader;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;
import hu.laci200270.games.sbs3djumper.threading.AnimationThread;
import hu.laci200270.games.sbs3djumper.threading.WorldTickingThread;
import hu.laci200270.games.sbs3djumper.world.*;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.*;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

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

        MainRenderManager manager=new MainRenderManager();
        System.setProperty("org.lwjgl.librarypath", Object.class.getResource("/natives/windows/").getFile());
        GLFWErrorCallback errorCallback;
        errorCallback=GLFWErrorCallback(new GLFWErrorCallback.SAM() {
            @Override
            public void invoke(int error, long description) {
                System.out.println("E "+error+"D "+description);
            }
        });

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
        bunny1.setScaling(new Vector3f(0.1f));


        WorldPart part=new TerrainWorldObject("heightmap");
        part.setScaling(new Vector3f(500));
        part.setWorldPos(new Vector3f(0,-500,1000));
        world.addWorldPart(part);
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
