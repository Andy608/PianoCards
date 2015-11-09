package andrewrimpici.knowyournotes.core;

import android.app.Activity;
import android.app.Application;

import andrewrimpici.knowyournotes.activities.AbstractActivity;

public class AppMain extends Application {

    private GameLoop loop;
    protected AbstractActivity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        currentActivity = null;
        loop = new GameLoop(this);
        loop.createThread();
    }

    public GameLoop getLoop() {
        return loop;
    }

    public void setCurrentActivity(AbstractActivity activity) {
        currentActivity = activity;
    }

    public AbstractActivity getCurrentActivity() {
        return currentActivity;
    }
}
