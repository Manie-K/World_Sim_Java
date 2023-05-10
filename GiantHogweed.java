package World_sim;

import javafx.util.Pair;

import java.awt.*;

public class GiantHogweed extends Plant{
    GiantHogweed(World w, Logger l, int a,Pair<Integer, Integer> pos){
        super(w,l,0,a,Config.GIANT_HOGWEED_SPEC,pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new GiantHogweed(w,l,0,pos);
    }
    @Override
    Color draw() {
        return Config.GIANT_HOGWEED_COLOR;
    }
    @Override
    boolean kill(Organism attacker) {
        killOrganism(attacker);
        logger.addLog(attacker.getSpecies() + " died from eating " + getSpecies());
        return true;
    }
    @Override
    void action()
    {
        world.killNearbyAnimals(this);
    }
}
