package hu.laci200270.games.sbs3djumper.utils;

import hu.laci200270.games.sbs3djumper.Constants;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Hashtable;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class GLUtils {
    public static ByteBuffer convertImageData(BufferedImage bufferedImage) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace
                .getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8, 8},
                true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

        raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                bufferedImage.getWidth(), bufferedImage.getHeight(), 4, null);
        texImage = new BufferedImage(glAlphaColorModel, raster, true,
                new Hashtable());


        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, 256, 256);
        g.drawImage(bufferedImage, 0, 0, null);


        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer())
                .getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

    public static void drawFloor(float y) {
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3f(-10000, y, -10000);
        GL11.glVertex3f(-10000, y, 10000);
        GL11.glVertex3f(10000, y, 10000);
        GL11.glEnd();
    }

    public static FloatBuffer makeGoodBuffer(FloatBuffer badBuffer) {
        FloatBuffer retBuff = BufferUtils.createFloatBuffer(badBuffer.capacity());
        retBuff.put(badBuffer);
        retBuff.flip();
        return retBuff;
    }

    public void translate(Vector3f trans) {
        if (Constants.useShaders) {

        }
    }

}
