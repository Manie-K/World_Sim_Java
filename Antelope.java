package World_sim;

import javafx.util.Pair;

import java.awt.*;
import java.util.Random;

public class Antelope extends Animal{
    Antelope(World w, Logger l, int s, int a,Pair<Integer, Integer> pos){
        super(w, l, s, Config.ANTELOPE_INIT,a,Config.ANTELOPE_SPEC, pos);
    }

    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return new Antelope(w,l, Config.ANTELOPE_STRENGTH, 0,pos);
    }

    @Override
    Color draw() {
        return Config.ANTELOPE_COLOR;
    }

    @Override
    int howManyMoves(){return 2;}

    @Override
    boolean defenderFlee(Animal attacker){
        int flee = new Random().nextInt() % 2;
        if (flee == 1)
        {
            boolean hasFleed = world.handleDefenderFlee(attacker,this);
            if(hasFleed) {
                logger.addLog("Antelope fleed!");
                return true;
            }
        }
        return false;
    }
}
