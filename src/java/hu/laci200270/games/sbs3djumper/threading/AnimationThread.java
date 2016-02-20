package hu.laci200270.games.sbs3djumper.threading;

import hu.laci200270.games.sbs3djumper.utils.Sync;


/**
 * Created by Laci on 2016. 02. 19..
 */
public class AnimationThread implements Runnable {
    Sync sync = new Sync();

    @Override
    public void run() {
        while (true) {
            sync.sync(10000);

        }
    }
}
