package hu.laci200270.games.sbs3djumper;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

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

         //FileUtils.unzipIt(new ResourceLocation("natives.zip").getInputStream(), Constants.lwjgl_natives_dir);
        System.setProperty("org.lwjgl.librarypath", Object.class.getResource("/natives/windows/").getFile());
        init();
        GLContext.createFromCurrent();
        GL.createCapabilities(true);
        /*IModel file = null;
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
            //GL11.glTranslatef(0, -1, -0.5f);
            //GL11.glScalef(0.5f, 0.5f, 0.5f);
            //GL11.glRotatef(rotation, 0, 1, 0f);
            //GL.at
            //GL20.glLinkProgram(Constants.programID);
            rotation++;


           // GL20.glUseProgram(Constants.programID);
           // System.out.println(GL20.glGetProgramInfoLog(Constants.programID));
            file.render();
            glfwSwapBuffers(window);
            glfwPollEvents();


        }*/
        float[] vertexes={
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f

        };
        FloatBuffer buff=BufferUtils.createFloatBuffer(vertexes.length);
        int vaoId=GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        buff.put(vertexes);
        buff.flip();

        int vboId=GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buff,GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL30.glBindVertexArray(0);
        ShaderProgram shaderProgram = null;
        try {
            shaderProgram = new ShaderProgram("object");
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (GLFW.glfwWindowShouldClose(window)==GL11.GL_FALSE) {
            shaderProgram.bind();
            GL30.glBindVertexArray(vaoId);
            GL20.glEnableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
            shaderProgram.unbind();
            System.out.println(GL11.glGetError());

            System.out.println(GL20.glGetProgramInfoLog(Constants.programID));
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

    public static void processGlError() {
        int errCode = GL11.glGetError();
        if (errCode != 0) {
            List<StackTraceElement> strElemets = Arrays.asList(Thread.currentThread().getStackTrace());
            for (StackTraceElement elemt : strElemets)
                System.err.println(elemt.toString());
            System.err.println(errCode);
        }
    }


}
