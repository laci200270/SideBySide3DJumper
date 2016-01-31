package hu.laci200270.games.sbs3djumper.obj;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class FacePoint {

    public Vertex4F point;
    public Vertex3F texture;
    public Vertex3F normal;

    public FacePoint(Vertex4F point, Vertex3F texture,Vertex3F normal) {
        this.point = point;
        this.texture = texture;
        this.normal=normal;
    }


}
