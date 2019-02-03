package Me.Snizzle.Dominos;

import Me.Snizzle.Dominos.Renderers.DominoGameRenderer;

public class GameLoop {
    //I really want to use an interface here so I can reuse this gameloop later without changing anything.
    //but I cant figure out how to create an interface that describes  a class that defines different exporter interfaces
    // i guess changing two lines of code is better than a hundred through out the project.
    private DominoGame gameLogic;
    private DominoGameRenderer gameRenderer;

    public GameLoop(DominoGame logic, DominoGameRenderer renderer){
        this.gameLogic = logic;
        this.gameRenderer = renderer;
    }

    public void loop(){
        //take a step through the state(this should allow for animation later. I can simulate my own 60 frames per second loop.
        gameLogic.step();
        //export the current state of the game to the renderer
        gameLogic.export(gameRenderer);
        //render it whether it be text or gui... or some other representation in the future that implements the interface
        gameRenderer.render();
    }
}
