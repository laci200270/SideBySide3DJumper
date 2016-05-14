package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.renderer.ITitleInfoProvider;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;
import org.joml.AxisAngle4d;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class Camera implements ITitleInfoProvider {


    public static final float maxHeadAngle = 30f;
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000000.f;
    public float fov = 60f;
    Vector3f pos;
    Vector3f rot;
    float movementMultipplier = 500f;
    float mouseMultiplier = 0.05f;
    Vector2f currentPosCursor = new Vector2f();
    Vector2f prevPosCursor = new Vector2f();
    GLFWCursorPosCallback cursorPosCallback = null;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Vector3f up = new Vector3f(0, 1, 0);
    private float yaw;
    private float pitch;
    private boolean canFly = true;
    private long window;
    long lastMeasure;
    int frames;
    int prevFrames;
    public void init(long window) {
        pos = new Vector3f(0, 0, -1);
        rot = new Vector3f(0, 0, 0);
       //GLFW.glfwSetCursor(window, GLFW.GLFW_CURSOR_HIDDEN);
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        cursorPosCallback=new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                currentPosCursor = new Vector2f((float) xpos, (float) ypos);

            }
        };
        GLFW.glfwSetCursorPosCallback(window, cursorPosCallback);
        this.window=window;

    }

    public void apply(MainRenderManager manager) {


        float aspectRatio = (manager.getResolution().x/manager.getResolution().y);
        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(fov), aspectRatio,
                Z_NEAR, Z_FAR);

        if (currentPosCursor.x != prevPosCursor.x) {

            yaw += (currentPosCursor.x - prevPosCursor.x) * mouseMultiplier;
        }
        if (currentPosCursor.y != prevPosCursor.y) {
            if (!((pitch + (currentPosCursor.y - prevPosCursor.y)) * mouseMultiplier < -maxHeadAngle || (((currentPosCursor.y - prevPosCursor.y) + pitch) * mouseMultiplier) > maxHeadAngle))
                pitch += (currentPosCursor.y - prevPosCursor.y) * mouseMultiplier;
        }


        viewMatrix = new Matrix4f();

        viewMatrix.rotate((float) Math.toRadians(pitch), 1.0f, 0.0f, 0.0f);
        viewMatrix.rotate((float) Math.toRadians(yaw), 0.0f, 1.0f, 0.0f);
        viewMatrix.translate(pos);



        manager.getMainShader().setUnifromMatrix("projectionMatrix", projectionMatrix);
        manager.getMainShader().setUnifromMatrix("viewMatrix", viewMatrix);
        prevPosCursor = currentPosCursor;

    }

    public void handleKeys(long window) {
     
        if(GLFW.glfwGetKey(window,GLFW.GLFW_KEY_LEFT_SHIFT)==1)
            movementMultipplier=0.0025f;
        else
            movementMultipplier=0.05f;
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == 1)
            walkForward(movementMultipplier);
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == 1)
            walkBackwards(movementMultipplier);
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == 1)
            strafeLeft(movementMultipplier);
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == 1)
            strafeRight(movementMultipplier);
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_KP_ADD) == 1)
            fov++;
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_KP_SUBTRACT) == 1)
            fov--;
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == 1)
            Starter.shouldRun = false;

    }

    public void walkForward(float distance) {
        pos.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        pos.z += distance * (float) Math.cos(Math.toRadians(yaw));
        if(canFly)
            pos.y += distance * (float) Math.sin(Math.toRadians(pitch));
    }

    public void walkBackwards(float distance) {
        pos.x += distance * (float) Math.sin(Math.toRadians(yaw));
        pos.z -= distance * (float) Math.cos(Math.toRadians(yaw));
        if(canFly)
            pos.y -= distance * (float) Math.sin(Math.toRadians(pitch));
    }

    public void strafeLeft(float distance) {
        pos.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        pos.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }

    public void strafeRight(float distance) {
        pos.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        pos.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }

    public Vector3f getPos() {
        return new Vector3f(pos);
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f(viewMatrix);

    }


    @Override
    public String[] getInfo() {
        String[] info={String.format("X: %s",pos.x),String.format("Y: %s",pos.y),String.format("Z: %s",pos.z),String.format("Yaw: %s",yaw),String.format("Pitch: %s",pitch),String.format("Fov: %s",fov)};
        return info;
    }
}



