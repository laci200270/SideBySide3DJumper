package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.Texture;
import hu.laci200270.games.sbs3djumper.renderer.EnumRenderState;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Laci on 2016. 03. 25..
 */
public class PrimitiveModel extends AbstractModel {

    private final int numberOfVerts;
    int indicesVboId=GL15.glGenBuffers();

    public PrimitiveModel(float[] verts,int[] indices,float[] texCoords,float[] normals,ResourceLocation texture ,int lenght) {
        /*try {
            System.out.println("Exporting.");

            PrintWriter writer=new PrintWriter(new FileWriter("terrain.obj"));
            writer.println("#START");
            for(int i=0;i<verts.length;i+=4){
                writer.println(String.format("v %s %s %s",verts[i],verts[i+1],verts[i+2]));
            }
            for(int i=0;i<indices.length;i+=3){
                writer.println(String.format("f %s %s %s",indices[i]+1,indices[i+1]+1,indices[i+2]+1));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Experting done");*/
        GL30.glBindVertexArray(vaoId);
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
        //indicesBuffer.rewind();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, lenght, GL11.GL_FLOAT, true, 0, 0);
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
        if(texture!=null)
            this.texture=new Texture(texture);
        numberOfVerts=indices.length;
        GL30.glBindVertexArray(0);
    }

    @Override
    public void render(MainRenderManager manager,EnumRenderState state) {
       switch (state) {
           case PRE:
               GL30.glBindVertexArray(vaoId);
           if (this.texture != null)
               texture.bind();
           //else
               //Constants.errorTexture.bind();
               System.out.println("Primtivemodel PRE");
           GL20.glEnableVertexAttribArray(0);
           GL20.glEnableVertexAttribArray(2);

           GL20.glEnableVertexAttribArray(1);
            break;

          case DRAW:
              System.out.println("Primtivemodel DRAW");
              if (manager.isInWireFrameMode())
               GL11.glDrawElements(GL11.GL_LINE_STRIP, numberOfVerts, GL11.GL_UNSIGNED_INT, 0);
           else
               GL11.glDrawElements(GL11.GL_TRIANGLES, numberOfVerts, GL11.GL_UNSIGNED_INT, 0);
                 //GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, numberOfVerts*32);
                  break;
            case POST:
                System.out.println("Primtivemodel POST");
           GL20.glDisableVertexAttribArray(0);
           GL20.glDisableVertexAttribArray(1);
           GL20.glDisableVertexAttribArray(2);
           GL30.glBindVertexArray(0);
                break;
       }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrimitiveModel)) return false;

        PrimitiveModel that = (PrimitiveModel) o;

        if (indicesVboId != that.indicesVboId) return false;
        return numberOfVerts == that.numberOfVerts;

    }

    @Override
    public int hashCode() {
        int result = numberOfVerts;
        result = 31 * result + indicesVboId;
        return result;
    }
}
