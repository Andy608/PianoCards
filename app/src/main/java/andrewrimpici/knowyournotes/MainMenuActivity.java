package andrewrimpici.knowyournotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button buttonPractice;
    private Button buttonQuiz;
    private Button buttonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttonPractice = (Button) findViewById(R.id.button_main_menu_to_practice_screen);
        buttonQuiz = (Button) findViewById(R.id.button_main_menu_to_quiz_screen);
        buttonSettings = (Button) findViewById(R.id.button_main_menu_to_settings_screen);
        initListeners();
    }

    private void initListeners() {

        buttonPractice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, PracticeActivity.class);
                startActivity(intent);
            }
        });

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
}
