package hu.laci200270.games.sbs3djumper.world;

import hu.laci200270.games.sbs3djumper.models.IModel;
import org.joml.Vector3f;

/**
 * Created by Laci on 2016. 02. 19..
 */
public class Planet implements IWorldPart {

    private final String texName;

    IModel planetModel = null;

    Vector3f posOnPrevTick = new Vector3f();

    Vector3f posOnCurrentTick = new Vector3f();

    int texutre;

    public Planet(String texName) {
        this.texName = texName;

    }


    @Override
    public void render() {

    }

    @Override
    public void onAnimationTick() {

    }

    @Override
    public void onWorldTick(World world) {

    }

    @Override
    public void setPosition(Vector3f pos) {

    }


}
