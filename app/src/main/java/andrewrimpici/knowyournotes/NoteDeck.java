package andrewrimpici.knowyournotes;

import java.util.ArrayList;
import java.util.List;

public class NoteDeck {

    private static final String[] LETTER_TYPES = {"C", "D", "E", "F", "G", "A", "B"};
    private static final int[] OCTAVE_RANGE = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

    private List<NoteCard> deck;
    private EnumClefType type;

    public NoteDeck(EnumClefType t) {
        type = t;

        deck = new ArrayList<>();

        for (int o : OCTAVE_RANGE) {
            for (String l : LETTER_TYPES) {

                if (o == 0 && !(l.equals("A") || l.equals("B"))) {
                    continue;
                }
                else if (o == 8 && !l.equals("C")) {
                    continue;
                }
                else {
                    deck.add(new NoteCard(type, l, o));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < deck.size(); i++) {
            builder.append(deck.get(i).toString() + " ");
        }

        return builder.toString();
    }
}
