package hu.laci200270.games.sbs3djumper.obj;

import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.Texture;
import hu.laci200270.games.sbs3djumper.models.IModel;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laci on 2016. 01. 30..
 */
public class ObjModel implements IModel {

    int numberOfVerts = 0;

    int vboId = GL15.glGenBuffers();
    int colorVboId = GL15.glGenBuffers();
    int texVboId = GL15.glGenBuffers();
    int normalVboId = GL15.glGenBuffers();
    int vaoId = GL30.glGenVertexArrays();
    private ArrayList<Vector4f> vertexes = new ArrayList<>();
    private ArrayList<Face> faces = new ArrayList<>();
    private ArrayList<Vector3f> normals = new ArrayList<>();
    private ArrayList<Vector3f> textures = new ArrayList<>();
    private ArrayList<Vector3f> points = new ArrayList<>();
    private ArrayList<Vector3f> indices = new ArrayList<>();
    private BufferedImage textureBuffImg;
    private int texId;
    private Texture texture;
    private String texName;


    public ObjModel(ArrayList<Vector4f> vertexes, ArrayList<Face> faces, ArrayList<Vector3f> normals, ArrayList<Vector3f> textures, ArrayList<Vector3f> points, ArrayList<Vector3f> indices) {
        this.vertexes = vertexes;
        this.faces = faces;
        this.normals = normals;
        this.textures = textures;

        List<Float> verts = new ArrayList<>();
        List<Float> colors = new ArrayList<>();
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

                //if(fpoint.normal==null)
                    fpoint.normal=getNormal(face.elements.get(0).point,face.elements.get(1).point,face.elements.get(2).point);

               /* colors.add(fpoint.color.x);
                colors.add(fpoint.color.y);
                colors.add(fpoint.color.z);*/
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
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.size());
        FloatBuffer normalBuffer=BufferUtils.createFloatBuffer(normalsArray.size());
        FloatBuffer texBuffer=BufferUtils.createFloatBuffer(texCoords.size());
        for (float f : verts) {
            verticesBuffer.put(f);
        }
        for (float f : colors) {
            colorBuffer.put(f);
        }
        for(float f: texCoords){
            texBuffer.put(f);
        }
        for(float f: normalsArray){
            normalBuffer.put(f);
        }
        colorBuffer.flip();
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

    public void render() {


        GL30.glBindVertexArray(vaoId);
        if (this.texture != null)
            texture.bind();
        else
            Constants.errorTexture.bind();

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

    @Override
    public String getTextureName() {
        return texName;
    }

    @Override
    public void setTextureName(String name) {
        texName=name;
    }


    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void loadTexture() {
        if (!this.texName.equals("")) {
            if (new ResourceLocation(String.format("textures/%s.png", texName)).getInputStream() != null)
                setTexture(new Texture(new ResourceLocation(String.format("textures/%s.png", texName))));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        GL30.glBindVertexArray(vaoId);
        GL15.glDeleteBuffers(vboId);
        GL15.glDeleteBuffers(colorVboId);
        GL15.glDeleteBuffers(texVboId);
        GL30.glDeleteVertexArrays(vaoId);
        super.finalize();
    }

    public Vector3f getNormal(Vector4f p1, Vector4f p2, Vector4f p3) {

        //Create normal vector we are going to output.
        Vector3f output = new Vector3f();

        //Calculate vectors used for creating normal (these are the edges of the triangle).
        Vector3f calU = new Vector3f(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        Vector3f calV = new Vector3f(p3.x-p1.x, p3.y-p1.y, p3.z-p1.z);

        //The output vector is equal to the cross products of the two edges of the triangle
        output.x = calU.y*calV.z - calU.z*calV.y;
        output.y = calU.z*calV.x - calU.x*calV.z;
        output.z = calU.x*calV.y - calU.y*calV.x;

        //Return the resulting vector.
        return output.normalize();
    }
}
