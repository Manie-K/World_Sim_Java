package World_sim;

import javafx.util.Pair;

import java.util.Random;

public class Human extends Animal{
    private InputManager manager;
    Human(World w, Logger l,InputManager man, int s,int a, Pair<Integer, Integer> pos){
        super(w, l, s, Config.HUMAN_INIT,a,Config.HUMAN_SPEC, pos);
        manager = man;
    }

    @Override
    Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return null; //Human doesn't breed
    }
    @Override
    void draw() {

    }
    @Override
    int howManyMoves()
    {
        int turnsLeft = manager.getAbility();
        if(turnsLeft == 0)
            return 1;
        if (turnsLeft > 2)
            return 2;
        Random random = new Random();
        int randomNumber = random.nextInt(2) % 2;
        return randomNumber == 1 ? 2 : 1;
    }
    @Override
    int getDirection() //Won't move into border
    {
        //IMPLEMENT
        return world.getHumanDirection(getPosition(),manager);
    }
}
