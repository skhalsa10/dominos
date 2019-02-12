package Me.Snizzle.Dominos;

import Me.Snizzle.Dominos.Renderers.TextRenderer;

/**
 * this is the main entry for the text based version of this game
 */
public class TextMain {

    public static void main(String[] args){
        TextRenderer txRenderer = new TextRenderer();
        DominoGame dominos = new DominoGame(txRenderer);
        GameLoop loop = new GameLoop(dominos,txRenderer);
        loop.loop();
    }
}
