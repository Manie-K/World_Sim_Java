package World_sim;

import javafx.util.Pair;

public class Antelope extends Animal{
    public Antelope(World w, Logger l, int s, int i, String species, Pair<Integer, Integer> pos){
        super(w, l, s, i, species, pos);
    }

    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Antelope(w,l, Config.ANTELOPE_STRENGTH, Config.ANTELOPE_INIT,Config.ANTELOPE_SPEC,pos);
    }

    @Override
    public void draw() {

    }

    @Override
    public int howManyMoves(){return 2;}

    @Override
    public boolean defenderFlee(Animal attacker){return true;}
}
