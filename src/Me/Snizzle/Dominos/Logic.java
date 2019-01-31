package Me.Snizzle.Dominos;


/**
 * this interface defines a generic logic. Anything that implements it has to have a step function.
 * This can be utilized with a common gameloop. the pattern is the gameloop steps the logic. exports the new state to the renderer then renders
 *
 */
public interface Logic {

    public void  step();

}
