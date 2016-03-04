package hu.laci200270.games.sbs3djumper.threading;

import hu.laci200270.games.sbs3djumper.utils.Sync;
import hu.laci200270.games.sbs3djumper.world.WorldPart;
import hu.laci200270.games.sbs3djumper.world.World;


/**
 * Created by Laci on 2016. 02. 19..
 */
public class AnimationThread implements Runnable {
    World world;
    Sync sync = new Sync();


    public AnimationThread(World world) {
         this.world=world;
    }

    @Override
    public void run() {
        while (true) {
            for(WorldPart currentPart:world.getParts())
            {
               // currentPart.render();
            }
            sync.sync(60);

        }
    }
}
