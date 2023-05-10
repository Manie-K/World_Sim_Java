package World_sim;

import javafx.util.Pair;

public class Grass extends Plant{

    Grass(World w, Logger l,int a,Pair<Integer, Integer> pos){
        super(w,l,0,a,Config.GRASS_SPEC,pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Grass(w,l,0,pos);
    }

    @Override
    void draw() {

    }

}
