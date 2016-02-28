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
import java.util.Random;

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


        float[] vertices = {
                // Left bottom triangle
                -0.5f, 0.5f, 0.5f, 1f,
                -0.5f, -0.5f, 0f, 1f,
                0.5f, -0.5f, 0f, 1f,
                // Right top triangle
                0.5f, -0.5f, 0f, 1f,
                0.5f, 0.5f, 0f, 1f,
                -0.5f, 0.5f, 0f, 1f
        };

        float[] colors = new float[18];
        Random r = new Random();
        for (int i = 0; i < colors.length; i++) {
            colors[i] = r.nextFloat();
        }
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorBuffer.put(colors);
        colorBuffer.flip();

        int vertexCount = 6;
        int vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        ShaderProgram shader = null;
        try {
            shader = new ShaderProgram("object");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            shader.link();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.projMatPos = GL20.glGetUniformLocation(shader.getProgramId(), "projModViewMat");
        int vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        int colorVboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
        Camera camera = new Camera();
        camera.init();
        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            camera.apply(shader);
            shader.bind();
            GL30.glBindVertexArray(vaoId);
            GL20.glEnableVertexAttribArray(0);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
            GL20.glDisableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL30.glBindVertexArray(0);
            shader.unbind();
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
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

        if (glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        int WIDTH = 700;
        int HEIGHT = 700;
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
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
