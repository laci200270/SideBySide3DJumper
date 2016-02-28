package hu.laci200270.games.sbs3djumper.world;

import org.joml.Vector3f;

/**
 * Created by Laci on 2016. 02. 19..
 */
public interface IWorldPart {


    public void render();

    public void onAnimationTick();

    public void onWorldTick(World world);

    public void setPosition(Vector3f pos);
}
