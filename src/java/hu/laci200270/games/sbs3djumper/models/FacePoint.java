package hu.laci200270.games.sbs3djumper.models;


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



}
