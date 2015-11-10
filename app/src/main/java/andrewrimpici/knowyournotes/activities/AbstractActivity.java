package andrewrimpici.knowyournotes.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import andrewrimpici.knowyournotes.R;
import andrewrimpici.knowyournotes.core.AppMain;
import andrewrimpici.knowyournotes.core.Color;

public abstract class AbstractActivity extends Activity {

    private AppMain main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (AppMain) this.getApplicationContext();

        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setCurrentActivity(this);
        main.getLoop().createThread();
    }

    @Override
    public void onPause() {
        super.onPause();
        clearActivity();
        main.getLoop().stop();
    }

    private void clearActivity() {
        AbstractActivity activity = main.getCurrentActivity();
        if (this.equals(activity)) {
            main.setCurrentActivity(null);
        }
    }

    public abstract void updateActivity(float deltaTime);
    public abstract void updateColor(final Color c);
}
