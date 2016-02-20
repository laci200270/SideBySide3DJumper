package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.ResourceLocation;

import java.io.IOException;

/**
 * Created by Laci on 2016. 02. 06..
 */
public interface IModelLoader {


    public IModel loadModel(ResourceLocation loc) throws IOException;
    public String getTypeFormat();

}
