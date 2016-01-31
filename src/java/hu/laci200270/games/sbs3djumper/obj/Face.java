package hu.laci200270.games.sbs3djumper.obj;

import java.util.ArrayList;

/**
 * Created by Laci on 2016. 01. 31..
 */
public class Face {
    ArrayList<FacePoint> elements=new ArrayList<>();

    public Face(ArrayList<FacePoint> elements) {
        this.elements = elements;
    }

    public ArrayList<FacePoint> getElements() {
        return elements;
    }
}
