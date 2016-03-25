package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Laci on 2016. 03. 25..
 */
public class PrimitiveModel extends AbstractModel {

    private final int numberOfVerts;
    int indicesVboId=GL15.glGenBuffers();

    public PrimitiveModel(float[] verts,int[] indices,float[] texCoords,float[] normals,ResourceLocation texture) {

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verts.length);
        FloatBuffer normalBuffer=BufferUtils.createFloatBuffer(normals.length);
        FloatBuffer texBuffer=BufferUtils.createFloatBuffer(texCoords.length);
        IntBuffer indicesBuffer=BufferUtils.createIntBuffer(indices.length);
        verticesBuffer.put(verts);
        normalBuffer.put(normals);
        texBuffer.put(texCoords);
        indicesBuffer.put(indices);
        verticesBuffer.flip();
        texBuffer.flip();
        normalBuffer.flip();
        indicesBuffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texVboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalVboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVboId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        numberOfVerts=indices.length;
        GL30.glBindVertexArray(0);
    }

    @Override
    public void render() {
        GL30.glBindVertexArray(vaoId);
        if (this.texture != null)
            texture.bind();
        else
            Constants.errorTexture.bind();

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(2);

        GL20.glEnableVertexAttribArray(1);

        GL11.glDrawElements(GL11.GL_TRIANGLES, numberOfVerts, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        if(texture!=null)
            GL20.glDisableVertexAttribArray(1);

        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
}
