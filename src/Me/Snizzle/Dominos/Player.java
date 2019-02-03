package Me.Snizzle.Dominos;

public class Player {
    private Hand hand;
    private Boneyard boneyard;


    public Player(Boneyard boneyard){
        this.boneyard = boneyard;
        this.hand = new Hand();
        initHand();
    }

    /**
     * this method draws seven dominos from the boneyard.
     */
    private void initHand() {
        for(int i = 0; i<7; i++){
            hand.add(boneyard.draw());
        }
    }

    /**
     * this exports the current state of the hand in a domino array
     * @return Domino[] representation of the the players hand
     */
    public Domino[] handToArray(){
        return hand.toArray();
    }
}
