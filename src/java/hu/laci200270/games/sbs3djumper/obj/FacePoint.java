package hu.laci200270.games.sbs3djumper.obj;

import org.lwjgl.opengl.GL11;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class FacePoint {

    public Vertex4F point;

    public Vertex3F texture;

    public Vertex3F normal;

    public Vertex3F color;

    public FacePoint(Vertex4F point, Vertex3F texture, Vertex3F normal) {
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


    }


}
