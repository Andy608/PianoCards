package andrewrimpici.knowyournotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticeActivity extends AppCompatActivity {

    private TextView textviewNoteStreak;
    private TextView textviewNoteCardIndex;

    private Button[] optionsButtons;

    private int noteStreak;
    private int correctButtonID;

    private NoteDeck trebleDeck;
    private NoteDeck bassDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        textviewNoteStreak = (TextView)findViewById(R.id.textview_note_streak);
        textviewNoteCardIndex = (TextView)findViewById(R.id.textview_note_card_index);
        init();
    }

    private void init() {
        correctButtonID = 1;

        trebleDeck = SplashScreenActivity.trebleDeck;
        bassDeck = SplashScreenActivity.bassDeck;

        Log.d("DERP: ", trebleDeck.toString());
        Log.d("DERP: ", bassDeck.toString());

        textviewNoteStreak.setText(Integer.toString(noteStreak));
        textviewNoteCardIndex.setText(noteStreak + "/10"); //textviewNoteCardIndex.setText("1/" + deck.getSize());
        initOptionsButtons();
    }

    private void initOptionsButtons() {

        optionsButtons = new Button[4];

        for (int i = 0; i < optionsButtons.length; i++) {
            int resourceID = getResources().getIdentifier("button_practice_id_" + i, "id", getPackageName());
            final int buttonID = i;
            optionsButtons[i] = (Button)findViewById(resourceID);
            optionsButtons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v){
                    //TODO: Check to see if the buttonID matches the correctID.
                    //If they match, add a point the to note streak.
                    //Else make note streak 0.
                    //Move onto the next card.

                    if (buttonID == correctButtonID) {
                        noteStreak++;
                    }
                    else {
                        noteStreak = 0;
                    }

                    //TODO: Go to the next card in the deck, and randomize the correctButtonID.
                    textviewNoteStreak.setText(Integer.toString(noteStreak));
                    textviewNoteCardIndex.setText(noteStreak + "/10"); //textviewNoteCardIndex.setText((deck.getNoteIndex() + 1) + "/" + deck.getSize());
                }
            });
        }
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
