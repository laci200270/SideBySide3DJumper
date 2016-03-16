package hu.laci200270.games.sbs3djumper.threading;

import hu.laci200270.games.sbs3djumper.utils.Sync;
import hu.laci200270.games.sbs3djumper.world.WorldPart;
import hu.laci200270.games.sbs3djumper.world.World;


/**
 * Created by Laci on 2016. 02. 19..
 */
public class AnimationThread extends Thread {
    World world;
    Sync sync = new Sync();
    Boolean canRun=true;
    public boolean shouldRun=true;

    public AnimationThread(World world) {
         this.world=world;
    }

    @Override
    public void run() {
        while (canRun) {
            if(shouldRun)
            {
                for(WorldPart currentPart:world.getParts())
                {
                    currentPart.onAnimationTick();
                }
            }
            sync.sync(60);

        }
    }

    public void stopThread(){
        canRun=false;
    }
}
