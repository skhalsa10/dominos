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


    //either need a hashcode or index to use to pull domino. probably should not do hashcode as this can map to more than one domino
    public Domino remove(int index){
        //TODO
        return null;
    }

    /**
     * checks to see if the hand has any pieces.
     * @return true if hand is empty
     */
    public boolean isEmpty(){
        return hand.isEmpty();
    }
}
