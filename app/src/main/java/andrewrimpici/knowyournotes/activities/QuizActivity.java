package andrewrimpici.knowyournotes.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andrewrimpici.knowyournotes.R;
import andrewrimpici.knowyournotes.core.BackgroundDisplayUpdater;
import andrewrimpici.knowyournotes.core.Color;
import andrewrimpici.knowyournotes.core.NoteCard;

public class QuizActivity extends AbstractActivity {

    private RelativeLayout relativeLayoutWrapper;

    private static final int MAX_TIME = 10;

    private int timeCount = MAX_TIME;
    private float countDown;

    private TextView textviewTimer;
    private TextView textviewQuizNoteCardIndex;

    private TextView textviewQuizFragmentTitle;
    private Button buttonStartQuiz;

    private ImageView imageviewQuizNoteCard;

    private Button[] optionsButtons;

    private Dialog dialog;

    private int correctButtonID;
    private int totalCorrectNotes;

    private boolean isClickable, isTimerUp, isQuizStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        relativeLayoutWrapper = (RelativeLayout)findViewById(R.id.relativelayout_quiz_wrapper);

        textviewTimer = (TextView)findViewById(R.id.textview_quiz_timer);
        textviewQuizNoteCardIndex = (TextView)findViewById(R.id.textview_quiz_note_card_index);
        imageviewQuizNoteCard = (ImageView)findViewById(R.id.imageview_quiz_note_card);

        init();
    }

    private void init() {
        initDialog();

        isClickable = true;
        main.getDeck().shuffle();

        initOptionsButtons();
    }

    private void initDialog() {
        isQuizStarted = false;
        dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.quiz_fragment);

        Display d = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);

        int width = p.x;

        textviewQuizFragmentTitle = (TextView) dialog.findViewById(R.id.textview_quiz_dialog_title);
        textviewQuizFragmentTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 0.08f);
        textviewQuizFragmentTitle.setText("Press Start To Take The Quiz!");

        buttonStartQuiz = (Button)dialog.findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 0.08f);
        buttonStartQuiz.setText("START!");

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isQuizStarted = true;
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        this.finish();
        super.onBackPressed();
    }

    private void initOptionsButtons() {

        optionsButtons = new Button[4];

        for (int i = 0; i < optionsButtons.length; i++) {
            int resourceID = getResources().getIdentifier("button_quiz_id_" + i, "id", getPackageName());
            final int buttonID = i;
            optionsButtons[i] = (Button)findViewById(resourceID);
            optionsButtons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (isClickable) {
                        isClickable = false;
                        for (int i = 0; i < optionsButtons.length; i++) {
                            optionsButtons[i].setClickable(false);
                        }

                        if (buttonID == correctButtonID) {
                            totalCorrectNotes++;
                        }
                    }
                }
            });
        }
        updateGUI();
    }

    private void updateGUI() {
        timeCount = MAX_TIME;
        textviewTimer.setText(timeCount + "");

        textviewQuizNoteCardIndex.setText((main.getDeck().getCurrentIndex() + 1) + "/" + main.getDeck().getDeckSize());
        imageviewQuizNoteCard.setImageResource(getResources().getIdentifier(main.getDeck().getCurrentCard(true).getImageResourceID(), "drawable", getPackageName()));

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

        if (!isQuizStarted) return;

        countDown += deltaTime;

        if (countDown >= 1) {
            timeCount--;
            countDown = 0;
        }

        if (timeCount == 0){
            isTimerUp = true;
        }

        QuizActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textviewTimer.setText((int)timeCount + "");
            }
        });

        if (!isClickable || isTimerUp) {
            isTimerUp = false;

            if (!main.getDeck().nextCard()) {
                Intent intent = new Intent(QuizActivity.this, QuizResultsActivity.class);
                intent.putExtra("notesCorrect", totalCorrectNotes);
                intent.putExtra("deckSize", main.getDeck().getDeckSize());
                QuizActivity.this.startActivity(intent);
                finish();
            }
            else {
                QuizActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateGUI();
                    }
                });
            }

            for (int i = 0; i < optionsButtons.length; i++) {
                optionsButtons[i].setClickable(true);
            }
            isClickable = true;
        }
    }

    @Override
    public void updateColor(final Color c) {

        QuizActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                relativeLayoutWrapper.setBackgroundColor(c.toInt());
            }
        });
    }
}
