package hu.laci200270.games.sbs3djumper.models;


import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.Texture;
import hu.laci200270.games.sbs3djumper.utils.GLUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class ModelLoaderRegistry {
    private static ArrayList<IModelLoader> loaders=new ArrayList<IModelLoader>();
    private static HashMap<IModel,Texture> textures=new HashMap<>();
    private static IntBuffer textureIntBuffer= BufferUtils.createIntBuffer(1024);

    public static void registerModelLoader(IModelLoader loader) throws RuntimeException{
        for(IModelLoader current:loaders){
            if(current.getTypeFormat().equals(loader.getTypeFormat())){
                throw new RuntimeException("A model loader with this type is already registered!");
            }
        }
        loaders.add(loader);
    }

    public static IModel getModel(ResourceLocation loc, String type) throws IOException {

        for (IModelLoader current : loaders) {
            if (current.getTypeFormat().equals(type)) {
                IModel model = current.loadModel(loc);

                return model;
            }
        }
        return null;
    }


    public static Texture getTexture(IModel model,
                              int target,
                              int dstPixelFormat,
                              int minFilter,
                              int magFilter) throws IOException
    {
        int srcPixelFormat = 0;

        // create the texture ID for this texture

        int textureID = textureIntBuffer.get();
        Texture texture = new Texture(target,textureID);

        // bind this texture

        GL11.glBindTexture(target, textureID);

        BufferedImage bufferedImage = model.getTexture();
        texture.setWidth(bufferedImage.getWidth());
        texture.setHeight(bufferedImage.getHeight());

        if (bufferedImage.getColorModel().hasAlpha()) {
            srcPixelFormat = GL11.GL_RGBA;
        } else {
            srcPixelFormat = GL11.GL_RGB;
        }

        // convert that image into a byte buffer of texture data

        ByteBuffer textureBuffer = GLUtils.convertImageData(bufferedImage);

        if (target == GL11.GL_TEXTURE_2D)
        {
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
        }

        // produce a texture from the byte buffer

        GL11.glTexImage2D(target,
                0,
                dstPixelFormat,
                get2Fold(bufferedImage.getWidth()),
                get2Fold(bufferedImage.getHeight()),
                0,
                srcPixelFormat,
                GL11.GL_UNSIGNED_BYTE,
                textureBuffer );

        return texture;
    }
    private static int get2Fold(int fold) {
        int ret = 2;
        while (ret < fold) {
            ret *= 2;
        }
        return ret;
    }
    public static Texture getTexture(IModel model) throws IOException {
        Texture tex = textures.get(model);

        if (tex != null) {
            return tex;
        }

        tex = getTexture(model,
                GL11.GL_TEXTURE_2D, // target
                GL11.GL_RGBA,     // dst pixel format
                GL11.GL_LINEAR, // min filter (unused)
                GL11.GL_LINEAR);

        textures.put(model,tex);

        return tex;
    }

}
