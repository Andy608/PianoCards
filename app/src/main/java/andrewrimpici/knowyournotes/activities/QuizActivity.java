package andrewrimpici.knowyournotes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import andrewrimpici.knowyournotes.R;
import andrewrimpici.knowyournotes.core.BackgroundDisplayUpdater;
import andrewrimpici.knowyournotes.core.Color;

public class QuizActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

    @Override
    public void updateActivity(float deltaTime) {
        updateColor(BackgroundDisplayUpdater.getTargetColor());
    }

    @Override
    public void updateColor(Color c) {

    }
}
