package World_sim;

import javafx.util.Pair;

public class Fox extends Animal{

    public Fox(World w, Logger l, int s, int i, String species, Pair<Integer, Integer> pos){
        super(w, l, s, i, species, pos);
    }

    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Fox(w,l, Config.FOX_STRENGTH, Config.FOX_INIT,Config.FOX_SPEC,pos);
    }

    @Override
    public void draw() {

    }

    @Override
    public boolean goodSmell(Animal defender)
    {
        return true;
    }
}
