package Me.Snizzle.Dominos;

import java.util.*;

public class Boneyard {
    //collection that stores the dominos can be a list ... All i need to do is pull of the end.
    LinkedList<Domino> boneyard;

    public Boneyard(int maxNum){
        boneyard = newBoneYard(maxNum);
    }

    private LinkedList<Domino> newBoneYard(int maxNum) {
        var hset = new HashSet<Domino>();
        int p = 0;
        while (p<10){
            for (int i = p; i<10; i++){
                hset.add(new Domino(p,i));
            }
            p++;
        }

        LinkedList<Domino> list = new LinkedList<>(hset);
        Collections.shuffle(list);

        return list;
    }

    public boolean isEmpty(){
        return boneyard.isEmpty();
    }

    public Domino draw(){
        return boneyard.pollFirst();
    }
}
