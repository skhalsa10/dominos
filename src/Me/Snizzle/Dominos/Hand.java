package Me.Snizzle.Dominos;

import java.util.ArrayList;

/**
 *This class keeps track of a hand. a hand is a collection of dominos. the collection needs to have
 * fast access to remove from anywhere seeing as any piece can be played.
 */
public class Hand {

    private ArrayList<Domino> hand;


    public Hand(){
        hand = new ArrayList<Domino>();
    }

    /**
     * this will add a Domino piece to the hand.
     * @param piece domino that will get added.
     */
    public void add(Domino piece){
        hand.add(piece);
    }


    /**
     * The index parameter must be in terms of the array list. this may cause issues for a graphics version.
     * This means other logic is converting graphics  xy into an index usable in this method.
     *
     * @param index index of domino to remove
     * @return removed domino
     */
    public Domino remove(int index){

        return hand.remove(index);
    }

    /**
     * checks to see if the hand has any pieces.
     * @return true if hand is empty
     */
    public boolean isEmpty(){
        return hand.isEmpty();
    }


    public Domino[] toArray(){
        Domino[] output = new Domino[hand.size()];
        return hand.toArray(output);
    }
}
