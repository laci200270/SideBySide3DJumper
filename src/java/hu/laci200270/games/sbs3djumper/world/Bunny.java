package hu.laci200270.games.sbs3djumper.world;


import com.sun.prism.ps.Shader;
import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.ModelRegistry;
import org.joml.Vector3f;


/**
 * Created by diak on 2016.03.03..
 */
public class Bunny extends WorldPart {

    IModel bunnyModel=null;
    ShaderProgram shaderProgram=null;
    public Bunny(){

        bunnyModel= ModelRegistry.getModel("stanfordbunny.obj");
        setWorldPos(new Vector3f(0,-0.10f,-10));
        setScaling(new Vector3f(1f));


    }


    @Override
    public void render(ShaderProgram shader) {

        shader.bind();
        shader.setUnifromMatrix("modelMatrix",modelMat);
        bunnyModel.render();
        shader.unbind();
    }

    @Override
    public void onAnimationTick() {

    }

    @Override
    public void onWorldTick(World world) {

    }
}
