package hu.laci200270.games.sbs3djumper;

import java.io.InputStream;

/**
 * Created by Laci on 2016. 02. 12..
 */
public class ResourceLocation {

    private final String location;
    InputStream inputStream;

    public ResourceLocation(String location) {

        this.inputStream = getClass().getClassLoader().getResourceAsStream(location);
        this.location=location;
    }

    public String getLocation() {
        return location;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getFileNameWithoutExtension(){
        String[] dotSplit=location.split(".");
        String returanble="";
        for (int i = 0; i < dotSplit.length-1; i++) {
            returanble=returanble+dotSplit[i];
        }
        return returanble;
    }
}
