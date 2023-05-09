package World_sim;

import javafx.util.Pair;

public class GiantHogweed extends Plant{
    public GiantHogweed(World w, Logger l, Pair<Integer, Integer> pos){
        super(w,l,0,Config.GIANT_HOGWEED_SPEC,pos);
    }
    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new GiantHogweed(w,l,pos);
    }
    @Override
    public void draw() {

    }
    @Override
    public boolean kill(Organism attacker) {
        killOrganism(attacker);
        //logger.addLog({attacker -> getSpecies() + " died from eating " + species, KILL});
        //ADD LOGSSSSSSSSSSSSSSSSSSSSS
        return true;
    }
    @Override
    public  void action()
    {
        //ADDD
    }
}
