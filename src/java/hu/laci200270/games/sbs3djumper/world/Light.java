package hu.laci200270.games.sbs3djumper.world;

import org.joml.Vector3f;

/**
 * Created by Laci on 2016. 03. 12..
 */
public class Light {

    Vector3f color;
    Vector3f pos;

    public Light(Vector3f color, Vector3f pos) {
        this.color = color;
        this.pos = pos;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
}
