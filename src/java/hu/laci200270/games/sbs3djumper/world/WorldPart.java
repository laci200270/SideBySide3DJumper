package hu.laci200270.games.sbs3djumper.world;

import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.renderer.EnumRenderState;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Laci on 2016. 02. 19..
 */
public abstract class WorldPart {

    Vector3f pos=new Vector3f();
    AxisAngle4f rot=new AxisAngle4f();
    Vector3f scaling=new Vector3f();
    Matrix4f modelMat=new Matrix4f();

    int uniqueId=-1;
    public int getUniqueId(){
        return uniqueId;
    }

    public void setUniqueId(int id){
        this.uniqueId=id;
    }

    public abstract void render(ShaderProgram shader,MainRenderManager manager,EnumRenderState state);

    public void onAnimationTick(){

    }

    public abstract void onWorldTick(World world);


    public  Matrix4f getTranslationMatrix(){
        return modelMat;
    }

    public Vector3f getWorldPos() {
        return pos;
    }

    public void setWorldPos(Vector3f pos) {

        this.pos=pos;
        regenModelMatrix();

    }

    public AxisAngle4f getRotation(){
        return rot;
    }

    public void setRotation(AxisAngle4f rotation) {

        this.rot = rotation;
        regenModelMatrix();
    }

    public Vector3f getScaling() {
        return scaling;
    }

    public void setScaling(Vector3f scaling){


        this.scaling=scaling;
        regenModelMatrix();
    }

    public void regenModelMatrix(){
        modelMat = new Matrix4f();
        modelMat.translate(pos);
        modelMat.rotate(rot);
        modelMat.scale(scaling);

    }

    public abstract IModel getModel();


}
