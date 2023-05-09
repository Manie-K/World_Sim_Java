package World_sim;

import javafx.util.Pair;

public class Grass extends Plant{

    public Grass(World w, Logger l,Pair<Integer, Integer> pos){
        super(w,l,0,Config.GRASS_SPEC,pos);
    }
    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Grass(w,l,pos);
    }

    @Override
    public void draw() {

    }

}
