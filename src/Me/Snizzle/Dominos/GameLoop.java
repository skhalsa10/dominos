package Me.Snizzle.Dominos;

import Me.Snizzle.Dominos.Renderers.DominoGameRenderer;

/**
 * this Gameloop loops between updating the state rendering the state and then collecting new data for the state
 * This should work for both GUI and text but I ended up making a new GUI Game loop troubleshooting GUI problems
 *
 * Since it is working I will leave as is
 *
 * @author Siri Khalsa
 * @version 2/10/19
 */
public class GameLoop implements Loop{
    //I really want to use an interface here so I can reuse this gameloop later without changing anything.
    //but I cant figure out how to create an interface that describes  a class that defines different exporter interfaces
    // i guess changing two lines of code is better than a hundred through out the project.
    private DominoGame gameLogic;
    private DominoGameRenderer gameRenderer;

    /**
     * initialized the gameloop with a renderer and a Domino Game
     * @param logic the DominoGame instance
     * @param renderer
     */
    public GameLoop(DominoGame logic, DominoGameRenderer renderer){
        this.gameLogic = logic;
        this.gameRenderer = renderer;
    }

    /**
     * this will loop until the game is over
     */
    @Override
    public void loop(){
        while(!gameLogic.isGameOver()){
            //export the current state of the game to the renderer
            gameLogic.export(gameRenderer);
            System.out.println("gameloop");
            //render it whether it be text or gui... or some other representation in the future that implements the interface
            gameRenderer.render();
            //take a step through the state(this should allow for animation later. I can simulate my own 60 frames per second loop.
            gameLogic.step();

        }

    }
}
