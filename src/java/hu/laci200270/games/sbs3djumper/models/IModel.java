package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.Texture;

import java.awt.image.BufferedImage;

/**
 * Created by Laci on 2016. 02. 06..
 */
public interface IModel {
    /***
     * Renders the model
     * Called each frame
    */
    public void render();

    /***
     *
     *
     * @return The name of the texture for see @link{hu.laci200270.games.sbs3djumper.ResourceLocation}
     */
    public String getTextureName();
    public void setTextureName(String name);
    public void setTexture(Texture texture);
    public void loadTexture();

}
