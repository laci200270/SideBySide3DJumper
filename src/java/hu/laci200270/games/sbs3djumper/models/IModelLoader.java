package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.ResourceLocation;

import java.io.IOException;

/**
 * Created by Laci on 2016. 02. 06..
 */
public interface IModelLoader {


    /**
     * @param loc the location of the model that should get loaded
     * @return the loaded model, or null if there was an error
     * @throws IOException if there was error while loading inputstream
     */
    IModel loadModel(ResourceLocation loc) throws IOException;

    /**
     * @return the file extension what this loader should handle
     */
    String getTypeFormat();

}
