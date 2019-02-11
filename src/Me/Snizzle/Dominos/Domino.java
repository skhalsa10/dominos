package Me.Snizzle.Dominos;

/**
 * This class represents a Domino
 *
 * @author Siri Khalsa
 * @version 2/10/19
 */
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

    /**
     * this method will swap the left and right values... essentially rotating it 180 degrees.
     * this works for this game because the dominos can only be horizontal.
     *
     * IM SERIOUSLY CONSIDERING CONVERTING THE LEFT AND RIGHT FIELDS TO FINAL TO ENFORCE THE IDEA OF ENCAPSULATION
     * I CAN IMPLEMENT A ROTATE WITH A BOOLEAN THAT CHANGES RETURN LOGIC. IT IS ALL CONTROLLED IN THE CLASS.
     * NO ONE ELSE WOULD REALLY KNOW WHAT I AM DOING. I THINK THE RISK OF LEAVING AS IS is minimal though and easier to understand.
     *
     */
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

    /**
     * I hesitate to put this in here as getters and setter violate encapsulation that we attempt to enforce
     * in OOD. but the risk is generally minimal here. I may come back and change logic but this method will be used either way.
     *
     * I made the name seem more like behavior compared to an accessor.
     *
     * @return the right side integer representation of the domino.
     */
    public int checkRight() {
        return this.right;
    }

    /**
     * I hesitate to put this in here as getters and setter violate encapsulation that we attempt to enforce
     * in OOD. but the risk is generally minimal here. I may come back and change logic but this method will be used either way
     *
     * I made the name seem more like behavior compared to an accessor.
     *
     * @return the right side integer representation of the domino.
     */
    public int checkLeft() {
        return this.left;
    }

    /**
     *
     * @return this returns a text representation of a Domino in an ASCII art form
     */
    @Override
    public String toString() {
        String out = " __ __ \n" +
                     "|"+ this.left + " | " + this.right + "|\n" +
                     "`~~'~~'";
        return out;
    }
}
