package andrewrimpici.knowyournotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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

       for (int octave = EnumOctaveType.THREE.ordinal(); octave < EnumOctaveType.SIX.ordinal(); octave++) {
            for (int letter = EnumLetterType.A.ordinal(); letter <= EnumLetterType.G.ordinal(); letter++) {

                if (octave == EnumOctaveType.THREE.ordinal() && (letter != EnumLetterType.A.ordinal() && letter != EnumLetterType.B.ordinal())) {
                    continue;
                }
                else {
                    basicDeck.addCard(new NoteCard(EnumClefType.TREBLE, EnumLetterType.values()[letter], EnumOctaveType.values()[octave]));
                }
            }
        }
        basicDeck.addCard(new NoteCard(EnumClefType.TREBLE, EnumLetterType.C, EnumOctaveType.SIX));

        for (int octave = EnumOctaveType.TWO.ordinal(); octave <= EnumOctaveType.FOUR.ordinal(); octave++) {
            for (int letter = EnumLetterType.A.ordinal(); letter <= EnumLetterType.G.ordinal(); letter++) {

                if (octave == EnumOctaveType.TWO.ordinal() && (letter == EnumLetterType.A.ordinal() || letter == EnumLetterType.B.ordinal())) {
                    continue;
                }
                else if (octave == EnumOctaveType.FOUR.ordinal() && (letter > EnumLetterType.E.ordinal())) {
                    continue;
                }
                else {
                    basicDeck.addCard(new NoteCard(EnumClefType.BASS, EnumLetterType.values()[letter], EnumOctaveType.values()[octave]));
                }
            }
        }

        /*for (int i = EnumLetterType.C.ordinal(); i <= EnumLetterType.G.ordinal(); i++)
            basicDeck.addCard(new NoteCard(EnumClefType.TREBLE, EnumLetterType.values()[i], EnumOctaveType.FOUR));
        */

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
        imageViewNoteCard.setImageResource(getResources().getIdentifier(basicDeck.getCurrentCard(true).getImageResourceID(), "drawable", getPackageName()));

        //TODO: Update gui buttons to pick a random letter and display it. Connect the buttonID to the correctButtonID.
        //Randomly generate a new correctButtonID.
        //Set the button with that ID to the correct text.
        //Fill in the other buttons with random notes. (Preferably notes that are close in range to the real note).

        randomizeOptionsButtons();
    }

    private void randomizeOptionsButtons() {

        correctButtonID = (int)(Math.random() * optionsButtons.length);
        optionsButtons[correctButtonID].setText(basicDeck.getCurrentCard(true).toString());

        List<NoteCard> copy = new ArrayList<>(basicDeck.getDeckSize() - 1);
        for (int i = 0; i < basicDeck.getDeckSize(); i++) {
            NoteCard c = basicDeck.getCard(i);

            if (c.equals(basicDeck.getCurrentCard(true))) {
                continue;
            }
            else {
                copy.add(c);
            }
        }

        for (int i = 0; i < optionsButtons.length; i++) {
            if (i == correctButtonID) {
                continue;
            }
            else {
                int rand = (int)(Math.random() * copy.size());
                optionsButtons[i].setText(copy.get(rand).toString());
                copy.remove(rand);
            }
        }
    }
}
