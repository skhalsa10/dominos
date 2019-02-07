package Me.Snizzle.Dominos;

import Me.Snizzle.Dominos.Renderers.FXRenderer;
import javafx.application.Application;
import javafx.stage.Stage;


public class GUIMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Dominos");

        FXRenderer fxRenderer = new FXRenderer(primaryStage);
        DominoGame dominos = new DominoGame(fxRenderer);
        GUIGameLoop gameLoop = new GUIGameLoop(dominos, fxRenderer);

        primaryStage.show();
        gameLoop.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
