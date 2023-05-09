package World_sim;

import javafx.util.Pair;

public abstract class Plant extends Organism{
    Plant(World w, Logger l, int s, String species, Pair<Integer, Integer> pos) {
        super(w, l, s, 0, species, pos);
    }

    void sow(Pair<Integer, Integer> pos){

    }
    @Override
    void action()
    {

    }
    int getChances(){return 1;}
    boolean kill(Organism attacker){return false;}
    void modify(Organism attacker){return;}
    @Override
    boolean collision(Organism attacker)
    {
        return true;
    }
}
