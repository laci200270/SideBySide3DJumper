package hu.laci200270.games.sbs3djumper;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class Camera {


    public static final float maxHeadAngle = 30f;
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    public float fov = 60f;
    Vector3f pos;
    Vector3f rot;
    float movementMultipplier = 0.05f;
    float mouseMultiplier = 0.05f;
    Vector2f currentPosCursor = new Vector2f();
    Vector2f prevPosCursor = new Vector2f();
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Vector3f up = new Vector3f(0, 1, 0);
    private float yaw;
    private float pitch;

    GLFWCursorPosCallback cursorPosCallback=null;

    public void init(long window) {
        pos = new Vector3f(0, 0, -1);
        rot = new Vector3f(0, 0, 0);
        GLFW.glfwSetCursor(window, GLFW.GLFW_CURSOR_HIDDEN);
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        cursorPosCallback=new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                currentPosCursor = new Vector2f((float) xpos, (float) ypos);

            }
        };
        GLFW.glfwSetCursorPosCallback(window, cursorPosCallback);


    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public void apply(ShaderProgram prog) {


        float aspectRatio = (float) 700 / 700;
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


        prog.setUnifromMatrix("projectionMatrix", projectionMatrix);
        prog.setUnifromMatrix("viewMatrix", viewMatrix);
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
        pos.y += distance * (float) Math.sin(Math.toRadians(pitch));
    }


    public void walkBackwards(float distance) {
        pos.x += distance * (float) Math.sin(Math.toRadians(yaw));
        pos.z -= distance * (float) Math.cos(Math.toRadians(yaw));
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
}



