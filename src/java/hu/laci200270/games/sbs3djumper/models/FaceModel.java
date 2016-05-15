package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.Texture;
import hu.laci200270.games.sbs3djumper.models.AbstractModel;
import hu.laci200270.games.sbs3djumper.models.Face;
import hu.laci200270.games.sbs3djumper.models.FacePoint;
import hu.laci200270.games.sbs3djumper.renderer.EnumRenderState;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laci on 2016. 01. 30..
 */
public class FaceModel extends AbstractModel {

    public static Texture errorTexture=new Texture(new ResourceLocation("textures/error.png"));

    int numberOfVerts = 0;

    public FaceModel(List<Face> faces) {


        List<Float> verts = new ArrayList<>();
        List<Float> texCoords=new ArrayList<>();
        List<Float> normalsArray=new ArrayList<>();
        GL30.glBindVertexArray(vaoId);
        for (Face face : faces) {
            for (int i=0;i<face.elements.size();i++) {
                FacePoint fpoint=face.elements.get(i);
                verts.add(fpoint.point.x);
                verts.add(fpoint.point.y);
                verts.add(fpoint.point.z);
                verts.add(fpoint.point.w);

                if(fpoint.normal==null)
                    fpoint.normal=face.generateNormalFace();
                if (fpoint.texture != null) {
                    texCoords.add(fpoint.texture.x);
                    texCoords.add(fpoint.texture.y);
                }
                else{
                    if(i%2==0) {
                        texCoords.add(0f);
                        texCoords.add(1f);
                    }
                    else{
                        texCoords.add(1f);
                        texCoords.add(1f);
                    }

                }

                    normalsArray.add(fpoint.normal.x);
                    normalsArray.add(fpoint.normal.y);
                    normalsArray.add(fpoint.normal.z);


                numberOfVerts++;
            }
        }


        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verts.size());
        FloatBuffer normalBuffer=BufferUtils.createFloatBuffer(normalsArray.size());
        FloatBuffer texBuffer=BufferUtils.createFloatBuffer(texCoords.size());
        for (float f : verts) {
            verticesBuffer.put(f);
        }
        for(float f: texCoords){
            texBuffer.put(f);
        }
        for(float f: normalsArray){
            normalBuffer.put(f);
        }
        verticesBuffer.flip();
        texBuffer.flip();
        normalBuffer.flip();
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

        GL30.glBindVertexArray(0);


    }

    public void render(MainRenderManager manager,EnumRenderState state) {

        System.out.format("Rendering FaceModel at state %s \n",state);
         GL30.glBindVertexArray(vaoId);
        if (this.texture != null)
            texture.bind();
        else
           errorTexture.bind();

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(2);

         GL20.glEnableVertexAttribArray(1);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, numberOfVerts);
        GL20.glDisableVertexAttribArray(0);
        if(texture!=null)
         GL20.glDisableVertexAttribArray(1);

        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }


}
