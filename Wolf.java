package World_sim;

import javafx.util.Pair;

import java.awt.*;

public class Wolf extends Animal {
    Wolf(World w, Logger l, int s,int a, Pair<Integer, Integer> pos){
        super(w, l, s, Config.WOLF_INIT,a,Config.WOLF_SPEC, pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Wolf(w,l,Config.WOLF_STRENGTH,0,pos);
    }

    @Override
    Color draw() {
        return Config.WOLF_COLOR;
    }
}
