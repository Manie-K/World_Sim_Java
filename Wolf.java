package World_sim;

import javafx.util.Pair;

public class Wolf extends Animal {
    Wolf(World w, Logger l, int s, Pair<Integer, Integer> pos){
        super(w, l, s, Config.WOLF_INIT,Config.WOLF_SPEC, pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Wolf(w,l,Config.WOLF_STRENGTH,pos);
    }

    @Override
    void draw() {

    }
}
