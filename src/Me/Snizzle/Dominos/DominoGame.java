package Me.Snizzle.Dominos;

import java.util.ArrayList;

public class DominoGame  implements Logic{

    private Player user;
    private Player computer;
    private Board board;
    private Boneyard boneyard;
    private boolean isUserTurn;

    public DominoGame(){
        boneyard = new Boneyard(6);
        user = new Player(boneyard);
        computer = new Player(boneyard);
        board = new Board();
        isUserTurn = true;
    }

    public void export(Exporter display){
        display.displayBoard(board.topRowPosition(), board.topRowToArray(), board.bottomRowToArray());
        display.displayUserHand(user.handToArray());
    }



    public interface Exporter {
        void displayBoard(boolean topLeftExtends, Domino[] top, Domino[] right);
        void displayUserHand(Domino[] userHand);
    }

    @Override
    public void step() {
        System.out.println("stepping");
    }
}
