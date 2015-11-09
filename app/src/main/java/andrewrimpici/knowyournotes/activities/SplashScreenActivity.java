package andrewrimpici.knowyournotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import andrewrimpici.knowyournotes.R;
import andrewrimpici.knowyournotes.core.Color;

public class SplashScreenActivity extends AbstractActivity {

    //TODO: ADD IN GAME LOOP OBJECT HERE AND START IT. ADD ONPAUSE() AND ONRESUME() HERE AND CALL THE METHODS TO PAUSE AND START THE GAMELOOP.
    //TODO: ADD THE ONPAUSE AND ONRESUME TO EVERY CLASS. (HOPEFULLY THATLL WORK).
    //TODO: REDO HOW THE SPLASH SCREEN STAYS FOR 5 SECONDS USING THE NEW GAMELOOP OBJECT
    //TODO: TEST!!!

    private Thread splashThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final SplashScreenActivity splashScreen = this;
        splashThread = new Thread() {
            @Override
            public void run() {

                ImageView splashImage = (ImageView)findViewById(R.id.imageview_splash_logo);
                Animation fadeIn = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.fade_in);
                splashImage.startAnimation(fadeIn);

                try {
                    synchronized (this) {
                        wait(5000);
                    }
                } catch(InterruptedException ie) {/*I should probably put something useful here...*/}

                Intent intent = new Intent();
                intent.setClass(splashScreen, MainMenuActivity.class);
                startActivity(intent);
                finish();

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
            synchronized (splashThread) {
                splashThread.notifyAll();
            }
        }
        return true;
    }

    @Override
    public void updateColor(Color c) {

    }
}
