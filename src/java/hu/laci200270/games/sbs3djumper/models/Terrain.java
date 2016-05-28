package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.models.*;
import hu.laci200270.games.sbs3djumper.renderer.EnumRenderState;
import hu.laci200270.games.sbs3djumper.renderer.MainRenderManager;
import hu.laci200270.games.sbs3djumper.world.Light;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Laci on 2016. 03. 24..
 */
public class Terrain extends AbstractModel{

    float heights[][]={{1,0,1},{0,1,0}};
    int numberOfVerts;
    IModel model;
    float minY,maxY;
    private static final int MAX_COLOUR = 255 * 255 * 255;

    private static final float STARTX = -5f;

    private static final float STARTZ = -5f;
    public Terrain(String name,float minY,float maxY, int textInc){


            this.maxY=maxY;
            this.minY=minY;
        BufferedImage buffImage = null;
        try {
            buffImage = ImageIO.read(new ResourceLocation(String.format("heightmaps/%s.png", name)).getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loading image done.");
        int height = buffImage.getHeight();
        int width = buffImage.getWidth();



        float incx = getXLength() / (width - 1);
        float incz = getZLength() / (height - 1);

        List<Float> positions = new ArrayList();
        List<Float> textCoords = new ArrayList();
        List<Integer> indices = new ArrayList();
        System.out.println("Constructing positons");
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Create vertex for current position
                positions.add(STARTX + col * incx); // x
                positions.add(getHeight(col, row, buffImage)); //y
                positions.add(STARTZ + row * incz); //z
                positions.add(1f);
                // Set texture coordinates
                textCoords.add((float) textInc * (float) col / (float) width);
                textCoords.add((float) textInc * (float) row / (float) height);

                // Create indices
                if (col < width - 1 && row < height - 1) {
                    int leftTop = row * width + col;
                    int leftBottom = (row + 1) * width + col;
                    int rightBottom = (row + 1) * width + col + 1;
                    int rightTop = row * width + col + 1;

                    indices.add(leftTop);
                    indices.add(leftBottom);
                    indices.add(rightTop);

                    indices.add(rightTop);
                    indices.add(leftBottom);
                    indices.add(rightBottom);
                }
            }
        }
        System.out.println("Constructiong positions done.");
        float[] posArr = new float[positions.size()];
        for (int i = 0; i < positions.size() ; i++) {
            posArr[i]=positions.get(i);
        }
        int[] indicesArr = indices.stream().mapToInt(i -> i).toArray();
        float[] texcoordarray = new float[textCoords.size()];
        for (int i = 0; i < textCoords.size() ; i++) {
            texcoordarray[i]=textCoords.get(i);
        }
        float[] normalsArr = calcNormals(posArr, width, height);

        model=new PrimitiveModel(posArr,indicesArr,texcoordarray,normalsArr, new ResourceLocation("textures/terrain.png"),4);


    }



    @Override
    public void render(MainRenderManager manager,EnumRenderState state) {
       model.render(manager,state);
    }



    private float getBrightnessOfPixel(int x, int y, BufferedImage image){
        int[] asd=new int[4];
        System.out.println(String.format("Scanning %s %s",x,y));
        image.getData().getPixel(x,y, asd);
        return asd[0]/255;

    }

    public static float getXLength() {
        return Math.abs(-STARTX*2);
    }

    public static float getZLength() {
        return Math.abs(-STARTZ*2);
    }

    private float getHeight(int x, int z, BufferedImage buffImage) {
        float result = 0;
        if (x >= 0 && x < buffImage.getWidth() && z >= 0 && z < buffImage.getHeight()) {
            int rgb = buffImage.getRGB(x, z);
            result = this.minY + Math.abs(this.maxY - this.minY) * ((float) rgb / (float) MAX_COLOUR);
        }
        return result;
    }

    private float[] calcNormals(float[] posArr, int width, int height) {
        Vector3f v0 = new Vector3f();
        Vector3f v1 = new Vector3f();
        Vector3f v2 = new Vector3f();
        Vector3f v3 = new Vector3f();
        Vector3f v4 = new Vector3f();
        Vector3f v12 = new Vector3f();
        Vector3f v23 = new Vector3f();
        Vector3f v34 = new Vector3f();
        Vector3f v41 = new Vector3f();
        List<Float> normals = new ArrayList<>();
        Vector3f normal = new Vector3f();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row > 0 && row < height -1 && col > 0 && col < width -1) {
                    int i0 = row*width*3 + col*3;
                    v0.x = posArr[i0];
                    v0.y = posArr[i0 + 1];
                    v0.z = posArr[i0 + 2];

                    int i1 = row*width*3 + (col-1)*3;
                    v1.x = posArr[i1];
                    v1.y = posArr[i1 + 1];
                    v1.z = posArr[i1 + 2];
                    v1 = v1.sub(v0);

                    int i2 = (row+1)*width*3 + col*3;
                    v2.x = posArr[i2];
                    v2.y = posArr[i2 + 1];
                    v2.z = posArr[i2 + 2];
                    v2 = v2.sub(v0);

                    int i3 = (row)*width*3 + (col+1)*3;
                    v3.x = posArr[i3];
                    v3.y = posArr[i3 + 1];
                    v3.z = posArr[i3 + 2];
                    v3 = v3.sub(v0);

                    int i4 = (row-1)*width*3 + col*3;
                    v4.x = posArr[i4];
                    v4.y = posArr[i4 + 1];
                    v4.z = posArr[i4 + 2];
                    v4 = v4.sub(v0);

                    v1.cross(v2, v12);
                    v12.normalize();

                    v2.cross(v3, v23);
                    v23.normalize();

                    v3.cross(v4, v34);
                    v34.normalize();

                    v4.cross(v1, v41);
                    v41.normalize();

                    normal = v12.add(v23).add(v34).add(v41);
                    normal.normalize();
                } else {
                    normal.x = 0;
                    normal.y = 1;
                    normal.z = 0;
                }
                normal.normalize();
                normals.add(normal.x);
                normals.add(normal.y);
                normals.add(normal.z);
            }
        }
        float[] normalArray=new float[normals.size()];
        for (int i = 0; i < normals.size(); i++) {
            normalArray[i]=normals.get(i);
        }
        return normalArray;
    }

}
