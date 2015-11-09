package andrewrimpici.knowyournotes.listener;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import andrewrimpici.knowyournotes.activities.MainMenuActivity;

public class MenuButtonListener implements View.OnClickListener {

    private Activity screen;
    private boolean closeScreen;

    public MenuButtonListener(boolean b, Activity s) {
        screen = s;
        closeScreen = b;
    }

    @Override
    public void onClick(View v) {
        if (closeScreen) {
            screen.finish();
        }
    }
}
