package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.obj.ObjLoader;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * Created by Laci on 2016. 02. 12..
 */
public class ResourceLocation {
    URL url;
    File file;
    public ResourceLocation(String location){

        url = getClass().getClassLoader().getResource(location);
        file=new File(url.getFile());
    }

    public URL getUrl() {
        return url;
    }

    public File getFile() {
        return file;
    }


}
