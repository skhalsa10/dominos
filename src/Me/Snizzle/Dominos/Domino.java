package Me.Snizzle.Dominos;


public class Domino {
    private int left;
    private int right;

    /**
     * constructs a new Domino eith a left and right value for each side of the domino. this domino game is basic
     * so the domino is only in a horizontal position.
     * @param left this is set to the left side.
     * @param right this is set to the right side.
     */
    public Domino(int left, int right){
        this.left = left;
        this.right = right;
    }

    public void rotate(){
        int tmp = this.left;
        this.left = right;
        this.right = tmp;
    }


    @Override
    public boolean equals(Object o){
        //null check
        if(o == null) {
            return false;
        }

        //reference check
        if (o == this) {
            return true;
        }

        //instance check
        if(!(o instanceof Domino)){
            return false;
        }

        Domino dom = (Domino)o;

        //valueCheck
        return (this.left == dom.left && this.right ==dom.right);

    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        return prime* (result+left+right);
    }


}
