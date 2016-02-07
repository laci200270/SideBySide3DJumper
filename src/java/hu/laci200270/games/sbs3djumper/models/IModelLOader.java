package hu.laci200270.games.sbs3djumper.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Laci on 2016. 02. 06..
 */
public interface IModelLoader {


    public IModel loadModel(File file) throws IOException;
    public String getTypeFormat();

}
