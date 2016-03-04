package hu.laci200270.games.sbs3djumper.world;

import hu.laci200270.games.sbs3djumper.ShaderProgram;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Laci on 2016. 02. 19..
 */
public abstract class WorldPart {

    Vector3f pos=new Vector3f();
    AxisAngle4f rot=new AxisAngle4f();
    Matrix4f modelMat=new Matrix4f();
    Vector3f scaling=new Vector3f();
    int uniqueId=-1;
    public int getUniqueId(){
        return uniqueId;
    }

    public void setUniqueId(int id){
        this.uniqueId=id;
    }

    public abstract void render(ShaderProgram shader);

    public abstract void onAnimationTick();

    public abstract void onWorldTick(World world);


    public  Matrix4f getTranslationMatrix(){
        return modelMat;
    }

    public void setWorldPos(Vector3f pos){

        if(!pos.equals(this.pos)) {
            modelMat = new Matrix4f();
            modelMat.translate(pos);
            modelMat.rotate(rot);
            modelMat.scale(scaling);
        }

        this.pos=pos;

    }

    public void setRotation(AxisAngle4f rotation){
        if(!rotation.equals(this.rot)) {
            modelMat = new Matrix4f();
            modelMat.translate(pos);
            modelMat.rotate(rotation);
            modelMat.scale(scaling);
        }

        this.rot=rotation;
    }

    public Vector3f getWorldPos(){
        return pos;
    }

    public AxisAngle4f getRotation(){
        return rot;
    }

    public void setScaling(Vector3f scaling){
        if(!this.scaling.equals(scaling)) {
            modelMat = new Matrix4f();
            modelMat.translate(pos);
            modelMat.rotate(rot);
            modelMat.scale(scaling);

        }

        this.scaling=scaling;
    }

    public Vector3f getScaling(){
        return scaling;
    }

}
