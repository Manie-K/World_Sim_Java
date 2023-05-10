package World_sim;

import javafx.util.Pair;

public class Turtle extends Animal{
    Turtle(World w, Logger l, int s,int a, Pair<Integer, Integer> pos){
        super(w, l, s, Config.TURTLE_INIT,a,Config.TURTLE_SPEC, pos);
    }

    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Turtle(w,l, Config.TURTLE_STRENGTH, 0,pos);
    }

    @Override
    void draw() {

    }

    @Override
    float chanceToStay(){return 0.75f;}

    @Override
    boolean defenderDeflected(Animal attacker){
        if(attacker!=null)
            return attacker.getStrength() < 5;
        return false;
    }
}
