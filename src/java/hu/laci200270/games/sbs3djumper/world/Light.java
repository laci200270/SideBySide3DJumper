package hu.laci200270.games.sbs3djumper.world;

import org.joml.Vector3f;

/**
 * Created by Laci on 2016. 03. 12..
 */
public class Light {

    Vector3f color;
    Vector3f pos;
    Float strenght;
    EnumLightType type;

    public Light(Vector3f color, Vector3f pos, Float strenght,EnumLightType type) {
        this.color = color;
        this.pos = pos;
        this.strenght = strenght;
        this.type=type;
    }

    public Light(Vector3f color, Vector3f pos) {
        this(color, pos, 50000f,EnumLightType.POINT);

    }

    public Light(Vector3f pos) {
        this(new Vector3f(1),pos,1f,EnumLightType.POINT);

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
