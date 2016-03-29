package hu.laci200270.games.sbs3djumper.renderer;

import java.util.ArrayList;

/**
 * Created by Laci on 2016. 03. 26..
 */
public class FpsCounter implements ITitleInfoProvider {

    private long frames;
    private long lastMeasure;
    private long prevFrames;

    public void increaseCounter(){
        frames++;
        if(System.nanoTime()>lastMeasure+1000000000){
            lastMeasure=System.nanoTime();
            prevFrames=frames;
            frames=0;
        }
    }

    @Override
    public String[] getInfo() {
        String[] result={String.format("FPS: %s",prevFrames)};
        return result;
    }
}
