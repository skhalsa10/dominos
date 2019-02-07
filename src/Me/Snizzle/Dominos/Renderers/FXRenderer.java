package Me.Snizzle.Dominos.Renderers;

import Me.Snizzle.Dominos.Domino;
import Me.Snizzle.Dominos.Hand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



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
    private BorderPane root;
    private VBox handAndControls;
    private HBox hand;
    private HBox controls;
    private Button draw, rotate, finish;
    private int handPixelWidth;
    private int boardPixelWidth;

    /**
     * constructs a new javaFX Renderer on the primary Stage that is passed in.
     * @param primaryStage this is the javafx stage to rendor the game to
     */
    public FXRenderer(Stage primaryStage){
        topLeftExtends = false;
        sendRotate = false;
        sendDraw = false;
        turnComplete = false;
        //lets set up the stage, scene and root pane
        this.primaryStage = primaryStage;
        root = new BorderPane();
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

        //initialize stuff
        //add buttons to the controlbox
        hand = new HBox();
        Pane ctrlLSpacer = new Pane();
        ctrlLSpacer.setMinSize(5,1);
        Pane ctrlRSpacer = new Pane();
        ctrlRSpacer.setMinSize(5,1);
        controls = new HBox(ctrlLSpacer, draw, rotate, finish, ctrlRSpacer);
        controls.setSpacing(10);
        controls.setPadding(new Insets(20));
        controls.setAlignment(Pos.CENTER);
        hand.setAlignment(Pos.CENTER);
        handAndControls = new VBox(hand, controls);

        handAndControls.setAlignment(Pos.CENTER);
        root.setBottom(handAndControls);




        primaryStage.setScene(scene);
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
        sendRotate = true;
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

    }

    private void renderBoard() {
    }

    private void renderUserHand() {
        hand.getChildren().clear();
        Pane handLSpacer = new Pane();
        handLSpacer.setMinSize(10,1);
        Pane handRSpacer = new Pane();
        handRSpacer.setMinSize(10,1);
        Pane handRendorPane = new Pane();
        Canvas handRendorCanvas = new Canvas();
        handRendorPane.getChildren().add(handRendorCanvas);
        GraphicsContext hrcGC = handRendorCanvas.getGraphicsContext2D();
        hand.getChildren().addAll(handLSpacer,handRendorPane, handRSpacer);

        //this will calculate the index of the drawn dominos
        hand.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                System.out.println(primaryStage.getWidth());
                System.out.println(event.getX());
            }
        });

        //first calculate the size of the canvas and set the max of the stage
        handPixelWidth = userHand.length *(2*HALFDOMINOWIDTH);
        handRendorCanvas.setWidth(handPixelWidth);
        handRendorCanvas.setHeight(HALFDOMINOHEIGHT);
        primaryStage.setMinWidth(handPixelWidth+20);
        primaryStage.setMinHeight(500);

        hrcGC.setFill(Color.DARKGRAY);

        int x = 0;
        for (Domino piece: userHand) {
            hrcGC.fillRoundRect(x,0,(2*HALFDOMINOWIDTH),HALFDOMINOHEIGHT,10,10);
            hrcGC.setStroke(Color.BLACK);
            hrcGC.setLineWidth(3);
            hrcGC.strokeLine(x+HALFDOMINOWIDTH,5,x+HALFDOMINOWIDTH,HALFDOMINOHEIGHT-5);
            x += HALFDOMINOWIDTH*2;
        }

    }

    private HBox rendorPiece(Domino piece) {
        HBox renderedPiece = new HBox();
        //leftside of piece
        Canvas left = new Canvas();
        left.setWidth(HALFDOMINOWIDTH);
        left.setHeight(HALFDOMINOHEIGHT);
        GraphicsContext leftGC = left.getGraphicsContext2D();
        leftGC.setFill(Color.DARKSALMON);
        leftGC.fillRoundRect(0,0,(double)HALFDOMINOWIDTH,(double)HALFDOMINOHEIGHT,20,5 );
        renderedPiece.getChildren().add(left);
        renderedPiece.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                //System.out.println(event.);
            }
        });

        //right side
        /*Canvas right = new Canvas();
        right.setWidth(HALFDOMINOWIDTH);
        right.setHeight(HALFDOMINOHEIGHT);
        GraphicsContext rightGC = left.getGraphicsContext2D();
        rightGC.setFill(Color.DARKGRAY);
        rightGC.fillRoundRect(0,0,(double)HALFDOMINOWIDTH,(double)HALFDOMINOHEIGHT,5,5 );
        renderedPiece.getChildren().add(right);*/

        return renderedPiece;

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


        turnComplete = false;
        if(sendDraw){
            sendDraw = false;
            return new String[]{"draw"};
        }
        if(sendRotate){
            sendRotate = false;
            return new String[]{Integer.toString(index), row, side, "rotate"};
        }

        return new String[]{Integer.toString(index), row, side};
        //return new String[]{};
    }
}
