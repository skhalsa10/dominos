package Me.Snizzle.Dominos;

public class DominoGame  implements Logic{

    public boolean isGameOver;
    private Player user;
    private Player computer;
    private Board board;
    private Boneyard boneyard;
    private boolean isUserTurn;
    private Importer importer;

    public DominoGame(Importer importer){
        this.importer = importer;
        board = new Board();
        boneyard = new Boneyard(6);
        user = new Player(boneyard, board);
        computer = new Player(boneyard, board);
        board = new Board();
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

    public interface Importer {
        String[] fetchData();
    }



    public interface Exporter {
        void displayBoard(boolean topLeftExtends, Domino[] top, Domino[] bottom);
        void displayUserHand(Domino[] userHand);
    }

    @Override
    public void step() {

        if(isUserTurn){
            //lets import the user move
            String[] move = importer.fetchData();
            //if the user draws from the boneyard and it is empty then they lose
            if(move.length == 1 && move[0] == "draw"){
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
            //we are now in the computers move
            computer.playCompHand();
        }

    }


}
