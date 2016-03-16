package hu.laci200270.games.sbs3djumper;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import hu.laci200270.games.sbs3djumper.models.ModelLoaderRegistry;
import hu.laci200270.games.sbs3djumper.obj.ObjLoader;
import hu.laci200270.games.sbs3djumper.threading.AnimationThread;
import hu.laci200270.games.sbs3djumper.threading.WorldTickingThread;
import hu.laci200270.games.sbs3djumper.world.Bunny;
import hu.laci200270.games.sbs3djumper.world.Light;
import hu.laci200270.games.sbs3djumper.world.World;
import org.joml.Vector3f;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.*;

import java.io.BufferedReader;
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


        System.setProperty("org.lwjgl.librarypath", Object.class.getResource("/natives/windows/").getFile());
        GLFWErrorCallback errorCallback;
        errorCallback=GLFWErrorCallback(new GLFWErrorCallback.SAM() {
            @Override
            public void invoke(int error, long description) {
                System.out.println("E "+error+"D "+description);
            }
        });
        glfwSetErrorCallback(errorCallback);
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

        shader.setUniformInteger("tex", 0);
        ModelLoaderRegistry.registerModelLoader(new ObjLoader());
        Camera camera = new Camera();

        camera.init(window);
        GL11.glEnable(GL_DEPTH_TEST);
        World world=new World(shader);
        Bunny bunny1=new Bunny();
        //Weapon ak = new Weapon(camera, "eye.obj", new Vector3f(100f));
        bunny1.setWorldPos(new Vector3f(-0f, -0.25f, -4f));
        bunny1.setScaling(new Vector3f(0.1f));
        shader.setUniformVector3("lightColour", new Vector3f(1f));
        shader.setUniformVector3("lightPos",new Vector3f(.05f,1,-1));
        //world.addWorldPart(bunny1);
        fillWorldWithRandom(world,1,8,1);
        AnimationThread animationThread = new AnimationThread(world);
        WorldTickingThread worldTickingThread = new WorldTickingThread(world);
        animationThread.start();
        worldTickingThread.start();

        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glEnable(GL_BLEND);
        GL11.glEnable(GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_FRONT_FACE);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK,GL11.GL_LINES);
        System.out.println(GL20.glGetProgramInfoLog(shader.getProgramId()));
        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE && shouldRun) {
            GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //GL11.glClearColor(0.2f,0f,1f,1);
            GLFW.glfwPollEvents();
            camera.handleKeys(window);
            if(GLFW.glfwGetKey(window,GLFW_KEY_LEFT_CONTROL)==1)
                animationThread.shouldRun=!animationThread.shouldRun;
            camera.apply(shader);

            world.render();

            GLFW.glfwSwapBuffers(window);
            lastFrame = 0;
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
        int WIDTH = 640;
        int HEIGHT = 480;
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    public static void fillWorldWithRandom(World world,int bunnyCount,int lightCount,int range){
        for (int i=0;i<bunnyCount;i++){
            Bunny bunny=new Bunny();
            bunny.setWorldPos(new Vector3f(Constants.random.nextFloat()*range,Constants.random.nextFloat()*range,Constants.random.nextFloat()*range));
            bunny.setScaling(new Vector3f(0.001f));
            world.addWorldPart(bunny);
        }
        for (int i=0;i<lightCount;i++)
            world.addLight(new Light(new Vector3f(Constants.random.nextFloat(),Constants.random.nextFloat(),Constants.random.nextFloat()),new Vector3f(Constants.random.nextFloat()*getRandomMultiplier(range),Constants.random.nextFloat()*getRandomMultiplier(range),Constants.random.nextFloat()*getRandomMultiplier(range))));

    }

    public static int getRandomMultiplier(int range){
        if(Constants.random.nextBoolean())
            range=-range;
        return range;
    }

}
