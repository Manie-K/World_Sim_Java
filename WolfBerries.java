package World_sim;

import javafx.util.Pair;

import java.awt.*;

public class WolfBerries extends Plant{
    WolfBerries(World w, Logger l,int a, Pair<Integer, Integer> pos){
        super(w,l,0,a,Config.WOLF_BERRIES_SPEC,pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new WolfBerries(w,l,0,pos);
    }
    @Override
    Color draw() {
        return Config.WOLF_BERRIES_COLOR;
    }
    @Override
    boolean kill(Organism attacker) {
        killOrganism(attacker);
        logger.addLog(attacker.getSpecies() + " died from eating " + getSpecies());
        return true;
    }
}
