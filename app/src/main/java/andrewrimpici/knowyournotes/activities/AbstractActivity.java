package andrewrimpici.knowyournotes.activities;

import android.app.Activity;
import android.os.Bundle;

import andrewrimpici.knowyournotes.core.AppMain;
import andrewrimpici.knowyournotes.core.Color;

public abstract class AbstractActivity extends Activity {

    private AppMain main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (AppMain) this.getApplicationContext();
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

    public abstract void updateColor(final Color c);
}
