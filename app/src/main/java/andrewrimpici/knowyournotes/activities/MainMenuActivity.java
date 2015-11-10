package andrewrimpici.knowyournotes.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import andrewrimpici.knowyournotes.core.BackgroundDisplayUpdater;
import andrewrimpici.knowyournotes.core.Color;
import andrewrimpici.knowyournotes.listener.PracticeButtonListener;
import andrewrimpici.knowyournotes.R;

public class MainMenuActivity extends AbstractActivity {

    private Button buttonPractice;
    private Button buttonQuiz;
    private Button buttonSettings;

    private TextView textviewTitle;

    private LinearLayout linearLayoutWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        linearLayoutWrapper = (LinearLayout) findViewById(R.id.linearlayout_mainmenu_wrapper);

        textviewTitle = (TextView) findViewById(R.id.textview_title);

        Display d = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);

        int width = p.x;

        textviewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 0.16f);
        textviewTitle.setText(R.string.app_name);

        buttonPractice = (Button) findViewById(R.id.button_main_menu_to_practice_screen);
        buttonQuiz = (Button) findViewById(R.id.button_main_menu_to_quiz_screen);
        buttonSettings = (Button) findViewById(R.id.button_main_menu_to_settings_screen);
        initListeners();
    }

    private void initListeners() {

        buttonPractice.setOnClickListener(new PracticeButtonListener(false, this));

        buttonQuiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Send to new screen
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Send to new screen
            }
        });
    }

    @Override
    public void updateActivity(float deltaTime) {
        updateColor(BackgroundDisplayUpdater.getTargetColor());
    }

    @Override
    public void updateColor(final Color c) {

        MainMenuActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayoutWrapper.setBackgroundColor(c.toInt());
            }
        });
    }
}
