package andrewrimpici.knowyournotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PracticeActivity extends AppCompatActivity {

    private TextView textviewNoteStreak;
    private TextView textviewNoteCardIndex;

    private ImageView imageViewNoteCard;

    private Button[] optionsButtons;

    private int noteStreak;
    private int correctButtonID;

    private NoteDeck basicDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        textviewNoteStreak = (TextView)findViewById(R.id.textview_note_streak);
        textviewNoteCardIndex = (TextView)findViewById(R.id.textview_note_card_index);

        imageViewNoteCard = (ImageView)findViewById(R.id.imageview_note_card);
        init();
    }

    private void init() {
        basicDeck = new NoteDeck();

        for (int i = EnumLetterType.C.ordinal(); i <= EnumLetterType.G.ordinal(); i++)
            basicDeck.addCard(new NoteCard(EnumClefType.TREBLE, EnumLetterType.values()[i], EnumOctaveType.FOUR));

        Log.d("DERP", basicDeck.toString());

        basicDeck.shuffle();

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

                    if (!basicDeck.nextCard()) {
                        //TODO: GO TO A FINISH PRACTICE SCREEN INSTEAD OF BACK TO MAIN MENU!!
                        finish();
                    }

                    updateGUI();
                }
            });
        }
        updateGUI();
    }

    private void updateGUI() {
        textviewNoteStreak.setText(Integer.toString(noteStreak));
        textviewNoteCardIndex.setText((basicDeck.getCurrentIndex() + 1) + "/" + basicDeck.getDeckSize());
        imageViewNoteCard.setImageResource(getResources().getIdentifier(basicDeck.getCurrentCard(true).getImageResourceID(), "mipmap", getPackageName()));

        //TODO: Update gui buttons to pick a random letter and display it. Connect the buttonID to the correctButtonID.
        //Randomly generate a new correctButtonID.
        //Set the button with that ID to the correct text.
        //Fill in the other buttons with random notes. (Preferably notes that are close in range to the real note).

        randomizeOptionsButtons();
    }

    private void randomizeOptionsButtons() {

        correctButtonID = (int)(Math.random() * optionsButtons.length);
        optionsButtons[correctButtonID].setText(basicDeck.getCurrentCard(true).toString());

        String[] optionsList = new String[4];
        optionsList[correctButtonID] = optionsButtons[correctButtonID].getText().toString();

        for (int i = 0; i < optionsButtons.length; i++) {

            if (i == correctButtonID) continue;

            //TODO: GENERATE RANDOM WITHOUT ANY DUPLICATES!!
            NoteCard randomCard = randomCard = basicDeck.getRandomCard();

            optionsList[i] = randomCard.toString();
            optionsButtons[i].setText(randomCard.toString());
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
