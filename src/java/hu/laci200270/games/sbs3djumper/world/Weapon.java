package hu.laci200270.games.sbs3djumper.world;

import hu.laci200270.games.sbs3djumper.Camera;
import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.ModelRegistry;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Laci on 2016. 03. 05..
 */
public class Weapon extends WorldPart {

    Camera camera = null;
    IModel model = null;

    public Weapon(Camera camera, String name, Vector3f scaling) {
        this.camera = camera;
        this.setScaling(scaling);
        model = ModelRegistry.getModel(name);
    }

    @Override
    public void render(ShaderProgram shader) {

        shader.bind();

        Matrix4f matrix = new Matrix4f().identity();
        matrix.scale(new Vector3f(0.000005f));
        /*matrix.rotate((float) Math.toRadians(180), 0, 1, 0);
        matrix.rotate((float) Math.toRadians(camera.getPitch()), 1.0f, 0.0f, 0.0f);*/
        matrix.rotate((float) Math.toRadians(camera.getYaw()), 0.0f, 1.0f, 0.0f);
        matrix.setTranslation(camera.getPos().mul(-1));

        // matrix.setTranslation(camera.getPos().mul(-1f).add(-0.003f, -0.0004f, -0.03f));
        shader.setUnifromMatrix("modelMatrix", matrix);
        model.render();
        shader.unbind();
    }

    @Override
    public void onWorldTick(World world) {

    }

}
