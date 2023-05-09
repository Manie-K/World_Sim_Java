package World_sim;

import javafx.util.Pair;

public class Antelope extends Animal{
    public Antelope(World w, Logger l, int s, Pair<Integer, Integer> pos){
        super(w, l, s, Config.ANTELOPE_INIT,Config.ANTELOPE_SPEC, pos);
    }

    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Antelope(w,l, Config.ANTELOPE_STRENGTH, pos);
    }

    @Override
    public void draw() {

    }

    @Override
    public int howManyMoves(){return 2;}

    @Override
    public boolean defenderFlee(Animal attacker){return true;}
}
