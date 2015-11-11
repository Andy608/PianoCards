package andrewrimpici.knowyournotes.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andrewrimpici.knowyournotes.core.BackgroundDisplayUpdater;
import andrewrimpici.knowyournotes.core.Color;
import andrewrimpici.knowyournotes.util.EnumClefType;
import andrewrimpici.knowyournotes.util.EnumLetterType;
import andrewrimpici.knowyournotes.util.EnumOctaveType;
import andrewrimpici.knowyournotes.core.NoteCard;
import andrewrimpici.knowyournotes.core.NoteDeck;
import andrewrimpici.knowyournotes.R;

public class PracticeActivity extends AbstractActivity {

    private RelativeLayout relativeLayoutWrapper;

    private TextView textviewNoteStreak;
    private TextView textviewNoteCardIndex;

    private ImageView imageviewNoteCard;

    private Button[] optionsButtons;

    private int noteStreak;
    private int correctButtonID;
    private int totalCorrectNotes;

    private boolean showAnswer;
    private static final float SHOW_TIME = 1f;
    private float showCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        relativeLayoutWrapper = (RelativeLayout)findViewById(R.id.relativelayout_practice_wrapper);

        textviewNoteStreak = (TextView)findViewById(R.id.textview_note_streak);
        textviewNoteCardIndex = (TextView)findViewById(R.id.textview_note_card_index);

        imageviewNoteCard = (ImageView)findViewById(R.id.imageview_note_card);
        init();
    }

    private void init() {
        main.getDeck().shuffle();
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
                public void onClick(View v) {

                    if (!showAnswer) {
                        for (int i = 0; i < optionsButtons.length; i++) {
                            optionsButtons[i].setClickable(false);
                        }

                        if (buttonID == correctButtonID) {
                            totalCorrectNotes++;
                            noteStreak++;
                        }
                        else {
                            noteStreak = 0;
                            setButtonImage(buttonID, R.drawable.xhdpi_wrong_button);
                        }

                        setButtonImage(correctButtonID, R.drawable.xhdpi_correct_button);
                        showAnswer = true;
                    }
                }
            });
        }
        updateGUI();
    }

    private void updateGUI() {

        for (int i = 0; i < optionsButtons.length; i++) {
            setButtonImage(i, R.drawable.xhdpi_normal_button);
        }

        textviewNoteStreak.setText(Integer.toString(noteStreak));
        textviewNoteCardIndex.setText((main.getDeck().getCurrentIndex() + 1) + "/" + main.getDeck().getDeckSize());
        imageviewNoteCard.setImageResource(getResources().getIdentifier(main.getDeck().getCurrentCard(true).getImageResourceID(), "drawable", getPackageName()));

        randomizeOptionsButtons();
    }

    private void randomizeOptionsButtons() {

        correctButtonID = (int)(Math.random() * optionsButtons.length);
        optionsButtons[correctButtonID].setText(main.getDeck().getCurrentCard(true).toString());

        List<NoteCard> copy = new ArrayList<>(main.getDeck().getDeckSize() - 1);
        for (int i = 0; i < main.getDeck().getDeckSize(); i++) {
            NoteCard c = main.getDeck().getCard(i);

            if (c.equals(main.getDeck().getCurrentCard(true))) {
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

    @Override
    public void updateActivity(float deltaTime) {
        updateColor(BackgroundDisplayUpdater.getTargetColor());

        if (showAnswer) {

            showCounter += deltaTime;

            if (showCounter >= SHOW_TIME) {
                showAnswer = false;

                showCounter = 0;

                if (!main.getDeck().nextCard()) {
                    Intent intent = new Intent(PracticeActivity.this, PracticeResultsActivity.class);
                    intent.putExtra("notesCorrect", totalCorrectNotes);
                    intent.putExtra("deckSize", main.getDeck().getDeckSize());
                    PracticeActivity.this.startActivity(intent);
                    finish();
                }

                PracticeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateGUI();
                    }
                });

                for (int i = 0; i < optionsButtons.length; i++) {
                    optionsButtons[i].setClickable(true);
                }
            }
        }
    }

    @Override
    public void updateColor(final Color c) {

        PracticeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                relativeLayoutWrapper.setBackgroundColor(c.toInt());
            }
        });
    }

    private void setButtonImage(final int buttonID, final int resID) {

        PracticeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                optionsButtons[buttonID].setBackgroundResource(resID);
                optionsButtons[buttonID].setPadding(0, 0, 0, 0);
            }
        });
    }
}
