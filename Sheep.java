package World_sim;

import javafx.util.Pair;

public class Sheep extends Animal{
    public Sheep(World w, Logger l, int s, int i, String species, Pair<Integer, Integer> pos){
        super(w, l, s, i, species, pos);
    }
    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Sheep(w,l,Config.SHEEP_STRENGTH,Config.SHEEP_INIT,Config.SHEEP_SPEC,pos);
    }

    @Override
    public void draw() {

    }
}
