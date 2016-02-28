package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.ModelLoaderRegistry;
import hu.laci200270.games.sbs3djumper.obj.ObjLoader;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

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

        //FileUtils.unzipIt(new ResourceLocation("natives.zip").getInputStream(), Constants.lwjgl_natives_dir);
        System.setProperty("org.lwjgl.librarypath", Object.class.getResource("/natives/windows/").getFile());
        init();
        GLContext.createFromCurrent();
        GL.createCapabilities(true);
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
        ModelLoaderRegistry.registerModelLoader(new ObjLoader());
        IModel model = ModelLoaderRegistry.getModel(new ResourceLocation("stanfordbunny.obj"), "obj");
        Camera camera = new Camera();
        camera.init(window);
        GL11.glEnable(GL_DEPTH_TEST);
        float distance = -0.3f;
        float rot = 0f;
        GL11.glMatrixMode(GL_PROJECTION);
        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE && shouldRun) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GLFW.glfwPollEvents();
            Matrix4f modelMat = new Matrix4f().scale(1f);
            rot++;
            modelMat.translation(0, -0.10f, distance);
            modelMat.rotate((float) Math.toRadians(rot) * 1f, 0, 1, 0);
            shader.bind();
            shader.setUnifromMatrix("modelMatrix", modelMat);
            camera.handleKeys(window);
            camera.apply(shader);
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINES);
            model.render();
            shader.unbind();
            GLFW.glfwSwapBuffers(window);

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
