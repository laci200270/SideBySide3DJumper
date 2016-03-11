package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.ResourceLocation;

import java.util.HashMap;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class ModelRegistry {
    static HashMap<String, IModel> modelHashMap = new HashMap<>();

    public static IModel getModel(String name) {
        try {


            if (!modelHashMap.containsKey(name)) {
                String ext = name.substring(name.lastIndexOf(".") + 1);

                IModel model = ModelLoaderRegistry.getModel(new ResourceLocation(name), ext);
                model.setTextureName(getFileNameWithoutExtension(name));
                model.loadTexture();
                modelHashMap.put(name, model);
            }

            return modelHashMap.get(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getExtension(String file) {
        String returnable = "";
        String[] sepWithDots = file.split(".");
        returnable = sepWithDots[sepWithDots.length - 1];
        return returnable;
    }

    private static String getFileNameWithoutExtension(String file) {
        String returnable = "";
        int lastDotPos = file.lastIndexOf(".");
        for (int i = 0; i < lastDotPos; i++) {
            returnable += file.charAt(i);

        }
        return returnable;
    }

}
