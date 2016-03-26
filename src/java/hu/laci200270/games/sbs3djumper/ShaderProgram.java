package hu.laci200270.games.sbs3djumper;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Laci on 2016. 02. 12..
 */

public class ShaderProgram {

    private final int programId;
    HashMap<String, Integer> unformLocations = new HashMap();
    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderProgram() throws Exception {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
        link();
    }

    public ShaderProgram(String shaderName) {

        programId = glCreateProgram();


        try {
            createVertexShader(readResourceUntilEnd(new ResourceLocation(String.format("shaders/%s.vert", shaderName)).getInputStream()));
            createFragmentShader(readResourceUntilEnd(new ResourceLocation(String.format("shaders/%s.frag", shaderName)).getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getProgramId() {
        return programId;
    }

    public int getVertexShaderId() {
        return vertexShaderId;
    }

    public int getFragmentShaderId() {
        return fragmentShaderId;
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Code: " + shaderId);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetShaderInfoLog(programId, 1024));
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            throw new Exception("Error validating Shader code: " + glGetShaderInfoLog(programId, 1024));
        }

    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            if (vertexShaderId != 0) {
                glDetachShader(programId, vertexShaderId);
            }
            if (fragmentShaderId != 0) {
                glDetachShader(programId, fragmentShaderId);
            }
            glDeleteProgram(programId);
        }
    }

    public String readResourceUntilEnd(InputStream stream) {
        String returnable = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            String line = reader.readLine();
            while (line != null) {
                returnable += line + "\n";
                line = reader.readLine();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return returnable;
    }

    public int getUniformLocation(String uniformName) {
        bind();
        if (!unformLocations.containsKey(uniformName))
            unformLocations.put(uniformName, GL20.glGetUniformLocation(programId, uniformName));
        return unformLocations.get(uniformName) ;
    }

    public void setUnifromMatrix(String name, Matrix4f value) {
        bind();

        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        value.get(fb);
        glUniformMatrix4fv(getUniformLocation(name), false, fb);
    }

    public void setUniformInteger(String name,int value){
        GL20.glUniform1i(getUniformLocation(name),value);
    }

    public void setUniformVector3(String name,Vector3f value){
        bind();
        FloatBuffer fb=BufferUtils.createFloatBuffer(3);
        value.get(fb);
        glUniform3fv(getUniformLocation(name),fb);
    }

    public void setUniformFloat(String name, Float value) {
        bind();
        glUniform1f(getUniformLocation(name),value);
    }
}
