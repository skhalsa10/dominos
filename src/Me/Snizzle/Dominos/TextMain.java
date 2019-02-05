package Me.Snizzle.Dominos;

import Me.Snizzle.Dominos.Renderers.TextRenderer;

public class TextMain {

    public static void main(String[] args){
        TextRenderer txRenderer = new TextRenderer();
        DominoGame dominos = new DominoGame(txRenderer);
        GameLoop loop = new GameLoop(dominos,txRenderer);
        loop.loop();
    }
}
