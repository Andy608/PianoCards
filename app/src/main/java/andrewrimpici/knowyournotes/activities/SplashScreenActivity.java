package andrewrimpici.knowyournotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import andrewrimpici.knowyournotes.R;
import andrewrimpici.knowyournotes.core.BackgroundDisplayUpdater;
import andrewrimpici.knowyournotes.core.Color;

public class SplashScreenActivity extends AbstractActivity {

    private static final float TOTAL_WAIT_TIME = 5f;

    private Thread splashThread;
    private float secondsCounter;

    private boolean isSwitching = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashThread = new Thread() {
            @Override
            public void run() {

                ImageView splashImage = (ImageView)findViewById(R.id.imageview_splash_logo);
                Animation fadeIn = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.fade_in);
                splashImage.startAnimation(fadeIn);

                try {
                    join();
                } catch (InterruptedException ie) {/*I should probably put something useful here...*/}
            }
        };
        splashThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isSwitching) {
                switchScreens();
            }
        }
        return true;
    }

    @Override
    public void updateActivity(float deltaTime) {
        if (!isSwitching) {
            updateColor(BackgroundDisplayUpdater.getTargetColor());

            secondsCounter += deltaTime;

            if (secondsCounter >= TOTAL_WAIT_TIME) {
                switchScreens();
            }
        }
    }

    @Override
    public void updateColor(Color c) {}

    private void switchScreens() {
        isSwitching = true;
        Log.d("HELLLOLLO", "SWITCHING");
        finish();
        Intent intent = new Intent();
        intent.setClass(this, MainMenuActivity.class);
        startActivity(intent);
    }
}
