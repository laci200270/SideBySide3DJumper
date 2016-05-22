package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.Texture;
import hu.laci200270.games.sbs3djumper.renderer.EnumRenderState;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;

import java.awt.image.BufferedImage;

/**
 * Created by Laci on 2016. 02. 06..
 */
public interface IModel {
    /***
     * Renders the model
     * Called each frame
    */
    void render(MainRenderManager renderManager, EnumRenderState state);

    /***
     *
     *
     * @return The name of the texture for see @link{hu.laci200270.games.sbs3djumper.ResourceLocation}
     */
    String getTextureName();
    void setTextureName(String name);
    void setTexture(Texture texture);
    void loadTexture();

}
