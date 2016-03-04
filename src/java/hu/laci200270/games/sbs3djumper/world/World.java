package hu.laci200270.games.sbs3djumper.world;

import hu.laci200270.games.sbs3djumper.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laci on 2016. 02. 19..
 */
public class World {
    List<WorldPart> parts = new ArrayList<>();
    ShaderProgram normalShader;

    public World(ShaderProgram shader) {
        normalShader=shader;
    }

    public void render() {
        for (WorldPart part : parts) {
            part.render(normalShader);
        }
    }

    public List<WorldPart> getParts() {
        return parts;
    }

    public void addWorldPart(WorldPart part){
        if(part!=null)
            parts.add(null);
            int index=parts.indexOf(null);
            part.setUniqueId(index);
            parts.set(index,part);
    }
}
