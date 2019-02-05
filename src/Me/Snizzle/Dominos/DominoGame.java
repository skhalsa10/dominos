package Me.Snizzle.Dominos;

public class DominoGame  implements Logic{

    private boolean isGameOver;
    private Player user;
    private Player computer;
    private Board board;
    private Boneyard boneyard;
    private boolean isUserTurn;
    private Importer importer;

    /**
     * contstructs a new DominoGame with default state
     * @param importer the importer that feeds user state back into the dominogame.
     */
    public DominoGame(Importer importer){
        this.importer = importer;
        board = new Board();
        boneyard = new Boneyard(6);
        user = new Player(boneyard, board);
        computer = new Player(boneyard, board);
        isUserTurn = true;
        isGameOver = false;
    }

    /**
     * this method exports the current state of the game to the display.
     * @param display
     */
    public void export(Exporter display){
        display.displayBoard(board.topRowPosition(), board.topRowToArray(), board.bottomRowToArray());
        display.displayUserHand(user.handToArray());
    }

    /**
     *
     * @return returns true if game is over.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * defines an importer that this DominoGame uses to pull data from the interface which is either a text
     * or a gui
     */
    public interface Importer {
        String[] fetchData();
    }


    /**
     * this defines an interface to export state from this data structure
     */
    public interface Exporter {
        void displayBoard(boolean topLeftExtends, Domino[] top, Domino[] bottom);
        void displayUserHand(Domino[] userHand);
    }

    /**
     * this method takes a step through the state.  in this case it collects data from the importer
     * and processes it if it is the user's turn.... if it is the computers turn  than the computer will run an algorithm
     *
     */
    @Override
    public void step() {

        if(isUserTurn){
            //lets import the user move
            String[] move = importer.fetchData();
            //if the user draws from the boneyard and it is empty then they lose
            if(move.length == 1 && move[0].equals("draw")){
                if(!user.drawFromBoneyard()){
                    isGameOver = true;
                }
                return;
            }

            //if user move is valid end turn
            if(user.playHand(move)){
                isUserTurn = false;
            }
        }else{
            isUserTurn = true;
            //we are now in the computers move
            isGameOver = !(computer.playCompHand());
        }

    }


}
