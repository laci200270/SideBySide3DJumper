package hu.laci200270.games.sbs3djumper.world;

import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.Terrain;

/**
 * Created by Laci on 2016. 03. 24..
 */
public class TerrainWorldObject extends WorldPart {
    private final IModel model;

    public TerrainWorldObject(String name){
        this.model=new Terrain("szany",0,50,1);
    }

    @Override
    public void render(ShaderProgram shader) {
        shader.bind();
        regenModelMatrix();
        shader.setUnifromMatrix("modelMatrix",modelMat);
        model.render();
        shader.unbind();
    }

    @Override
    public void onWorldTick(World world) {

    }
}
