package andrewrimpici.knowyournotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import andrewrimpici.knowyournotes.core.BackgroundDisplayUpdater;
import andrewrimpici.knowyournotes.core.Color;
import andrewrimpici.knowyournotes.listener.MenuButtonListener;
import andrewrimpici.knowyournotes.listener.PracticeButtonListener;
import andrewrimpici.knowyournotes.listener.QuizButtonListener;
import andrewrimpici.knowyournotes.R;

public class PracticeResultsActivity extends AbstractActivity {

    private LinearLayout linearLayoutWrapper;

    private TextView textviewPercentage;
    private TextView textviewNumNotesCorrect;
    private Button buttonToQuizScreen;
    private Button buttonToPracticeScreen;
    private Button buttonToMenuScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_results);

        linearLayoutWrapper = (LinearLayout)findViewById(R.id.linearlayout_practiceresults_wrapper);

        textviewPercentage = (TextView)findViewById(R.id.textview_practice_results_percentage);
        textviewNumNotesCorrect = (TextView)findViewById(R.id.textview_practice_results_correct_notes);

        buttonToQuizScreen = (Button)findViewById(R.id.button_practice_results_to_quiz_screen);
        buttonToPracticeScreen = (Button)findViewById(R.id.button_practice_results_to_practice_screen);
        buttonToMenuScreen = (Button)findViewById(R.id.button_practice_results_to_menu_screen);

        buttonToQuizScreen.setOnClickListener(new QuizButtonListener(true, this));
        buttonToPracticeScreen.setOnClickListener(new PracticeButtonListener(true, this));
        buttonToMenuScreen.setOnClickListener(new MenuButtonListener(true, this));

        Intent i = getIntent();
        int notesCorrect = i.getIntExtra("notesCorrect", 0);
        int deckSize = i.getIntExtra("deckSize", 1);
        int percentage = (int)((notesCorrect / (float)deckSize) * 100);

        textviewPercentage.setText(percentage+"%");
        textviewNumNotesCorrect.setText(notesCorrect+"/"+deckSize+" Notes Correct");
    }

    @Override
    public void updateActivity(float deltaTime) {
        updateColor(BackgroundDisplayUpdater.getTargetColor());
    }

    @Override
    public void updateColor(final Color c) {

        PracticeResultsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayoutWrapper.setBackgroundColor(c.toInt());
            }
        });
    }
}
