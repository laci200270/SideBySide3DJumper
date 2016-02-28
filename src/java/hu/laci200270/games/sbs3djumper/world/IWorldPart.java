package hu.laci200270.games.sbs3djumper.world;

import org.joml.Matrix4f;

/**
 * Created by Laci on 2016. 02. 19..
 */
public interface IWorldPart {

    public int getUniqueId();

    public void setUniqueId(long id);

    public void render();

    public void onAnimationTick();

    public void onWorldTick(World world);

    public void takeTranslationMatrix(Matrix4f matrix4f);

    public Matrix4f getTranslationMatrix();
}
