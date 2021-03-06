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


    public enum Side { LEFT, RIGHT  }
    public enum Row { TOP, BOTTOM }

    private boolean topRightExtends;
    private boolean topLeftExtends;
    private boolean bottomRightExtends;
    private boolean bottomLeftExtends;

    private Deque<Domino> topRow;
    private Deque<Domino> bottomRow;

    /**
     * this constructs a new board in a default state
     */
    public Board(){
        topRow = new ArrayDeque<Domino>();
        bottomRow = new ArrayDeque<Domino>();
        topLeftExtends = false;
        topRightExtends =  false;
        bottomLeftExtends = false;
        bottomRightExtends =  false;
    }

    /**
     * this method adds to the board and takes a Board.Side and Board.Row as inputs to direct where we add the domino
     *
     * @param side which side we are attempting to add to.
     * @param row which row are we attempting to add to
     * @param piece  the domino we are attempting to add
     */
    public boolean add(Side side , Row row, Domino piece){
        boolean isFirstPiece = false;
        if (checkValid(side, row, piece)){
            if (topRow.size() ==0 && bottomRow.size() ==0){isFirstPiece = true;}
            if(side == Side.LEFT && row == Row.TOP){
                topRow.addFirst(piece);
                topLeftExtends = true;
                if(isFirstPiece){ topRightExtends = true;}
                bottomLeftExtends = false;
                if(isFirstPiece){ bottomRightExtends = false;}
            }
            if(side == Side.LEFT && row == Row.BOTTOM){
                bottomRow.addFirst(piece);
                bottomLeftExtends = true;
                if(isFirstPiece){bottomRightExtends = true;}
                topLeftExtends = false;
                if(isFirstPiece){topRightExtends = false;}
            }
            if(side == Side.RIGHT && row == Row.TOP){
                topRow.addLast(piece);
                if(isFirstPiece){topLeftExtends = true;}
                topRightExtends = true;
                bottomRightExtends = false;
                if(isFirstPiece){bottomLeftExtends = false;}
            }
            if(side == Side.RIGHT && row == Row.BOTTOM){
                bottomRow.addLast(piece);
                if(isFirstPiece){bottomLeftExtends = true;}
                bottomRightExtends = true;
                topRightExtends = false;
                if(isFirstPiece){topLeftExtends = false;}
            }
            return true;
        }else{
            return false;
        }

    }

    //this checks the validity of a requested move...I could break this out into more private methods...but I dont want to

    public boolean checkValid(Side side , Row row, Domino piece){
        //lets take care of the edge cases first
        if(topRow.isEmpty() && bottomRow.isEmpty()){
            return true;
        }
        //the following should work as long as there is at least one piece on the board. the other case is caught above
        //check top left
        if(side == Side.LEFT && row == Row.TOP){
            if(!topLeftExtends){
                if(piece.checkRight() == 0 || peekBottomLeft() == 0){
                    return true;
                }else {
                    return (peekBottomLeft() == piece.checkRight());
                }
            }
        }

        //check bottom left
        if(side == Side.LEFT && row == Row.BOTTOM){
            if(!bottomLeftExtends){
                if(piece.checkRight() == 0 || peekTopLeft() == 0){
                    return true;
                }else {
                    return (peekTopLeft() == piece.checkRight());
                }
            }
        }

        //check top right
        if(side == Side.RIGHT && row == Row.TOP){
            if(!topRightExtends){
                if(piece.checkLeft() == 0 || peekBottomRight() == 0){
                    return true;
                }else {
                    return (peekBottomRight() == piece.checkLeft());
                }
            }
        }

        //check bottom right
        if(side == Side.RIGHT && row == Row.BOTTOM){
            if(!bottomRightExtends){
                if(piece.checkLeft() == 0 || peekTopRight() == 0){
                    return true;
                }else {
                    return (peekTopRight() == piece.checkLeft());
                }
            }
        }
        return false;
    }

    /**
     * this will return the number on the left edge of the top row
     *
     * @return the int from the left side of the domino in the left edge of the top row
     */
    public int peekTopLeft(){
        return topRow.peekFirst().checkLeft();
    }

    /**
     *
     * this will return the number on the right edge of the top row.
     *
     * @return the int from the right side of the domino in the right edge of the top row
     */
    public int peekTopRight(){
        return topRow.peekLast().checkRight();
    }

    /**
     * this will return the int on the left edge of the bottom row.
     * @return the int from the left side of the domino in the left edge of the bottom row
     */
    public int peekBottomLeft(){
        return bottomRow.peekFirst().checkLeft();
    }

    /**
     *
     * this will return the number on the right edge of the bottom row.
     *
     * @return the int from the right side of the domino in the right edge of the bottom row
     */
    public int peekBottomRight(){
        return bottomRow.peekLast().checkRight();
    }

    /**
     * this checks to see if the top left extends
     * @return true if the left side of the top row should display extended if it were to be drawn
     */
    public boolean topRowPosition() {
        return topLeftExtends;
    }

    /**
     *
     * @return Domino[] representing the top row.
     */
    public Domino[] topRowToArray(){
        Domino[] output = new Domino[topRow.size()];
        topRow.toArray(output);
        return output;
    }

    /**
     *
     * @return Domino[] representing the bottom row.
     */
    public Domino[] bottomRowToArray(){
        Domino[] output = new Domino[bottomRow.size()];
        return bottomRow.toArray(output);
    }


}
