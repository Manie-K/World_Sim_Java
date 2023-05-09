package World_sim;

import javafx.util.Pair;

public class Guarana extends Plant{
    Guarana(World w, Logger l, Pair<Integer, Integer> pos){
        super(w,l,0,Config.GUARANA_SPEC,pos);
    }
    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Guarana(w,l,pos);
    }
    @Override
    void draw() {

    }
    @Override
    void modify(Organism attacker)
    {
        attacker.setStrength(attacker.getStrength()+3);
        //ADD LOGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGS
        //logger.addLog({ attacker->getSpecies() + " got stronger beacause it ate " + species, EAT });
    }
}
