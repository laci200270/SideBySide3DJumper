package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.ModelLoaderRegistry;
import hu.laci200270.games.sbs3djumper.models.ModelRegistry;
import hu.laci200270.games.sbs3djumper.obj.ObjLoader;
import hu.laci200270.games.sbs3djumper.threading.AnimationThread;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Laci on 2016. 01. 29..
 */
public class Starter {

    final static String classPath = System.getProperty("java.class.path", ".");

    final static String[] classPathElements = classPath.split(System.getProperty("path.separator"));

    public static long window;

    private static long lastFrame;

    public static void main(String[] args) throws IOException {

        //  FileUtils.unzipIt(new ResourceLocation("natives.zip").getInputStream(),Constants.lwjgl_natives_dir);
        System.setProperty("org.lwjgl.librarypath", Object.class.getResource("/natives/windows/").getFile());
        init();
        GLContext.createFromCurrent();
        GL.createCapabilities(true);
        IModel file = null;
        ModelLoaderRegistry.registerModelLoader(new ObjLoader());
        float rotation = 0f;
        Camera camera = new Camera();
        camera.init();

        AnimationThread animationThread = new AnimationThread();
        new Thread(animationThread).start();

        GL11.glDepthFunc(GL11.GL_LEQUAL);
        Shader vert = new Shader(new ResourceLocation("shaders/object.vert"), GL20.GL_VERTEX_SHADER);
        Shader frag = new Shader(new ResourceLocation("shaders/object.frag"), GL20.GL_FRAGMENT_SHADER);
        vert.apply(Constants.programID);
        frag.apply(Constants.programID);

        Constants.viewMatPos = GL20.glGetUniformLocation(Constants.programID, "viewMatrix");
        Constants.projMatPos = GL20.glGetUniformLocation(Constants.programID, "projectionMatrix");
        Constants.modelMatPos = GL20.glGetUniformLocation(Constants.programID, "modelMatrix");
        Constants.vertexVecPos = GL20.glGetUniformLocation(Constants.programID, "vertexPos");
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            // GL11.glLightModeli(GL_LIGHT0,1);
            file = ModelRegistry.getModel("spehreandcube.obj");
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glDisable(GL11.GL_CULL_FACE);
            camera.apply();
            GL11.glClearColor(0.5f,0.5f,1,0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); //clear screen
            GL11.glTranslatef(0, -1, -0.5f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            GL11.glRotatef(rotation, 0, 1, 0f);
            GL20.glLinkProgram(Constants.programID);
            rotation++;


            //GL20.glUseProgram(Constants.programID);
           // System.out.println(GL20.glGetProgramInfoLog(Constants.programID));
            file.render();
            glfwSwapBuffers(window);
            glfwPollEvents();


        }
        GLFW.glfwDestroyWindow(window);
        System.exit(0);
    }

    private static void handleDir(File file) {
        File[] elements = file.listFiles();
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].isDirectory())
                handleDir(elements[i]);
            else System.out.println(elements[i].getAbsolutePath());
        }
    }

    public static int getDelta() {
        long time = System.nanoTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public static void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        //glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        int WIDTH = 700;
        int HEIGHT = 700;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.


        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window


        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }


}
