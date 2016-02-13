package hu.laci200270.games.sbs3djumper;

import com.hackoeur.jglm.Mat4;
import hu.laci200270.games.sbs3djumper.models.ModelLoaderRegistry;
import hu.laci200270.games.sbs3djumper.obj.ObjLoader;
import hu.laci200270.games.sbs3djumper.obj.ObjModel;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memStrLen1;

/**
 * Created by Laci on 2016. 01. 29..
 */
public class Starter {

    private static long lastFrame;
    private static long window;

    final static String classPath = System.getProperty("java.class.path", ".");
    final static String[] classPathElements = classPath.split(System.getProperty("path.separator"));

    public static void main(String[] args){
        System.setProperty("org.lwjgl.librarypath", new ResourceLocation("natives/windows").file.getAbsolutePath());
try {

    init();
    GLContext.createFromCurrent();
    GL.createCapabilities(true);
    ObjModel file = null;
    ModelLoaderRegistry.registerModelLoader(new ObjLoader());
    float rotation = 0f;

    file = (ObjModel) ModelLoaderRegistry.getModel(new ResourceLocation("spehreandcube.obj").getFile(), "obj");

    GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glDepthFunc(GL11.GL_LEQUAL);
    Shader vert=new Shader(new ResourceLocation("shaders/object.vert").file, GL20.GL_VERTEX_SHADER);
    Shader frag= new Shader(new ResourceLocation("shaders/object.frag").file, GL20.GL_FRAGMENT_SHADER);

    while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glDisable(GL11.GL_CULL_FACE);

        // GL11.glShadeModel(G);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); //clear screen
        GL11.glTranslatef(0, -1, -0.5f);
        GL11.glScalef(0.1f, 0.1f, 0.1f);
        GL11.glRotatef(rotation, 0, 1, 0f);
        rotation++;
        Mat4 transform=new Mat4();
        //GL11.glClearColor(1,0,1,0);

        vert.apply(Constants.programID);
        frag.apply(Constants.programID);

        //GL11.glDisable(GL11.GL_CULL_FACE);
        GL20.glUseProgram(Constants.programID);
        System.out.println(GL20.glGetProgramInfoLog(Constants.programID));
        file.render();
        glfwSwapBuffers(window);
        glfwPollEvents();
        GL11.glPopMatrix();


    }
}catch (IOException e) {
        e.printStackTrace();
    }
    }

    private static void handleDir(File file) {
        File[] elements=file.listFiles();
        for(int i=0;i<elements.length;i++){
            if(elements[i].isDirectory())
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

    private static void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        //glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        int WIDTH = 300;
        int HEIGHT = 300;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
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
