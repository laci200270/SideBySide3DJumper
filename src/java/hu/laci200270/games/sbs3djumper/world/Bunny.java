package hu.laci200270.games.sbs3djumper.world;


import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.ModelRegistry;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;


/**
 * Created by diak on 2016.03.03..
 */
public class Bunny extends WorldPart {

    IModel bunnyModel=null;
    ShaderProgram shaderProgram=null;
    float rot=0f;
    public Bunny(){

        bunnyModel = ModelRegistry.getModel("eye.obj");
        setScaling(new Vector3f(1f));


    }


    @Override
    public void render(ShaderProgram shader) {

        shader.bind();
        regenModelMatrix();
        shader.setUnifromMatrix("modelMatrix",modelMat);
        bunnyModel.render();
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
}