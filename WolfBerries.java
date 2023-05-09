package World_sim;

import javafx.util.Pair;

public class WolfBerries extends Plant{
    WolfBerries(World w, Logger l, Pair<Integer, Integer> pos){
        super(w,l,0,Config.WOLF_BERRIES_SPEC,pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new WolfBerries(w,l,pos);
    }
    @Override
    void draw() {

    }
    @Override
    boolean kill(Organism attacker) {
        killOrganism(attacker);
        //logger.addLog({attacker -> getSpecies() + " died from eating " + species, KILL});
        //ADD LOGSSSSSSSSSSSSSSSSSSSSS
        return true;
    }
}
