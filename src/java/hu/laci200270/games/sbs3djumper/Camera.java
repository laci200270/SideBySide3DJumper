/*package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.obj.Vertex3F;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;*/

/**
 * Created by Laci on 2016. 02. 06..
 */
public class Camera {
    /*public static float moveSpeed = 0.001f;

    private static float maxLook = 85;

    private static float mouseSensitivity = 0.05f;

    private static Vertex3F pos;
    private static Vertex3F rotation;

    public static void create() {
        pos = new Vertex3F(0, 0, 0);
        rotation = new Vertex3F(0, 0, 0);
    }

    public static void apply() {
        if(rotation.getY() / 360 > 1) {
            rotation.y -= 360;
        } else if(rotation.getY() / 360 < -1) {
            rotation.y += 360;
        }
        glLoadIdentity();
        glRotatef(rotation.getX(), 1, 0, 0);
        glRotatef(rotation.getY(), 0, 1, 0);
        glRotatef(rotation.getZ(), 0, 0, 1);
       // glTranslatef(-pos.x, -pos.y, -pos.z);
    }



    public static void acceptInput(float delta) {
        acceptInputRotate(delta);
        acceptInputGrab();
        acceptInputMove(delta);
    }

    public static void acceptInputRotate(float delta) {
        if(Mouse.isGrabbed()) {
            float mouseDX = Mouse.getDX();
            float mouseDY = -Mouse.getDY();
            rotation.y += mouseDX * mouseSensitivity * delta;
            rotation.x += mouseDY * mouseSensitivity * delta;
            rotation.x = Math.max(-maxLook, Math.min(maxLook, rotation.x));
        }
    }

    public static void acceptInputGrab() {
        if(Mouse.isInsideWindow() && Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mouse.setGrabbed(false);
        }
    }

    public static void acceptInputMove(float delta) {
        boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_W);
        boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_S);
        boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_D);
        boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
        boolean keyFast = Keyboard.isKeyDown(Keyboard.KEY_Q);
        boolean keySlow = Keyboard.isKeyDown(Keyboard.KEY_E);
        boolean keyFlyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean keyFlyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);


        float speed;

        if(keyFast) {
            speed = moveSpeed * 5;
        }
        else if(keySlow) {
            speed = moveSpeed / 2;
        }
        else {
            speed = moveSpeed;
        }

        speed *= delta;

        if(keyFlyUp) {
            pos.y += speed;
        }
        if(keyFlyDown) {
            pos.y -= speed;
        }

        if(keyDown) {
            pos.x -= Math.sin(Math.toRadians(rotation.getY())) * speed;
            pos.z += Math.cos(Math.toRadians(rotation.getY())) * speed;
        }
        if(keyUp) {
            pos.x += Math.sin(Math.toRadians(rotation.getY())) * speed;
            pos.z -= Math.cos(Math.toRadians(rotation.getY())) * speed;
        }
        if(keyLeft) {
            pos.x += Math.sin(Math.toRadians(rotation.getY() - 90)) * speed;
            pos.z -= Math.cos(Math.toRadians(rotation.getY() - 90)) * speed;
        }
        if(keyRight) {
            pos.x += Math.sin(Math.toRadians(rotation.getY() + 90)) * speed;
            pos.z -= Math.cos(Math.toRadians(rotation.getY() + 90)) * speed;
        }
    }

    public static void setSpeed(float speed) {
        moveSpeed = speed;
    }

    public static void setPos(Vertex3F pos) {
        Camera.pos = pos;
    }

    public static Vertex3F getPos() {
        return pos;
    }

    public static void setX(float x) {
        pos.x = x;
    }

    public static float getX() {
        return pos.x;
    }

    public static void addToX(float x) {
        pos.x += x;
    }

    public static void setY(float y) {
        pos.y = y;
    }

    public static float getY() {
        return pos.y;
    }

    public static void addToY(float y) {
        pos.y += y;
    }

    public static void setZ(float z) {
        pos.z = z;
    }

    public static float getZ() {
        return pos.z;
    }

    public static void addToZ(float z) {
        pos.z += z;
    }

    public static void setRotation(Vertex3F rotation) {
        Camera.rotation = rotation;
    }

    public static Vertex3F getRotation() {
        return rotation;
    }

    public static void setRotationX(float x) {
        rotation.x = x;
    }

    public static float getRotationX() {
        return rotation.x;
    }

    public static void addToRotationX(float x) {
        rotation.x += x;
    }

    public static void setRotationY(float y) {
        rotation.y = y;
    }

    public static float getRotationY() {
        return rotation.y;
    }

    public static void addToRotationY(float y) {
        rotation.y += y;
    }

    public static void setRotationZ(float z) {
        rotation.z = z;
    }

    public static float getRotationZ() {
        return rotation.z;
    }

    public static void addToRotationZ(float z) {
        rotation.z += z;
    }

    public static void setMaxLook(float maxLook) {
        Camera.maxLook = maxLook;
    }

    public static float getMaxLook() {
        return maxLook;
    }

    public static void setMouseSensitivity(float mouseSensitivity) {
        Camera.mouseSensitivity = mouseSensitivity;
    }

    public static float getMouseSensitivity() {
        return mouseSensitivity;
    }*/
}
