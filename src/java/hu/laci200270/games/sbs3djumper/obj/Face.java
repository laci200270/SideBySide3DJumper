package hu.laci200270.games.sbs3djumper.obj;

import hu.laci200270.games.sbs3djumper.Constants;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class Face {
    ArrayList<FacePoint> elements = new ArrayList<>();
    float red = Constants.random.nextFloat();
    float blue = Constants.random.nextFloat();
    float green = Constants.random.nextFloat();

    public Face(ArrayList<FacePoint> elements) {
        this.elements = elements;
    }

    public ArrayList<FacePoint> getElements() {
        return elements;
    }

    public void renderFace() {
        //GL11.glColor3f(red,green,blue);
        for (FacePoint current : elements) {
            if (elements.indexOf(current) % 2 == 0) {
                GL11.glColor3f(1, 1, 0);
            } else
                GL11.glColor3f(1, 0, 1);
            current.render();
        }
    }
}
