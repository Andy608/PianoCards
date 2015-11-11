package andrewrimpici.knowyournotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import andrewrimpici.knowyournotes.R;
import andrewrimpici.knowyournotes.core.BackgroundDisplayUpdater;
import andrewrimpici.knowyournotes.core.Color;

public class QuizResultsActivity extends AbstractActivity {
    private LinearLayout linearLayoutWrapper;

    private TextView textviewPercentage;
    private TextView textviewNumNotesCorrect;
    private Button buttonToQuizScreen;
    private Button buttonToPracticeScreen;
    private Button buttonToMenuScreen;

    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        linearLayoutWrapper = (LinearLayout)findViewById(R.id.linearlayout_results_wrapper);

        textviewPercentage = (TextView)findViewById(R.id.textview_results_percentage);
        textviewNumNotesCorrect = (TextView)findViewById(R.id.textview_results_correct_notes);

        buttonToQuizScreen = (Button)findViewById(R.id.button_results_to_quiz_screen);
        buttonToPracticeScreen = (Button)findViewById(R.id.button_results_to_practice_screen);
        buttonToMenuScreen = (Button)findViewById(R.id.button_results_to_menu_screen);

        buttonToQuizScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });

        buttonToPracticeScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(v.getContext(), PracticeActivity.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });

        buttonToMenuScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
                lastClickTime = SystemClock.elapsedRealtime();

                finish();
            }
        });

        Intent i = getIntent();
        int notesCorrect = i.getIntExtra("notesCorrect", 0);
        int deckSize = i.getIntExtra("deckSize", 1);
        int percentage = (int)((notesCorrect / (float)deckSize) * 100);

        textviewPercentage.setText(percentage+"%");

        if (percentage >= 95) {
            textviewNumNotesCorrect.setText("Congratulations! " + notesCorrect + "/" + deckSize + " Notes Correct!");
        }
        else if (percentage > 80) {
            textviewNumNotesCorrect.setText("Good Job! " + notesCorrect + "/" + deckSize + " Notes Correct.");
        }
        else if (percentage < 30) {
            textviewNumNotesCorrect.setText("Were you trying? " +notesCorrect+"/"+deckSize+" Notes Correct!");
        }
        else {
            textviewNumNotesCorrect.setText(notesCorrect+"/"+deckSize+" Notes Correct");
        }
    }

    @Override
    public void updateActivity(float deltaTime) {
        updateColor(BackgroundDisplayUpdater.getTargetColor());
    }

    @Override
    public void updateColor(final Color c) {

        QuizResultsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayoutWrapper.setBackgroundColor(c.toInt());
            }
        });
    }
}
