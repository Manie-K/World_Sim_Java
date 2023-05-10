package World_sim;

import javafx.util.Pair;

import java.awt.*;

public class Sheep extends Animal{
    Sheep(World w, Logger l, int s,int a, Pair<Integer, Integer> pos){
        super(w, l, s,Config.SHEEP_INIT,a,Config.SHEEP_SPEC, pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Sheep(w,l,Config.SHEEP_STRENGTH,0,pos);
    }

    @Override
    Color draw() {
        return Config.SHEEP_COLOR;
    }
}
