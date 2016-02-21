package hu.laci200270.games.sbs3djumper.world;

import com.hackoeur.jglm.Vec3;
import hu.laci200270.games.sbs3djumper.models.IModel;

/**
 * Created by Laci on 2016. 02. 19..
 */
public class Planet implements IWorldPart {

    private final String texName;

    IModel planetModel = null;

    Vec3 posOnPrevTick = new Vec3();

    Vec3 posOnCurrentTick = new Vec3();

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
    public void setPosition(Vec3 pos) {

    }


}
