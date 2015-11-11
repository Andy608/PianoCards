package andrewrimpici.knowyournotes.core;

import android.app.Application;

import andrewrimpici.knowyournotes.activities.AbstractActivity;
import andrewrimpici.knowyournotes.util.EnumClefType;
import andrewrimpici.knowyournotes.util.EnumLetterType;
import andrewrimpici.knowyournotes.util.EnumOctaveType;

public class AppMain extends Application {

    private GameLoop loop;
    protected AbstractActivity currentActivity;
    protected NoteDeck deck;

    @Override
    public void onCreate() {
        super.onCreate();
        currentActivity = null;
        initDeck();
        loop = new GameLoop(this);
        loop.createThread();
    }

    public GameLoop getLoop() {
        return loop;
    }

    public void setCurrentActivity(AbstractActivity activity) {
        currentActivity = activity;
    }

    public AbstractActivity getCurrentActivity() {
        return currentActivity;
    }

    private void initDeck() {
        deck = new NoteDeck();

        for (int octave = EnumOctaveType.THREE.ordinal(); octave < EnumOctaveType.SIX.ordinal(); octave++) {
            for (int letter = EnumLetterType.A.ordinal(); letter <= EnumLetterType.G.ordinal(); letter++) {

                if (octave == EnumOctaveType.THREE.ordinal() && (letter != EnumLetterType.A.ordinal() && letter != EnumLetterType.B.ordinal())) {
                    continue;
                }
                else {
                    deck.addCard(new NoteCard(EnumClefType.TREBLE, EnumLetterType.values()[letter], EnumOctaveType.values()[octave]));
                }
            }
        }
        deck.addCard(new NoteCard(EnumClefType.TREBLE, EnumLetterType.C, EnumOctaveType.SIX));

        for (int octave = EnumOctaveType.TWO.ordinal(); octave <= EnumOctaveType.FOUR.ordinal(); octave++) {
            for (int letter = EnumLetterType.A.ordinal(); letter <= EnumLetterType.G.ordinal(); letter++) {

                if (octave == EnumOctaveType.FOUR.ordinal() && (letter < EnumLetterType.C.ordinal() || letter > EnumLetterType.E.ordinal())) {
                    continue;
                }
                else {
                    deck.addCard(new NoteCard(EnumClefType.BASS, EnumLetterType.values()[letter], EnumOctaveType.values()[octave]));
                }
            }
        }

        deck.shuffle();
    }

    public NoteDeck getDeck() {
        return deck;
    }
}
