package hu.laci200270.games.sbs3djumper;

import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Laci on 2016. 02. 12..
 */
public class Shader {
    int id;

    public Shader(ResourceLocation shaderLoc, int shaderType) {
        id = GL20.glCreateShader(shaderType);
        String shaderContents = "";
        try {
            shaderContents = readStreamUntilEnd(shaderLoc.inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GL20.glShaderSource(id, shaderContents);
        GL20.glCompileShader(id);

    }

    private static String readStreamUntilEnd(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String currentLine = null;
        currentLine = reader.readLine();
        while (currentLine != null) {
            builder.append(currentLine);
            builder.append('\n');
            currentLine = reader.readLine();
        }
        return builder.toString();
    }

    public void apply(int programId) {
        GL20.glAttachShader(programId, id);

    }
}
