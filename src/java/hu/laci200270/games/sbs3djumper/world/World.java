package hu.laci200270.games.sbs3djumper.world;

import hu.laci200270.games.sbs3djumper.ShaderProgram;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.renderer.EnumRenderState;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Laci on 2016. 02. 19..
 */
public class World {
    List<WorldPart> parts = new ArrayList<>();
    List<Light> lights = new ArrayList<>();
    ShaderProgram normalShader;
    private boolean lighChanged = true;

    public World(ShaderProgram shader) {
        normalShader = shader;
    }

    public void render(MainRenderManager manager, boolean optimazitionsEnabled) {
        if (optimazitionsEnabled) {
            List<IModel> models = new ArrayList<>();
            for (WorldPart part : parts) {
                IModel model=part.getModel();
                if(!models.contains(model))
                    models.add(model);
            }
            for(IModel model:models){
                model.render(manager,EnumRenderState.PRE);
                for (WorldPart part:parts){
                    if(part.getModel().equals(model))
                        part.render(normalShader,manager,EnumRenderState.DRAW);
                }
                model.render(manager,EnumRenderState.POST);

            }
        } else {
            for (WorldPart part : parts) {
                normalShader.setUniformInteger("lightCount", lights.size());
                if (lighChanged) {
                    lighChanged = false;
                    for (int i = 0; i < lights.size(); i++) {
                        normalShader.setUniformVector3("lights[" + i + "].color", lights.get(i).color);
                        normalShader.setUniformVector3("lights[" + i + "].location", lights.get(i).pos);
                        normalShader.setUniformInteger("lights[" + i + "].type", Arrays.asList(EnumLightType.values()).indexOf(lights.get(i)));
                        normalShader.setUniformFloat("lights[" + i + "].strenght", lights.get(i).strenght);
                    }
                }
                part.render(normalShader, manager, EnumRenderState.PRE);
                part.render(normalShader, manager, EnumRenderState.DRAW);
                part.render(normalShader, manager, EnumRenderState.POST);
            }
        }
    }

    public List<WorldPart> getParts() {
        return parts;
    }

    public void addWorldPart(WorldPart part) {
        if (part != null)
            parts.add(null);
        int index = parts.indexOf(null);
        part.setUniqueId(index);
        parts.set(index, part);
    }


    public void addLight(Light light) {
        lighChanged = true;
        if (light != null)
            lights.add(light);
    }


}
