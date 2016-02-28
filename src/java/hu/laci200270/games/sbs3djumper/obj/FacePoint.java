package hu.laci200270.games.sbs3djumper.obj;


import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class FacePoint {

    public Vector4f point;

    public Vector3f texture;

    public Vector3f normal;

    public Vector3f color;

    public FacePoint(Vector4f point, Vector3f texture, Vector3f normal) {
        this.point = point;
        this.texture = texture;
        this.normal = normal;
    }

    public void render() {
        //GL11.glColor3f(color.x, color.y, color.x);
        if (this.texture != null) {
            //TODO do texture loading
            // GL11.glTexCoord3f(texture.x,texture.y,texture.z);
        }
        if (this.normal != null) {
            GL11.glNormal3f(normal.x, normal.y, normal.y);
        }
        GL11.glVertex4f(point.x, point.y, point.z, point.w);


        //GL20.glUniformMatrix4fv(Constants.vertexVecPos, true, GLUtils.makeGoodBuffer(point.getBuffer()));

    }


}
