package andrewrimpici.knowyournotes.core;

import andrewrimpici.knowyournotes.util.EnumClefType;
import andrewrimpici.knowyournotes.util.EnumLetterType;
import andrewrimpici.knowyournotes.util.EnumOctaveType;

public class NoteCard {

    private EnumLetterType letter;
    private EnumOctaveType octave;

    private String imageResourceID;

    private EnumClefType type;

    public NoteCard(EnumClefType t, EnumLetterType l, EnumOctaveType o) {
        type = t;
        letter = l;
        octave = o;
        imageResourceID = t.getType() + "_" + l.getLetterType() + o.ordinal();
    }

    public EnumLetterType getNoteLetter() {
        return letter;
    }

    public EnumOctaveType getOctave() {
        return octave;
    }

    public String getImageResourceID() {
        return imageResourceID;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof NoteCard)) return false;
        if (!letter.equals(((NoteCard)other).letter) || !(octave.equals(((NoteCard)other).octave))) return false;
        else return true;
    }

    @Override
    public String toString() {
        return letter.getLetterType().toUpperCase() + Integer.toString(octave.ordinal());
    }
}
