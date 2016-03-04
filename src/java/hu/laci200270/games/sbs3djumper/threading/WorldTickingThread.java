package hu.laci200270.games.sbs3djumper.threading;

import hu.laci200270.games.sbs3djumper.utils.Sync;
import hu.laci200270.games.sbs3djumper.world.World;
import hu.laci200270.games.sbs3djumper.world.WorldPart;

/**
 * Created by diak on 2016.03.04..
 */
public class WorldTickingThread extends Thread {

    World world;
    Sync sync = new Sync();
    Boolean canRun=true;


    public WorldTickingThread(World world) {
        this.world=world;
    }

    @Override
    public void run() {
        while (canRun) {
            for(WorldPart currentPart:world.getParts())
            {
                currentPart.onWorldTick(world);
            }
            sync.sync(20);

        }
    }

    public void stopThread(){
        canRun=false;
    }
}
