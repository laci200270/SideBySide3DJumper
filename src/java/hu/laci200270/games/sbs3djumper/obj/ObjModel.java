package hu.laci200270.games.sbs3djumper.obj;

import hu.laci200270.games.sbs3djumper.Texture;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.utils.GLUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
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


    public ObjModel(ArrayList<Vertex4F> vertexes, ArrayList<Face> faces, ArrayList<Vertex3F> normals, ArrayList<Vertex3F> textures) {
        this.vertexes = vertexes;
        this.faces = faces;
        this.normals = normals;
        this.textures = textures;
        try {
            this.textureBuffImg=ImageIO.read(new File("d:/knife/KnifeD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        GL11.glBegin(GL11.GL_TRIANGLES);
        // GL11.glColor3d(128,128,0);
        //GL11.glBindTexture(GL11.);
        texture.bind();
        for (Face face : faces) {
            face.renderFace();
        }


        GL11.glEnd();
    }

    @Override
    public BufferedImage getTexture() {


        return textureBuffImg;
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture=texture;
    }


}
