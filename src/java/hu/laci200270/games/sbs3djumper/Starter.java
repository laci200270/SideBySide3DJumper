package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.ModelLoaderRegistry;
import hu.laci200270.games.sbs3djumper.models.ModelRegistry;
import hu.laci200270.games.sbs3djumper.obj.ObjLoader;
import hu.laci200270.games.sbs3djumper.threading.AnimationThread;
import hu.laci200270.games.sbs3djumper.threading.WorldTickingThread;
import hu.laci200270.games.sbs3djumper.world.Bunny;
import hu.laci200270.games.sbs3djumper.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import java.io.IOException;
import java.nio.ByteBuffer;

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

        System.setProperty("org.lwjgl.librarypath", Object.class.getResource("/natives/windows/").getFile());
        init();
        GLContext.createFromCurrent();
        GL.createCapabilities(true);


        ShaderProgram shader = null;
        try {
            shader = new ShaderProgram("object");
            shader.link();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelLoaderRegistry.registerModelLoader(new ObjLoader());
        Camera camera = new Camera();
        camera.init(window);
        GL11.glEnable(GL_DEPTH_TEST);
        World world=new World(shader);
        Bunny bunny1=new Bunny();
        Bunny bunny2=new Bunny();
        bunny1.setWorldPos(new Vector3f(1f,60f,-15f));
        bunny2.setWorldPos(new Vector3f(3f,0f,2f));
        world.addWorldPart(bunny1);
      //  world.addWorldPart(bunny2);
       /* AnimationThread animationThread=new AnimationThread(world);
        WorldTickingThread worldTickingThread=new WorldTickingThread(world);*/
        /*animationThread.start();
        worldTickingThread.start();*/
        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glEnable(GL_BLEND);
        System.out.println(GL20.glGetProgramInfoLog(shader.getProgramId()));
        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE && shouldRun) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GLFW.glfwPollEvents();
            camera.handleKeys(window);
            camera.apply(shader);

            world.render();
            GLFW.glfwSwapBuffers(window);
        }
        GLFW.glfwDestroyWindow(window);
        System.exit(0);
    }

    public static void init() {

        if (glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        int WIDTH = 700;
        int HEIGHT = 700;
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }



}
