package andrewrimpici.knowyournotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private Thread splashThread;
    public static NoteDeck trebleDeck;
    public static NoteDeck bassDeck;

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

                initDecks();

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

    private void initDecks() {
        trebleDeck = new NoteDeck(EnumClefType.TREBLE);
        bassDeck = new NoteDeck(EnumClefType.BASS);
    }

}
