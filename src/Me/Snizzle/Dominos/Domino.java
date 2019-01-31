package Me.Snizzle.Dominos;

public class Domino {
    private final int left;
    private final int right;

    public Domino(int left, int right){
        this.left = left;
        this.right = right;
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
        if(!(o instanceof Domino){
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

        return prime* (result +left +right);
    }


}
