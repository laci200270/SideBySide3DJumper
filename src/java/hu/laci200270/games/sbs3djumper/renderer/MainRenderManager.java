package hu.laci200270.games.sbs3djumper.renderer;

import hu.laci200270.games.sbs3djumper.Camera;
import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.threading.AnimationThread;
import hu.laci200270.games.sbs3djumper.world.World;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
//import org.lwjgl.opengl.GLContext;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Laci on 2016. 03. 26..
 */
public class MainRenderManager {
    int WIDTH=640;
    int HEIGHT=480;
    boolean fullScreen=false;
    ShaderProgram mainShader;
    Camera camera=new Camera();
    private long window;
    private List<ITitleInfoProvider> providers;
    private String sepChar;
    boolean wireframe;
    private boolean isInWireFrameMode;
    private FpsCounter counter;
    private boolean optimazitainsEnabled=true;

    public void init() {

        if (glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        if(fullScreen)
            window=glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", glfwGetPrimaryMonitor(), NULL);
        else
            window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);


        GL.createCapabilities();
        glfwSwapInterval(0);
        glfwShowWindow(window);
        mainShader=new ShaderProgram("object");
        try {
            mainShader.link();
        } catch (Exception e) {
            e.printStackTrace();
        }
        providers=new ArrayList<>();
        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glEnable(GL_BLEND);
        GL11.glEnable(GL_DEPTH_TEST);
        sepChar="|";
        GL11.glEnable(GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_FRONT_FACE);
        counter=new FpsCounter();
        addTitleInfoProvider(counter);
    }

    public void resizeWindow(int width,int height,boolean fullScreen){

    }

    public void render(World world,Camera camera,AnimationThread animationThread){
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GLFW.glfwPollEvents();
        camera.handleKeys(window);
        counter.increaseCounter();
        if(GLFW.glfwGetKey(window,GLFW_KEY_LEFT_CONTROL)==1)
            animationThread.shouldRun=!animationThread.shouldRun;
        camera.apply(this);
        //GL11.glRenderMode(GL11.GL_LINE);
        world.render(this,optimazitainsEnabled);
        String windowTitle="";
        for (int i = 0; i < providers.size(); i++) {
            ITitleInfoProvider currProvider=providers.get(i);
            String[] info=currProvider.getInfo();
            for (int j = 0; j < info.length; j++) {
                windowTitle+=info[j];
                    windowTitle+=sepChar;
            }
        }
        glfwSetWindowTitle(window,windowTitle);
        GLFW.glfwSwapBuffers(window);
    }

    public void addTitleInfoProvider(ITitleInfoProvider provider){
        if(!providers.contains(provider))
            providers.add(provider);
    }

    public long getWindow() {
        return window;
    }

    public Vector2f getResolution() {
        return new Vector2f(WIDTH,HEIGHT);
    }

    public ShaderProgram getMainShader() {
        return mainShader;
    }

    public boolean isInWireFrameMode() {
        return isInWireFrameMode;
    }
}
