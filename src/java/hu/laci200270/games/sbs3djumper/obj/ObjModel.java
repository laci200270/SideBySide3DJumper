package hu.laci200270.games.sbs3djumper.obj;

import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.Texture;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.utils.GLUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;

/**
 * Created by Laci on 2016. 01. 30..
 */
public class ObjModel implements IModel {


    private ArrayList<Vertex4F> vertexes = new ArrayList<>();

    private ArrayList<Face> faces = new ArrayList<>();

    private ArrayList<Vertex3F> normals = new ArrayList<>();

    private ArrayList<Vertex3F> textures = new ArrayList<>();

    private BufferedImage textureBuffImg;

    private int texId;

    private Texture texture;

    private int VBOid;
    int numberOfVerts=0;
    int numberOfPoint=0;
    int vertexSize = 3;
    int colorSize = 2;
    int stride = (vertexSize + colorSize) * 4;

    public ObjModel(ArrayList<Vertex4F> vertexes, ArrayList<Face> faces, ArrayList<Vertex3F> normals, ArrayList<Vertex3F> textures) {
        this.vertexes = vertexes;
        this.faces = faces;
        this.normals = normals;
        this.textures = textures;

        if(Constants.useVBOs){

            for(Face face: faces){
                numberOfVerts+=face.getElements().size()*3;
                numberOfPoint+=face.getElements().size();
            }
            VBOid=GL15.glGenBuffers();



           // int texSize=3;


            float[] vertexArray = new float[]{-0.5f, -0.5f, 0,1, 1,0,0, 0.5f, -0.5f, 0,1, 0,1,0, 0.5f, 0.5f, 0,1, 0,0,1, -0.5f, 0.5f, 0,1, 1,1,1};

            ArrayList<Float> floats=new ArrayList<>();
            for(Face face:faces){
                for(FacePoint point: face.elements){
                    floats.add(point.point.getX());
                    floats.add(point.point.getY());
                    floats.add(point.point.getZ());
                    //floats.add(point.point.getW());
                    floats.add(point.color.getX());
                    floats.add(point.color.getY());
                   //floats.add(point.color.getZ());
//                    vertBuff.put(new float[]{point.texture.getX(),point.texture.getZ()})
                    //textureBuff.put(new float[]{point.texture.getX(),point.texture.getY(),point.texture.getZ()});
                }
            }
            //vertBuff.flip();
            //textureBuff.flip();
            FloatBuffer vertBuff=BufferUtils.createFloatBuffer(floats.size());
            for(Float float1:floats){
                vertBuff.put(float1);
            }
            //vertBuff.flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOid);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER,vertBuff,GL15.GL_STATIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);

        }

        try {
           this.textureBuffImg=ImageIO.read(new ResourceLocation("KnifeD.png").getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        if(Constants.useVBOs){

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_COLOR_ARRAY);
           // glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            glDrawArrays(GL_TRIANGLES, 0, numberOfVerts);
            //glDisableClientState(GL_TEXTURE_COORD_ARRAY);
            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);


        }
        else {

            GL11.glBegin(GL11.GL_TRIANGLES);
            // GL11.glColor3d(128,128,0);
            //GL11.glBindTexture(GL11.);
//        texture.bind();
            for (Face face : faces) {
                face.renderFace();
            }


            GL11.glEnd();
        }
    }

    @Override
    public BufferedImage getTexture() {


        return textureBuffImg;
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture=texture;
    }

    @Override
    protected void finalize() throws Throwable {
        if(Constants.useVBOs){
            GL15.glDeleteBuffers(VBOid);
        }
        super.finalize();
    }
}
