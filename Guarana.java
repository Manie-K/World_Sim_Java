package World_sim;

import javafx.util.Pair;

import java.awt.*;

public class Guarana extends Plant{
    Guarana(World w, Logger l, int a,Pair<Integer, Integer> pos){
        super(w,l,0,a,Config.GUARANA_SPEC,pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Guarana(w,l,0,pos);
    }
    @Override
    Color draw() {
        return Config.GUARANA_COLOR;
    }
    @Override
    void modify(Organism attacker)
    {
        attacker.setStrength(attacker.getStrength()+3);
        logger.addLog(attacker.getSpecies() + " got stronger beacause it ate " + getSpecies());
    }
}
