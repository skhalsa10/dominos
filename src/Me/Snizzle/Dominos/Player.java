package Me.Snizzle.Dominos;

public class Player {
    private Hand hand;
    private Boneyard boneyard;
    private Board board;


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
        if(move.length != 3 || move.length != 4){return false;}

        //try to parse the first element which should be an int. if it fails use this time to return false
        try{
            handI = Integer.parseInt(move[0]);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return false;
        }
        //the second element can be top or bottom set state accordingly
        if(move[1] == "top"){
            row = Board.Row.TOP;
        }else if(move[1] == "bottom"){
            row = Board.Row.BOTTOM;
        }else{
            return false;
        }
        //the third element can be left or right set state accordingly
        if(move[2] == "left"){
            side = Board.Side.LEFT;
        }else if(move[2] == "right"){
            side = Board.Side.RIGHT;
        }else {
            return false;
        }
        //rotate if necessary
        if(move.length == 4 && move[3] == "rotate"){ hand.rotate(handI);}

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
     * if the player happens to be a computer
     * @return
     */
    public boolean playCompHand(){
        return false;
    }
}
