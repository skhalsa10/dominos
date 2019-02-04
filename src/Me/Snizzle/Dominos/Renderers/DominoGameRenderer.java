package Me.Snizzle.Dominos.Renderers;

import Me.Snizzle.Dominos.DominoGame;

/**
 * this interface defines behavior to render export from a DominoGame and to import into a DominoGame
 */
public interface DominoGameRenderer extends DominoGame.Exporter, DominoGame.Importer {
    void render();
}
