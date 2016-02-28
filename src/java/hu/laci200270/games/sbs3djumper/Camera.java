package hu.laci200270.games.sbs3djumper;/*package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.obj.Vertex3F;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;*/

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class Camera {

    public static final Vector3f UP = new Vector3f(0, 1, 0);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    public float fov = 60f;
    Vector3f pos;
    Vector3f rot;
    private Matrix4f projectionMatrix;


    public void init() {
        pos = new Vector3f(0, 0, 0);
        rot = new Vector3f(0, 0, 0);



    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public void apply(ShaderProgram prog) {

        float aspectRatio = (float) 700 / 700;
        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(fov), aspectRatio,
                Z_NEAR, Z_FAR);


        prog.setUnifromMatrix("projectionMatrix", projectionMatrix);




    }
}
