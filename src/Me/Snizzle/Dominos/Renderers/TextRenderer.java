package Me.Snizzle.Dominos.Renderers;

import Me.Snizzle.Dominos.Domino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * this Class renderers  a domino game with text on a console.
 *
 * @author Siri Khalsa
 * @version  2/3/19
 *
 * @see Me.Snizzle.Dominos.DominoGame.Exporter
 * @see Me.Snizzle.Dominos.DominoGame.Importer
 * @see Me.Snizzle.Dominos.Renderers.DominoGameRenderer
 */
public class TextRenderer implements DominoGameRenderer {
    private boolean topLeftExtends;
    private Domino[] top;
    private Domino[] bottom;
    private Domino[] userHand;

    /**
     * constructs a new TextRenderer in a default state.
     */
    public TextRenderer(){
        topLeftExtends = false;
        top = null;
        bottom = null;
        userHand = null;
    }

    /**
     * this rendors the state that was imported. in this renderer it rendors it in text
     */
    @Override
    public void render() {
        rendorBoard();
        System.out.println();
        renderUserHand();
        //printInstructions();
    }

    /**
     * this prints instructions on how to give commands
     */
    private void printInstructions() {
        System.out.println("\n\nPlease Respond with your move it the following formats are accepted rotate implies the " +
                            "piece must be rotated before placed it is optional.:\n"
                            + "[draw]\n"
                            + "or\n"
                            + "[index] [top|bottom] [left|right] [rotate (optional)]");
        System.out.println("EXAMPLE: 5 top left rotate");
    }

    //rendors the user hand. still horrible to understand but it should work to get a grade?
    private void renderUserHand() {
        StringBuilder topEdge = new StringBuilder();
        StringBuilder center = new StringBuilder();
        StringBuilder bottomEdge = new StringBuilder();
        StringBuilder indices = new StringBuilder();
        String hand;

        int i = 0;

        for (Domino domino:userHand) {
            topEdge.append(" __ __ ");
            center.append("|"+ domino.checkLeft() + " | " + domino.checkRight() + "|");
            bottomEdge.append("`~~'~~'");
            indices.append("  ("+i+")  ");
            i++;
        }

        topEdge.append("\n");
        topEdge.append(center);
        topEdge.append("\n");
        topEdge.append(bottomEdge);
        topEdge.append("\n");
        topEdge.append(indices);
        hand = topEdge.toString();

        System.out.println(hand);

    }

    //this rendors the boartd. its very long and horrible to understand sorry. it is probably full of bugs... oh well.
    private void rendorBoard() {
        StringBuilder topEdge = new StringBuilder();
        StringBuilder center = new StringBuilder();
        StringBuilder bottomEdge = new StringBuilder();
        String topR;
        String bottomR;

        if (!topLeftExtends){
            topEdge.append("   ");
            center.append("   ");
            bottomEdge.append("   ");
        }
        //build text of the top row of the board.
        for (Domino domino:top) {
            topEdge.append(" __ __ ");
            center.append("|"+ domino.checkLeft() + " | " + domino.checkRight() + "|");
            bottomEdge.append("`~~'~~'");
        }
        topEdge.append("\n");
        topEdge.append(center);
        topEdge.append("\n");
        topEdge.append(bottomEdge);
        topR = topEdge.toString();

        //now build the second row
        topEdge = new StringBuilder();
        center = new StringBuilder();
        bottomEdge = new StringBuilder();

        //space for bottom row
        if (topLeftExtends){
            topEdge.append("   ");
            center.append("   ");
            bottomEdge.append("   ");
        }
        //build string of bottom row
        for (Domino domino:bottom) {
            topEdge.append(" __ __ ");
            center.append("|"+ domino.checkLeft() + " | " + domino.checkRight() + "|");
            bottomEdge.append("`~~'~~'");
        }
        topEdge.append("\n");
        topEdge.append(center);
        topEdge.append("\n");
        topEdge.append(bottomEdge);
        bottomR = topEdge.toString();

        System.out.println(topR);
        System.out.println(bottomR);

    }

    /**
     * stores the state needed to render the board
     * @param topLeftExtends this determines how the rows are staggard
     * @param top Domino[] representing the top row.
     * @param bottom Domino[] representing the bottom row.
     */
    @Override
    public void displayBoard(boolean topLeftExtends, Domino[] top, Domino[] bottom) {
        this.topLeftExtends = topLeftExtends;
        this.top = top;
        this.bottom = bottom;
    }

    /**
     * this stores the userhand locally to display when requested to
     * @param userHand this is a domino[] representing the user hand.
     */
    @Override
    public void displayUserHand(Domino[] userHand) {

        this.userHand = userHand;

    }

    /**
     * this method is somewhat messy. I am probably not the best at parsing text but whatever.
     * the purpose of this method is to collect the move from the user.
     * @return the move in the form of a String[]
     */
    @Override
    public String[] fetchData() {
        //lets make a reader from the console and set  a string to hold this input and a string array to
        //keep track of the arguments
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userMoveIn = null;
        String[] userMoveOut = null;
        boolean valid = false;
        //lets loop until we get a valid move from the user
        while(!valid) {
            printInstructions();
            try {
                userMoveIn = reader.readLine();
            } catch (IOException e) {
                valid = false;
                e.printStackTrace();
            }
            //this parses through all valid  input
            if(userMoveIn != null){
                userMoveIn.toLowerCase();
                userMoveOut = userMoveIn.split(" ");
                if(userMoveOut.length == 1){
                    if(userMoveOut[0] == "draw"){valid = true;}
                }
                if(userMoveOut.length == 3 || userMoveOut.length == 4){
                    //lets assume true;
                    valid = true;
                    try {
                        if (!(Integer.parseInt(userMoveOut[0])<userHand.length)){
                            valid = false;
                        }
                        if(!(userMoveOut[1] == "top" ||userMoveOut[1] == "bottom")){
                            valid = false;
                        }
                        if(!(userMoveOut[2] == "left" ||userMoveOut[1] == "right")){
                            valid = false;
                        }
                        if(userMoveOut.length == 4 && userMoveOut[3] != "rotate"){
                            valid = false;
                        }
                    }catch(NumberFormatException e){
                        valid = false;
                        e.printStackTrace();
                    }
                }
            }
        }

        return userMoveOut;

    }
}
