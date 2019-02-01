package Me.Snizzle.Dominos;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * the Board class keeps track of the active playing area that the players are putting their Dominos on.
 * This utilizes the Deque collection as it allows adding from either end and grows dynamically. there are only two rows
 * here. to keep track of. depending on size of each determines a legal row to play on. if the rows have the same size
 * either row can be used. max difference in size that is legal is 1. the row with the smaller size must be played.
 */
public class Board {

    public static enum Side { LEFT, RIGHT  }
    public static enum Row { TOP, BOTTOM }

    private Deque<Domino> topRow;
    private Deque<Domino> bottomRow;

    public Board(){
        topRow = new ArrayDeque<Domino>();
        bottomRow = new ArrayDeque<Domino>();
    }

    public void add(Side side , Row row){


    }

}
