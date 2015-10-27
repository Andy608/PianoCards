package andrewrimpici.knowyournotes;

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

        buttonPractice = (Button)findViewById(R.id.button_main_menu_to_practice_screen);
        buttonQuiz = (Button)findViewById(R.id.button_main_menu_to_quiz_screen);
        buttonSettings = (Button)findViewById(R.id.button_main_menu_to_settings_screen);
        initListeners();
    }

    private void initListeners() {

        buttonPractice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Send to new screen
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
