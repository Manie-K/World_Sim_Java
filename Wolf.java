package World_sim;

import javafx.util.Pair;

public class Wolf extends Animal {
    public Wolf(World w, Logger l, int s, Pair<Integer, Integer> pos){
        super(w, l, s, Config.WOLF_INIT,Config.WOLF_SPEC, pos);
    }
    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Wolf(w,l,Config.WOLF_STRENGTH,pos);
    }

    @Override
    public void draw() {

    }
}
