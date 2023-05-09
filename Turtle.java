package World_sim;

import javafx.util.Pair;

public class Turtle extends Animal{
    public Turtle(World w, Logger l, int s, int i, String species, Pair<Integer, Integer> pos){
        super(w, l, s, i, species, pos);
    }

    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Turtle(w,l, Config.TURTLE_STRENGTH, Config.TURTLE_INIT,Config.TURTLE_SPEC,pos);
    }

    @Override
    public void draw() {

    }

    @Override
    public float chanceToStay(){return 0.75f;}

    @Override
    public boolean defenderDeflected(Animal attacker){return true;}
}
