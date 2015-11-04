package andrewrimpici.knowyournotes;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDeck {

    private List<NoteCard> deck;
    private List<NoteCard> shuffledDeck;

    private int currentIndex;

    public NoteDeck() {
        deck = new ArrayList<>();
        shuffledDeck = new ArrayList<>();
    }

    public void makeCompleteDeck() {
        //TODO: UPDATE FOR NEW ENUM
        /*for (int type = 0; type < EnumClefType.values().length; type++) {
            for (int o = 0; o < EnumOctaveType.values().length; o++) {
                for (int letterIndex = 0; letterIndex < EnumLetterType.values().length; letterIndex++) {
                    String l = EnumLetterType.values()[letterIndex].getLetterType();

                    if (o == 0 && !(l.equals("A") || l.equals("B"))) {
                        continue;
                    } else if (o == 8 && !l.equals("C")) {
                        continue;
                    } else {
                        deck.add(new NoteCard(EnumClefType.values()[type], EnumLetterType.values()[letterIndex], EnumOctaveType.values()[o]));
                    }
                }
            }
        }*/
    }

    public void addCard(NoteCard card) {
        if (deck.contains(card)) return;
        else {
            deck.add(card);
            shuffledDeck.add(card);
        }
    }

    public void removeCard(NoteCard card) {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).equals(card)) {
                deck.remove(i);
                shuffledDeck.remove(i);
                return;
            }
        }
    }

    public void shuffle() {

        List<NoteCard> copy = new ArrayList<>(deck.size());

        for (NoteCard c : deck) {
            copy.add(c);
        }

        shuffledDeck.clear();

        int i, rand;
        for (i = 0 ; i < deck.size(); i++) {

            rand = (int)(Math.random() * copy.size());

            shuffledDeck.add(i, copy.get(rand));
            copy.remove(rand);
        }
    }

    public List<NoteCard> getShuffledDeck() {
        return shuffledDeck;
    }

    public int getDeckSize() {
        return deck.size();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean nextCard() {
        if (currentIndex < (deck.size() - 1)) {
            currentIndex++;
            return true;
        }
        return false;
    }

    public NoteCard getCurrentCard(boolean fromShuffledDeck) {
        if (fromShuffledDeck) {
            return shuffledDeck.get(currentIndex);
        }
        else {
            return deck.get(currentIndex);
        }
    }

    public NoteCard getCard(int index) {
        if (index < 0 || index >= deck.size()) {
            Log.d("NoteDeck.getCard(" + index + ")", "The index is not in the card range. Returning null.");
            return null;
        }
        else {
            return deck.get(index);
        }
    }

    public NoteCard getRandomCard() {
        return deck.get((int)(Math.random() * deck.size()));
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
