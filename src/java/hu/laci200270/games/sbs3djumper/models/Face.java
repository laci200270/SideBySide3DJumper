package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.models.FacePoint;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class Face {
    public List<FacePoint> elements = new ArrayList<>();
    public Face(List<FacePoint> elements) {
        this.elements = elements;
    }
    public Vector3f generateNormalFace(){
        return getNormal(elements.get(0).point,elements.get(1).point,elements.get(02).point);
    }
    private Vector3f getNormal(Vector4f p1, Vector4f p2, Vector4f p3) {
        Vector3f output = new Vector3f();
        Vector3f calU = new Vector3f(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        Vector3f calV = new Vector3f(p3.x-p1.x, p3.y-p1.y, p3.z-p1.z);
        output.x = calU.y*calV.z - calU.z*calV.y;
        output.y = calU.z*calV.x - calU.x*calV.z;
        output.z = calU.x*calV.y - calU.y*calV.x;
        return output.normalize();
    }

}
