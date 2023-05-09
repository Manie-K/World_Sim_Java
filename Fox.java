package World_sim;

import javafx.util.Pair;

public class Fox extends Animal{

    Fox(World w, Logger l, int s, Pair<Integer, Integer> pos){
        super(w, l, s, Config.FOX_INIT,Config.FOX_SPEC, pos);
    }

    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Fox(w,l, Config.FOX_STRENGTH,pos);
    }

    @Override
    void draw() {

    }

    @Override
    boolean goodSmell(Organism defender)
    {
        return true;
    }
}
