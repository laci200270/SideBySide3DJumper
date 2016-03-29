package hu.laci200270.games.sbs3djumper.world;

import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.Terrain;
import hu.laci200270.games.sbs3djumper.renderer.EnumRenderState;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;

/**
 * Created by Laci on 2016. 03. 24..
 */
public class TerrainWorldObject extends WorldPart {
    private final IModel model;

    public TerrainWorldObject(String name){
        this.model=new Terrain(name,0,1f,1);
    }

    @Override
    public void render(ShaderProgram shader,MainRenderManager manager,EnumRenderState state) {


            shader.bind();
            regenModelMatrix();
            shader.setUnifromMatrix("modelMatrix", modelMat);
            model.render(manager,state);
            shader.unbind();

    }

    @Override
    public void onWorldTick(World world) {

    }

    @Override
    public IModel getModel() {
        return model;
    }
}
