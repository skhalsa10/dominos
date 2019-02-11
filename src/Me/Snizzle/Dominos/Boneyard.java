package Me.Snizzle.Dominos;

import java.util.*;

/**
 * This class represents a boneyard for the DominoGame
 *
 * @author Siri Khalsa
 * @version 2/10/19
 *
 */
public class Boneyard {
    //collection that stores the dominos can be a list ... All i need to do is pull of the end.
    LinkedList<Domino> boneyard;

    /**
     * construxts a new boneyard full of all pieces. the boneyard is shuffled.
     * @param maxNum
     */
    public Boneyard(int maxNum){
        boneyard = newBoneYard(maxNum);
    }

    //this is used to generate a shuffled link list of dominos
    private LinkedList<Domino> newBoneYard(int maxNum) {
        var hset = new HashSet<Domino>();
        int p = 0;
        while (p<=maxNum){
            for (int i = p; i<=maxNum; i++){
                hset.add(new Domino(p,i));
            }
            p++;
        }

        LinkedList<Domino> list = new LinkedList<>(hset);
        Collections.shuffle(list);

        return list;
    }

    /**
     * this method checks to see if the boneyard is empty.
     * @return returns true if the boneyard is empty.
     */
    public boolean isEmpty(){
        return boneyard.isEmpty();
    }

    /**
     * this method draws a domino piece from the boneyard. and returns it to the caller.
     * @return Random domino in boneyard. returns NUll if the Boneyard is empty.
     */
    public Domino draw(){
        return boneyard.pollFirst();
    }
}
