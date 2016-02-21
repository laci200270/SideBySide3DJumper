package hu.laci200270.games.sbs3djumper.obj;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.Texture;
import hu.laci200270.games.sbs3djumper.models.IModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

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

    private ArrayList<Vec4> vertexes = new ArrayList<>();

    private ArrayList<Face> faces = new ArrayList<>();

    private ArrayList<Vec3> normals = new ArrayList<>();

    private ArrayList<Vec3> textures = new ArrayList<>();

    private ArrayList<Vec3> points = new ArrayList<>();

    private ArrayList<Vec3> indices = new ArrayList<>();

    private BufferedImage textureBuffImg;

    private int texId;

    private Texture texture;

    private int VBOid;

    public ObjModel(ArrayList<Vec4> vertexes, ArrayList<Face> faces, ArrayList<Vec3> normals, ArrayList<Vec3> textures, ArrayList<Vec3> points, ArrayList<Vec3> indices) {
        this.vertexes = vertexes;
        this.faces = faces;
        this.normals = normals;
        this.textures = textures;

        if (Constants.useVBOs) {


            vertexBufferId = GL15.glGenBuffers();
            indicesBufferId = GL15.glGenBuffers();
            colorBufferId = GL15.glGenBuffers();

            ArrayList<Float> floats = new ArrayList<>();
            for (Face face : faces) {
                for (FacePoint point : face.elements) {
                    floats.add(point.point.getX());
                    floats.add(point.point.getY());
                    floats.add(point.point.getZ());

                }
            }

            FloatBuffer vertBuff = BufferUtils.createFloatBuffer(points.size() * 3);
            IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.size() * 3);
            for (Vec3 point : points) {
                float[] coords = {point.getX(), point.getY(), point.getZ()};
                System.out.println(Arrays.toString(coords));
                vertBuff.put(coords);
            }

            for (Vec3 indice : indices) {
                int[] coords = {(int) indice.getX(), (int) indice.getY(), (int) indice.getZ()};
                System.out.println(Arrays.toString(coords));
                indicesBuffer.put(coords);
            }

            vertBuff.flip();
            indicesBuffer.flip();

            float[] colors = {1, 0, 1, 1};
            FloatBuffer colorBuff=BufferUtils.createFloatBuffer(colors.length);
            colorBuff.put(colors);
            colorBuff.flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferId);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuff, GL15.GL_ARRAY_BUFFER);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer,
                    GL15.GL_STATIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, colorBufferId);
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, colorBuff,
                    GL15.GL_STATIC_DRAW);

        }


    }

    public void render() {
        if (Constants.useVBOs) {

            GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferId);
            GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);

            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferId);
            GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, indicesBufferId);

            GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, indices.size(), indices.size()/3,
                    GL11.GL_UNSIGNED_INT, 0);


        } else {

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
        this.texture = texture;
    }

    @Override
    protected void finalize() throws Throwable {
        if (Constants.useVBOs) {
            GL15.glDeleteBuffers(VBOid);
        }
        super.finalize();
    }
}
