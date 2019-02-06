package Me.Snizzle.Dominos;

public class Player {
    private Hand hand;
    private Boneyard boneyard;
    private Board board;


    /**
     * constructs a new player
     * @param boneyard the boneyard reference
     * @param board the board reference
     */
    public Player(Boneyard boneyard, Board board){
        this.board = board;
        this.boneyard = boneyard;
        this.hand = new Hand();
        initHand();
    }

    /**
     * this method draws seven dominos from the boneyard.
     */
    private void initHand() {
        for(int i = 0; i<7; i++){
            hand.add(boneyard.draw());
        }
    }

    /**
     *  This function draws from the bonyard. and adds it to the hand of the player
     *
     * @return  true on success false if the boneyard isEmpty
     */
    public boolean drawFromBoneyard(){
        if(boneyard.isEmpty()){return false;}

        Domino piece = boneyard.draw();

        hand.add(piece);
        return true;
    }

    /**
     * this exports the current state of the hand in a domino array
     * @return Domino[] representation of the the players hand
     */
    public Domino[] handToArray(){
        return hand.toArray();
    }


    /**
     * this method takes the following valid string[]
     * [index, top|bottom , left|right]
     * or
     * [index, top|bottom , left|right, rotate]
     *
     * @param move a string array representing the move
     * @return true on success false on  failure
     */
    public boolean  playHand(String[] move){

        int handI;
        Board.Side side = null;
        Board.Row row = null;
        Domino piece;
        //confirm valid array size

        if(move.length != 3 && move.length != 4){return false;}

        //try to parse the first element which should be an int. if it fails use this time to return false
        try{
            handI = Integer.parseInt(move[0]);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return false;
        }

        //the second element can be top or bottom set state accordingly
        if(move[1].equals("top")){
            row = Board.Row.TOP;
        }else if(move[1].equals("bottom")){
            row = Board.Row.BOTTOM;
        }else{
            return false;
        }
        //the third element can be left or right set state accordingly
        if(move[2].equals("left")){
            side = Board.Side.LEFT;
        }else if(move[2].equals("right")){
            side = Board.Side.RIGHT;
        }else {
            return false;
        }
        //rotate if necessary
        if(move.length == 4 && move[3].equals("rotate")){ hand.rotate(handI);}

        //load piece to a local variable (locality principle can increase performance not by much though)
        piece = hand.remove(handI);

        //add piece to the board if it is a legal move
        if(!board.add(side,row,piece)){
            hand.add(piece);
            return false;
        }
        return true;
    }

    /**
     * if the player happens to be a computer this method can run.
     * @return true if the move is successful and false
     * if the computer lost by drawing  from the empty boneyard
     */
    public boolean playCompHand(){
        //lets first set up the logic of when the computer has empty hand
        if(hand.isEmpty()){
            //if the boneyard is also empty return false
            if(!drawFromBoneyard()){return false;}
        }


        //loop over every domino and play first valid move(really dumb computer.)
        for (int i = 0; i< hand.size();i++) {
            if(checkAndAdd(i)){return true;}
        }
        //if there are no valid moves then draw and until the boneyard is empty and play the first valid move.
        while(drawFromBoneyard()){
            int i = (hand.size()-1);
            //check the new piece
            if(checkAndAdd(i)){return true;}
        }
        //if we have exuasted the entire boneyard without playing a move than return false
        return false;
    }

    /**
     * this private method will take an index into the player hand  and check to see if that
     * piece can be played anywhere on the board
     * @param index
     * @return
     */
    private boolean checkAndAdd(int index){
        Domino piece = hand.get(index);
        if(board.checkValid(Board.Side.LEFT, Board.Row.TOP,piece)){
            board.add(Board.Side.LEFT, Board.Row.TOP,hand.remove(index));
            return true;
        }
        if(board.checkValid(Board.Side.LEFT, Board.Row.BOTTOM,piece)){
            board.add(Board.Side.LEFT, Board.Row.BOTTOM,hand.remove(index));
            return true;
        }
        if(board.checkValid(Board.Side.RIGHT, Board.Row.TOP,piece)){
            board.add(Board.Side.RIGHT, Board.Row.TOP,hand.remove(index));
            return true;
        }
        if(board.checkValid(Board.Side.RIGHT, Board.Row.BOTTOM,piece)){
            board.add(Board.Side.RIGHT, Board.Row.BOTTOM,hand.remove(index));
            return true;
        }

        //rotate the piece and check again
        hand.rotate(index);
        piece = hand.get(index);
        if(board.checkValid(Board.Side.LEFT, Board.Row.TOP,piece)){
            board.add(Board.Side.LEFT, Board.Row.TOP,hand.remove(index));
            return true;
        }
        if(board.checkValid(Board.Side.LEFT, Board.Row.BOTTOM,piece)){
            board.add(Board.Side.LEFT, Board.Row.BOTTOM,hand.remove(index));
            return true;
        }
        if(board.checkValid(Board.Side.RIGHT, Board.Row.TOP,piece)){
            board.add(Board.Side.RIGHT, Board.Row.TOP,hand.remove(index));
            return true;
        }
        if(board.checkValid(Board.Side.RIGHT, Board.Row.BOTTOM,piece)){
            board.add(Board.Side.RIGHT, Board.Row.BOTTOM,hand.remove(index));
            return true;
        }
        return false;
    }
}
