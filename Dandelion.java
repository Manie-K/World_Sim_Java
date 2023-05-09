package World_sim;

import javafx.util.Pair;

public class Dandelion extends Plant{
    Dandelion(World w, Logger l,Pair<Integer, Integer> pos){
        super(w,l,0,Config.DANDELION_SPEC,pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Dandelion(w,l,pos);
    }
    @Override
    void draw() {

    }
    @Override
    int getChances(){return 3;}
}
