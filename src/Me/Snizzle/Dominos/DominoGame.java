package Me.Snizzle.Dominos;

public class DominoGame  implements Logic{

    interface Exporter {
        //write functions of data that needs to be exported
    }

    @Override
    public void step() {
        System.out.println("stepping");
    }
}
