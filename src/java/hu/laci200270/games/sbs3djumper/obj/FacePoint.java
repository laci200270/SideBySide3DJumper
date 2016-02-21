package hu.laci200270.games.sbs3djumper.obj;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.utils.GLUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class FacePoint {

    public Vec4 point;

    public Vec3 texture;

    public Vec3 normal;

    public Vec3 color;

    public FacePoint(Vec4 point, Vec3 texture, Vec3 normal) {
        this.point = point;
        this.texture = texture;
        this.normal = normal;
    }

    public void render() {
        //GL11.glColor3f(color.getX(), color.getY(), color.getX());
        if (this.texture != null) {
            //TODO do texture loading
            // GL11.glTexCoord3f(texture.getX(),texture.getY(),texture.getZ());
        }
        if (this.normal != null) {
            GL11.glNormal3f(normal.getX(), normal.getY(), normal.getY());
        }
        GL11.glVertex4f(point.getX(), point.getY(), point.getZ(), point.getW());


        GL20.glUniformMatrix4fv(Constants.vertexVecPos, true, GLUtils.makeGoodBuffer(point.getBuffer()));

    }


}
