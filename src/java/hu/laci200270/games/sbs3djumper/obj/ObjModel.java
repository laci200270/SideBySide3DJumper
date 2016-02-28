package hu.laci200270.games.sbs3djumper.obj;

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

    int vertexBufferId = 0;

    int indicesBufferId = 0;
    int colorBufferId = 0;
    int numberOfVerts = 0;

    int numberOfPoint = 0;

    int vertexSize = 3;

    int colorSize = 2;

    int stride = (vertexSize + colorSize) * 4;
    int vboId = GL15.glGenBuffers();
    int colorVboId = GL15.glGenBuffers();
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



    public ObjModel(ArrayList<Vector4f> vertexes, ArrayList<Face> faces, ArrayList<Vector3f> normals, ArrayList<Vector3f> textures, ArrayList<Vector3f> points, ArrayList<Vector3f> indices) {
        this.vertexes = vertexes;
        this.faces = faces;
        this.normals = normals;
        this.textures = textures;

        List<Float> verts = new ArrayList<>();
        List<Float> colors = new ArrayList<>();
        GL30.glBindVertexArray(vaoId);
        for (Face face : faces) {
            for (FacePoint fpoint : face.elements) {
                verts.add(fpoint.point.x);
                verts.add(fpoint.point.y);
                verts.add(fpoint.point.z);
                verts.add(fpoint.point.w);
                colors.add(fpoint.color.x);
                colors.add(fpoint.color.y);
                colors.add(fpoint.color.z);
                numberOfVerts++;
            }
        }


        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verts.size());
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.size());

        for (float f : verts) {
            verticesBuffer.put(f);
        }
        for (float f : colors) {
            colorBuffer.put(f);
        }

        colorBuffer.flip();
        verticesBuffer.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);


    }

    public void render() {


        GL30.glBindVertexArray(vaoId);

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, numberOfVerts);
        GL20.glDisableVertexAttribArray(0);

        GL30.glBindVertexArray(0);
    }

    @Override
    public BufferedImage getTexture() {


        return textureBuffImg;
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    protected void finalize() throws Throwable {

        super.finalize();
    }
}
