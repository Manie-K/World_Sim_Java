package World_sim;

import javafx.util.Pair;

public class Sheep extends Animal{
    Sheep(World w, Logger l, int s, Pair<Integer, Integer> pos){
        super(w, l, s,Config.SHEEP_INIT,Config.SHEEP_SPEC, pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Sheep(w,l,Config.SHEEP_STRENGTH,pos);
    }

    @Override
    void draw() {

    }
}
