package World_sim;

import javafx.util.Pair;

public class Fox extends Animal{

    Fox(World w, Logger l, int s, int a,Pair<Integer, Integer> pos){
        super(w, l, s, Config.FOX_INIT,a,Config.FOX_SPEC, pos);
    }

    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Fox(w,l, Config.FOX_STRENGTH,0,pos);
    }

    @Override
    void draw() {

    }

    @Override
    boolean goodSmell(Organism defender)
    {
        if (defender == null || defender.getStrength() <= getStrength())
            return false;
        logger.addLog(getSpecies() + " hasn't attacked " + defender.getSpecies() + " because of good smell");
        return true;
    }
}
