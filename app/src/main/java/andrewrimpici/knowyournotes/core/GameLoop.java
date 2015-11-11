package andrewrimpici.knowyournotes.core;

import android.util.Log;

import andrewrimpici.knowyournotes.activities.AbstractActivity;

public class GameLoop implements Runnable {

    private AppMain appInstance;

    private static float TICKS_PER_SECOND = 30;
    private static float TIME_SLICE = 1 / TICKS_PER_SECOND;
    private static float LAG_CAP = 0.2f;

    private int ticks;

    private float newTime;
    private float lastTime;
    private float elapsedTime;

    private static Thread gameThread;
    private boolean isRunning;

    public GameLoop(AppMain instance) {
        appInstance = instance;
    }

    public void createThread() {
        gameThread = new Thread(this);

        if (!isRunning) {
            gameThread.start();
        }
    }

    @Override
    public void run() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        startLoop();
    }

    private void startLoop() {
        lastTime = System.nanoTime();

        while (isRunning) {
            newTime = System.nanoTime();
            elapsedTime += (newTime - lastTime) / 1000000000;

            lastTime = newTime;

            if (elapsedTime >= LAG_CAP) {
                elapsedTime = LAG_CAP;
            }

            while (elapsedTime > TIME_SLICE) {
                update(TIME_SLICE);
                elapsedTime -= TIME_SLICE;
            }
        }
    }

    public void pause() {
        Log.d("GAME LOOP", "Paused!");
        isRunning = false;
    }

    public void unpause() {
        Log.d("GAME LOOP", "Unpaused!");
        isRunning = true;
        startLoop();
    }

    public void stop() {
        Log.d("GAME LOOP", "Stopped!");
        isRunning = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException ie) {
            Log.d("GAMELOOP", "An InterruptedException was thrown when trying to stop the Game Thread. :(");
        }
    }

    public void update(float deltaTime) {
        ticks++;

        if (ticks % TICKS_PER_SECOND == 0) {
            Log.d("GAME LOOP", "Ticks: " + ticks);
            ticks = 0;
        }

        BackgroundDisplayUpdater.update(deltaTime);

        AbstractActivity activity = appInstance.getCurrentActivity();
        if (activity != null) {
            activity.updateActivity(deltaTime);
        }
    }
}
