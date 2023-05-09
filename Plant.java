package World_sim;

import javafx.util.Pair;

public abstract class Plant extends Organism{
    public Plant(World w, Logger l, int s, String species, Pair<Integer, Integer> pos) {
        super(w, l, s, 0, species, pos);
    }

    public void sow(Pair<Integer, Integer> pos){

    }
    @Override
    public void action()
    {

    }
    public int getChances(){return 1;}
    public boolean kill(Organism attacker){return false;}
    public void modify(Organism attacker){return;}
    @Override
    public boolean collision(Organism attacker)
    {
        return true;
    }
}
