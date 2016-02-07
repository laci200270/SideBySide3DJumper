package hu.laci200270.games.sbs3djumper.obj;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class Vertex4F {
    float x, y, z, w;

    public Vertex4F(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

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

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex4F)) return false;

        Vertex4F vertex4F = (Vertex4F) o;

        if (Float.compare(vertex4F.w, w) != 0) return false;
        if (Float.compare(vertex4F.x, x) != 0) return false;
        if (Float.compare(vertex4F.y, y) != 0) return false;
        if (Float.compare(vertex4F.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
        return result;
    }


}
