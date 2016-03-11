package hu.laci200270.games.sbs3djumper.models;


import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.Texture;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class ModelLoaderRegistry {
    private static ArrayList<IModelLoader> loaders = new ArrayList<IModelLoader>();
    private static HashMap<IModel, Texture> textures = new HashMap<>();
    private static IntBuffer textureIntBuffer = BufferUtils.createIntBuffer(1024);

    public static void registerModelLoader(IModelLoader loader) throws RuntimeException {
        for (IModelLoader current : loaders) {
            if (current.getTypeFormat().equals(loader.getTypeFormat())) {
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





}
