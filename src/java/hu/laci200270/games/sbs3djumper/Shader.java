package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.utils.FileUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.io.IOException;

/**
 * Created by Laci on 2016. 02. 12..
 */
public class Shader {
    int id;
    public Shader(File shaderLoc,int shaderType){
        id= GL20.glCreateShader(shaderType);
        String shaderContents="";
        try {
            shaderContents= FileUtils.readFile(shaderLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GL20.glShaderSource(id,shaderContents);
        GL20.glCompileShader(id);


    }

    public void apply(int programId){
        GL20.glAttachShader(programId,id);

    }
}
