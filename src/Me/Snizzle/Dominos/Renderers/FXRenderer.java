package Me.Snizzle.Dominos.Renderers;

import Me.Snizzle.Dominos.Domino;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * this class renders the dominoGame with a gui.
 * this is pretty lengthy and complexly confusing
 * @author Siri Khalsa
 * @version 2/10/19
 */
public class FXRenderer implements DominoGameRenderer {
    //size of a half domino used for rendering
    private final int HALFDOMINOWIDTH = 50;
    private final int HALFDOMINOHEIGHT= 40;

    //domino game related stuff
    private boolean topLeftExtends;
    private Domino[] topRow;
    private Domino[] bottomRow;
    private Domino[] userHand;
    private int index;
    private String row;
    private String side;
    private boolean sendRotate;
    private boolean sendDraw;
    private boolean turnComplete;
    //JAVAFX related stuff
    private Stage primaryStage;
    private Scene scene;
    private VBox root;
    private HBox hand;
    private GraphicsContext hrcGC;
    private HBox controls;
    private Label moveInfo;
    private Button draw, rotate, finish;
    //board related
    private HBox board;
    private GraphicsContext brcGC;
    private int handPixelWidth;
    private int boardPixelWidth;

    /**
     * constructs a new javaFX Renderer on the primary Stage that is passed in.
     * @param primaryStage this is the javafx stage to rendor the game to
     */
    public FXRenderer(Stage primaryStage){
        //initialize anything
        topLeftExtends = false;
        sendRotate = false;
        sendDraw = false;
        turnComplete = false;
        index = -1;
        //lets set up the stage, scene and root pane
        this.primaryStage = primaryStage;
        root = new VBox();
        scene = new Scene(root);

        //lets set up the control buttons
        draw = new Button("Draw");
        rotate = new Button("Rotate");
        finish = new Button("Finish");
        //set up the button listeners
        draw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setDraw();
            }
        });

        rotate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRotate();
            }
        });

        finish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setTurnComplete();
            }
        });


        //add buttons to the controlbox and the hand representation
        //hand
        hand = new HBox();
        hand.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                int margin = (int)(hand.getWidth()-(userHand.length*HALFDOMINOWIDTH*2))/2;

                index = (int)((event.getX()-margin)/100);
                //if this is the first selected piece then end turn immediately
                if(topRow.length == 0 && bottomRow.length == 0) {
                    row = "top";
                    side = "right";
                    turnComplete = true;
                }

                updateMInfo();

            }
        });

        //control buttons
        Pane ctrlLSpacer = new Pane();
        ctrlLSpacer.setMinSize(5,1);
        Pane ctrlRSpacer = new Pane();
        ctrlRSpacer.setMinSize(5,1);
        moveInfo = new Label("" + Integer.toString(index) + " - " + row + " - " + side + " rotate: " + sendRotate);
        controls = new HBox(ctrlLSpacer, draw, rotate, finish, moveInfo, ctrlRSpacer);
        controls.setSpacing(10);
        controls.setPadding(new Insets(20));
        controls.setAlignment(Pos.CENTER);
        hand.setAlignment(Pos.CENTER);

        board = new HBox();
        board.setPadding(new Insets(20));
        board.setAlignment(Pos.CENTER);
        board.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //set side
                if(event.getX()>(board.getWidth()/2)){
                    side = "right";
                }else{
                    side = "left";
                }
                //set row
                if(event.getY() > (board.getHeight()/2)){
                    row = "bottom";
                }else{
                    row = "top";
                }

                updateMInfo();            }
        });

        //add board hand and controls to the root
        root.getChildren().addAll(board,hand,controls);


        primaryStage.setScene(scene);
    }

    private void updateMInfo() {
        moveInfo.setText("" + Integer.toString(index) + " - " + row + " - " + side + " rotate: " + sendRotate);

    }

    /**
     * this completes the turn by turning on the turnComplete flag
     */
    private void setTurnComplete() {
        turnComplete = true;
    }

    /**
     * sets the rotate switch to on
     */
    private void setRotate() {
        if(sendRotate){
            sendRotate = false;
        }else {
            sendRotate = true;
        }
        updateMInfo();
    }

    /**
     * this completes the loop. turn is not really complete but the
     * state is allowed to get imported into the game logic
     */
    private void setDraw() {
        sendDraw = true;
        turnComplete = true;
    }


    /**
     * this method draws the game with the current state that has been imported
     */
    @Override
    public void render() {
        renderUserHand();
        renderBoard();
        setStageSize();
        updateMInfo();

    }

    /**
     * this sets the stage size to the smallest it can be to display all data
     */
    private void setStageSize() {
        //set the primary stage size
        if(handPixelWidth> boardPixelWidth) {
            primaryStage.setMinWidth(handPixelWidth + 20);
        }else{
            primaryStage.setMinWidth(boardPixelWidth + 20);
        }
        primaryStage.setMinHeight(290);
        //primaryStage.setMinHeight(board.getHeight()+hand.getHeight()+controls.getHeight());
    }

    /**
     * this goes through the board and renders it on the screen
     */
    private void renderBoard() {
        board.getChildren().clear();
        Pane boardLSpacer = new Pane();
        boardLSpacer.setMinSize(10,1);
        Pane boardRSpacer = new Pane();
        boardRSpacer.setMinSize(10,1);
        //board setup
        Pane boardRendorPane = new Pane();
        Canvas boardRendorCanvas = new Canvas();
        boardRendorPane.getChildren().add(boardRendorCanvas);
        brcGC = boardRendorCanvas.getGraphicsContext2D();
        //add the panes to the board
        board.getChildren().addAll(boardLSpacer,boardRendorPane,boardRSpacer);

        //set size of board canvas - do i need to check i think the rows are always the same size
        if(topRow.length > bottomRow.length) {
            boardPixelWidth = topRow.length*(HALFDOMINOWIDTH*2);
        }else{
            boardPixelWidth = bottomRow.length*(HALFDOMINOWIDTH*2);
        }
        //set size
        boardPixelWidth += HALFDOMINOWIDTH;
        boardRendorCanvas.setWidth(boardPixelWidth);
        boardRendorCanvas.setHeight(HALFDOMINOHEIGHT*2);

        //now render the first row
        int x = 0;
        int y = 0;
        if(!topLeftExtends){x = HALFDOMINOWIDTH;}
        for (Domino piece:topRow) {
            brcGC.setFill(Color.DARKGRAY);
            brcGC.fillRoundRect(x,y,(2*HALFDOMINOWIDTH),HALFDOMINOHEIGHT,10,10);
            brcGC.setStroke(Color.BLACK);
            brcGC.setLineWidth(3);
            brcGC.strokeLine(x+HALFDOMINOWIDTH,y+5,x+HALFDOMINOWIDTH,HALFDOMINOHEIGHT-5);
            rendorDominoPiece(piece, x,y, brcGC);
            x += HALFDOMINOWIDTH*2;

        }
        //second row
        x = 0;
        y = HALFDOMINOHEIGHT;
        if(topLeftExtends){x = HALFDOMINOWIDTH;}
        for (Domino piece:bottomRow) {
            brcGC.setFill(Color.DARKGRAY);
            brcGC.fillRoundRect(x,y,(2*HALFDOMINOWIDTH),HALFDOMINOHEIGHT,10,10);
            brcGC.setStroke(Color.BLACK);
            brcGC.setLineWidth(3);
            brcGC.strokeLine(x+HALFDOMINOWIDTH,y+5,x+HALFDOMINOWIDTH,y+HALFDOMINOHEIGHT-5);
            rendorDominoPiece(piece, x,y, brcGC);
            x += HALFDOMINOWIDTH*2;
        }

    }

    /**
     * this rendors the userhand
     */
    private void renderUserHand() {
        hand.getChildren().clear();
        Pane handLSpacer = new Pane();
        handLSpacer.setMinSize(10,1);
        Pane handRSpacer = new Pane();
        handRSpacer.setMinSize(10,1);
        Pane handRendorPane = new Pane();
        Canvas handRendorCanvas = new Canvas();
        handRendorPane.getChildren().add(handRendorCanvas);
        hrcGC = handRendorCanvas.getGraphicsContext2D();
        hand.getChildren().addAll(handLSpacer,handRendorPane, handRSpacer);

        //set size of handRendorCanvas
        handPixelWidth = userHand.length *(2*HALFDOMINOWIDTH);
        handRendorCanvas.setWidth(handPixelWidth);
        handRendorCanvas.setHeight(HALFDOMINOHEIGHT);


        //render the pieces.
        int x = 0;
        for (Domino piece: userHand) {
            hrcGC.setFill(Color.DARKGRAY);
            hrcGC.fillRoundRect(x,0,(2*HALFDOMINOWIDTH),HALFDOMINOHEIGHT,10,10);
            hrcGC.setStroke(Color.BLACK);
            hrcGC.setLineWidth(3);
            hrcGC.strokeLine(x+HALFDOMINOWIDTH,5,x+HALFDOMINOWIDTH,HALFDOMINOHEIGHT-5);
            rendorDominoPiece(piece, x,0, hrcGC);
            x += HALFDOMINOWIDTH*2;
        }

    }

    /**
     * draws a domono piece at given x inside of the graphicsContext gc
     * @param piece
     * @param x
     * @param gc
     */
    private void rendorDominoPiece(Domino piece, int x, int y, GraphicsContext gc) {

        renderNumber(piece.checkLeft(), x, y, gc);
        renderNumber(piece.checkRight(), x+HALFDOMINOWIDTH, y, gc);

    }

    /**
     * render the numbers assuming x is the left side of a half domino.
     * @param num the number to render
     * @param x the left side of the half domino
     * @param gc the graphics context to draw to.
     */
    private void renderNumber(int num, double x, double y, GraphicsContext gc){
        gc.setFill(Color.BLACK);
        double circleSize = 5;
        switch(num){
            case 0:
                break;
            case 1:
                //middle
                gc.fillOval(x+((HALFDOMINOWIDTH/2)-(circleSize/2)),y+((HALFDOMINOHEIGHT/2)-(circleSize/2)),circleSize,circleSize);
                break;
            case 2:
                //upper left
                gc.fillOval(x+8, y+8, circleSize,circleSize);
                gc.fillOval(x+((HALFDOMINOWIDTH-circleSize)-8), y+((HALFDOMINOHEIGHT-circleSize-8)), circleSize,circleSize);
                //lower right
                gc.fillOval(x+((HALFDOMINOWIDTH-circleSize)-8), y+((HALFDOMINOHEIGHT-circleSize-8)), circleSize,circleSize);
                break;
            case 3:
                //upper left
                gc.fillOval(x+8, y+8, circleSize,circleSize);
                //middle
                gc.fillOval(x+((HALFDOMINOWIDTH/2)-(circleSize/2)),y+((HALFDOMINOHEIGHT/2)-(circleSize/2)),circleSize,circleSize);
                //lower right
                gc.fillOval(x+((HALFDOMINOWIDTH-circleSize)-8), y+(HALFDOMINOHEIGHT-circleSize-8), circleSize,circleSize);
                break;
            case 4:
                //upper left
                gc.fillOval(x+8, y+8, circleSize,circleSize);
                //upper right
                gc.fillOval(x+(HALFDOMINOWIDTH-circleSize-8),y+8,circleSize,circleSize);
                //lower left
                gc.fillOval(x+8, y+(HALFDOMINOHEIGHT-circleSize-8),circleSize,circleSize);
                //lower right
                gc.fillOval(x+((HALFDOMINOWIDTH-circleSize)-8), y+(HALFDOMINOHEIGHT-circleSize-8), circleSize,circleSize);
                break;
            case 5:
                //upper left
                gc.fillOval(x+8, y+8, circleSize,circleSize);
                //upper right
                gc.fillOval(x+(HALFDOMINOWIDTH-circleSize-8),y+8,circleSize,circleSize);
                //lower left
                gc.fillOval(x+8, y+(HALFDOMINOHEIGHT-circleSize-8),circleSize,circleSize);
                //lower right
                gc.fillOval(x+((HALFDOMINOWIDTH-circleSize)-8), y+(HALFDOMINOHEIGHT-circleSize-8), circleSize,circleSize);
                //middle
                gc.fillOval(x+((HALFDOMINOWIDTH/2)-(circleSize/2)),y+((HALFDOMINOHEIGHT/2)-(circleSize/2)),circleSize,circleSize);
                break;
            case 6:
                //upper left
                gc.fillOval(x+8, y+8, circleSize,circleSize);
                //upper middle
                gc.fillOval(x+((HALFDOMINOWIDTH/2)-(circleSize/2)),y+8,circleSize,circleSize);
                //upper right
                gc.fillOval(x+(HALFDOMINOWIDTH-circleSize-8),y+8,circleSize,circleSize);
                //lower left
                gc.fillOval(x+8, y+(HALFDOMINOHEIGHT-circleSize-8),circleSize,circleSize);
                //lower middle
                gc.fillOval(x+((HALFDOMINOWIDTH/2)-(circleSize/2)),y+(HALFDOMINOHEIGHT-circleSize-8),circleSize,circleSize);
                //lower right
                gc.fillOval(x+((HALFDOMINOWIDTH-circleSize)-8), y+(HALFDOMINOHEIGHT-circleSize-8), circleSize,circleSize);
                break;


        }

    }

    /**
     * store the current state to render
     * @param topLeftExtends this is used to determine how to display the board
     * @param top Domino[] of the top row
     * @param bottom Domino[] of the bottom row of the board.
     */
    @Override
    public void displayBoard(boolean topLeftExtends, Domino[] top, Domino[] bottom) {
        this.topLeftExtends = topLeftExtends;
        this.topRow = top;
        this.bottomRow = bottom;
    }

    /**
     * stores the current state of the user Hand
     * @param userHand Domino[] representing the user's hand
     */
    @Override
    public void displayUserHand(Domino[] userHand) {
        this.userHand = userHand;
    }


    /**
     * this method will let the gamelogic know if it is ready to return data. this will
     * allow the thread that is drawing the screen to get the thread back. dont worry the thread will come back and
     * check if we are ready yet so we can take our time picking our move.
     * @return
     */
    @Override
    public boolean timeToFetchData() {
        if (turnComplete){
            return true;
        }
        return false;
    }

    /**
     * This gets called when the gameloop needs to import the players move. Since the GUI interaction is setting
     * event handlers we need something to keep this from returning to the game loop. so will use a loop that holds
     * the processor until all user data as been input.
     * @return a String[] accepting ["draw"] or ["index", "top|bottom", "right|left", "rotate"(optional)]
     */
    @Override
    public String[] fetchData() {
        String[] out;


        turnComplete = false;
        if(sendDraw){
            sendDraw = false;
            index = -1;
            row = null;
            side = null;
            return new String[]{"draw"};
        }
        if(sendRotate){
            out = new String[]{Integer.toString(index), row, side, "rotate"};
            sendRotate = false;
            index = -1;
            row = null;
            side = null;
            return out;
        }
        out = new String[]{Integer.toString(index), row, side};
        System.out.println(index+", "+ row+", " + side);
        index = -1;
        row = null;
        side = null;
        return out;
        //return new String[]{};
    }
}
