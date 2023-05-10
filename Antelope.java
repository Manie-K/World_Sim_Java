package World_sim;

import javafx.util.Pair;

public class Antelope extends Animal{
    Antelope(World w, Logger l, int s, int a,Pair<Integer, Integer> pos){
        super(w, l, s, Config.ANTELOPE_INIT,a,Config.ANTELOPE_SPEC, pos);
    }

    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Antelope(w,l, Config.ANTELOPE_STRENGTH, 0,pos);
    }

    @Override
    void draw() {

    }

    @Override
    int howManyMoves(){return 2;}

    @Override
    boolean defenderFlee(Animal attacker){return true;}
}
