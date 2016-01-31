package hu.laci200270.games.sbs3djumper.obj;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class Vertex3F {
    float x,y,z;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Vertex3F(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Vertex3F{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
