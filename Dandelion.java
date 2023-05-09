package World_sim;

import javafx.util.Pair;

public class Dandelion extends Plant{
    public Dandelion(World w, Logger l,Pair<Integer, Integer> pos){
        super(w,l,0,Config.DANDELION_SPEC,pos);
    }
    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Dandelion(w,l,pos);
    }
    @Override
    public void draw() {

    }
    @Override
    public int getChances(){return 3;}
}
