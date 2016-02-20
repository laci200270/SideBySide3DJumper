package hu.laci200270.games.sbs3djumper;

import java.io.InputStream;

/**
 * Created by Laci on 2016. 02. 12..
 */
public class ResourceLocation {

    InputStream inputStream;

    public ResourceLocation(String location){

        this.inputStream = getClass().getClassLoader().getResourceAsStream(location);

    }


    public InputStream getInputStream() {
        return inputStream;
    }
}
