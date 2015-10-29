package andrewrimpici.knowyournotes;

public class NoteCard {

    private String letter;
    private int octave;

    public NoteCard(EnumClefType t, String l, int o) {
        letter = l;
        octave = o;
    }

    public String getNoteLetter() {
        return letter;
    }

    public int getOctave() {
        return octave;
    }

    @Override
    public String toString() {
        return letter + Integer.toString(octave);
    }
}
