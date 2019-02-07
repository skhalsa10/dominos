package Me.Snizzle.Dominos;

import Me.Snizzle.Dominos.Renderers.DominoGameRenderer;
import javafx.animation.AnimationTimer;

public class GUIGameLoop extends AnimationTimer implements Loop {
    private DominoGame gameLogic;
    private DominoGameRenderer gameRenderer;


    public GUIGameLoop(DominoGame logic, DominoGameRenderer renderer){
        this.gameLogic = logic;
        this.gameRenderer = renderer;

    }

    @Override
    public void loop() {
        if(gameLogic.isGameOver()){this.stop();}
        gameLogic.export(gameRenderer);
        gameRenderer.render();
        gameLogic.step();



    }

    @Override
    public void handle(long now) {
        //System.out.println(now);

        gameLogic.export(gameRenderer);
        gameRenderer.render();
        gameLogic.step();
        //loop();
    }
}
