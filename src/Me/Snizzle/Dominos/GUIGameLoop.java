package Me.Snizzle.Dominos;

import Me.Snizzle.Dominos.Renderers.DominoGameRenderer;
import javafx.animation.AnimationTimer;

/**
 * this class was made during troubleshooting for the gui portion of this game. this can actually
 * be merged with the regular Gameloop. but in the sake of time management I will leave as is.
 */
public class GUIGameLoop extends AnimationTimer implements Loop {
    private DominoGame gameLogic;
    private DominoGameRenderer gameRenderer;


    /**
     * this constructs a new GUIGameLoop
     * @param logic this is the DominoGame Logic
     * @param renderer this is the renderer used in the case for this it will be the FX renderor but should also work with
     *                 the textRenderer
     */
    public GUIGameLoop(DominoGame logic, DominoGameRenderer renderer){
        this.gameLogic = logic;
        this.gameRenderer = renderer;

    }

    /**
     * this class implements the loop because it promises this behavior
     */
    @Override
    public void loop() {
        if(gameLogic.isGameOver()){this.stop();}
        gameLogic.export(gameRenderer);
        gameRenderer.render();
        gameLogic.step();



    }

    /**
     * In theory we should be able to use the loop above but with out a second thread it will fail. the animation timer handles this better
     * @param now
     */
    @Override
    public void handle(long now) {


        gameLogic.export(gameRenderer);
        gameRenderer.render();
        gameLogic.step();

    }
}
