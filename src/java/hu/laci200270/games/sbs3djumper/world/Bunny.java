package hu.laci200270.games.sbs3djumper.world;


import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.ModelRegistry;
import hu.laci200270.games.sbs3djumper.renderer.EnumRenderState;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;


/**
 * Created by diak on 2016.03.03..
 */
public class Bunny extends WorldPart {

    IModel bunnyModel=null;

    float rot=0f;
    public Bunny(){

        bunnyModel = ModelRegistry.getModel("eye.obj");
        setScaling(new Vector3f(1f));


    }


    @Override
    public void render(ShaderProgram shader,MainRenderManager manager,EnumRenderState state) {

        shader.bind();
        regenModelMatrix();
        shader.setUnifromMatrix("modelMatrix",modelMat);
        bunnyModel.render(manager,state);
        shader.unbind();
    }

    @Override
    public void onAnimationTick() {
        rot+=0.01f;
        this.setRotation(new AxisAngle4f(rot,0,1,0));
        regenModelMatrix();

    }

    @Override
    public void onWorldTick(World world) {

    }

    @Override
    public IModel getModel() {
        return bunnyModel;
    }
}