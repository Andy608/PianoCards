package andrewrimpici.knowyournotes.listener;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import andrewrimpici.knowyournotes.activities.PracticeActivity;

public class PracticeButtonListener implements View.OnClickListener {

    private Activity screen;
    private boolean closeScreen;

    public PracticeButtonListener(boolean b, Activity s) {
        screen = s;
        closeScreen = b;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), PracticeActivity.class);
        v.getContext().startActivity(intent);
        if (closeScreen) {
            screen.finish();
        }
    }
}