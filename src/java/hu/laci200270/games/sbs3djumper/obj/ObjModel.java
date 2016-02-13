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

    public ObjModel(ArrayList<Vertex4F> vertexes, ArrayList<Face> faces, ArrayList<Vertex3F> normals, ArrayList<Vertex3F> textures) {
        this.vertexes = vertexes;
        this.faces = faces;
        this.normals = normals;
        this.textures = textures;

        if(Constants.useVBOs){

            for(Face face: faces){
                numberOfVerts+=face.getElements().size()*4;
                numberOfPoint+=face.getElements().size();
            }
            VBOid=GL15.glGenBuffers();
            FloatBuffer vertBuff= BufferUtils.createFloatBuffer(numberOfVerts);
            FloatBuffer textureBuff=  BufferUtils.createFloatBuffer(numberOfPoint*3);
            for(Face face:faces){
                for(FacePoint point: face.elements){
                    vertBuff.put(new float[]{point.point.getX(),point.point.getY(),point.point.getZ(),point.point.getW()});
                    //textureBuff.put(new float[]{point.texture.getX(),point.texture.getY(),point.texture.getZ()});
                }
            }
            vertBuff.flip();
            textureBuff.flip();

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOid);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER,vertBuff,GL15.GL_STATIC_DRAW);


        }

        try {
           this.textureBuffImg=ImageIO.read(new ResourceLocation("KnifeD.png").getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        if(Constants.useVBOs){
            // Bind buffers
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOid);

            // Enable attribute types
            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
            GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
            // Bind attribute types
            GL11.glNormalPointer(GL11.GL_FLOAT, 32, 12);
            GL11.glColorPointer(4, GL11.GL_BYTE, 32, 24);
            GL11.glVertexPointer(numberOfVerts, GL11.GL_FLOAT, 32, 0);
            // Draw attributes from GL_ARRAY_BUFFER using indices from GL_ELEMENT_ARRAY_BUFFER
            GL11.glDrawElements(GL11.GL_TRIANGLES, numberOfVerts, GL11.GL_INT, 0);
            // Disable attribute types
            GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
            GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
            GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            // Unbind buffers
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

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
