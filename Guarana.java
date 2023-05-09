package World_sim;

import javafx.util.Pair;

public class Guarana extends Plant{
    public Guarana(World w, Logger l, Pair<Integer, Integer> pos){
        super(w,l,0,Config.GUARANA_SPEC,pos);
    }
    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Guarana(w,l,pos);
    }
    @Override
    public void draw() {

    }
    @Override
    public void modify(Organism attacker)
    {
        attacker.setStrength(attacker.getStrength()+3);
        //ADD LOGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGS
        //logger.addLog({ attacker->getSpecies() + " got stronger beacause it ate " + species, EAT });
    }
}
