package hu.laci200270.games.sbs3djumper;/*package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.obj.Vertex3F;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;*/

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import hu.laci200270.games.sbs3djumper.utils.GLUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class Camera {

    public static final Vec3 UP = new Vec3(0, 1, 0);

    Vec3 pos;

    Vec3 rot;

    public void init() {
        pos = new Vec3(0, 0, 0);
        rot = new Vec3(0, 0, 0);
    }

    public void setPos(Vec3 pos) {
        this.pos = pos;
    }

    public void apply() {
        Mat4 viewMatrix = Matrices.perspective(45, 2, 0, 15);
        Mat4 projMat = Matrices.lookAt(pos, rot, UP);
        Mat4 modelMatrix = Mat4.MAT4_IDENTITY;

        FloatBuffer viewBuff = viewMatrix.getBuffer();
        FloatBuffer projBuff = projMat.getBuffer();
        FloatBuffer modelBuff = modelMatrix.getBuffer();

        GL20.glUniformMatrix4fv(Constants.modelMatPos, true, GLUtils.makeGoodBuffer(modelBuff));
        GL20.glUniformMatrix4fv(Constants.projMatPos, true, GLUtils.makeGoodBuffer(projBuff));
        GL20.glUniformMatrix4fv(Constants.viewMatPos, true, GLUtils.makeGoodBuffer(viewBuff));


    }
}
