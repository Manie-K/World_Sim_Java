package World_sim;

import javafx.util.Pair;

import java.util.Random;

public class Human extends Animal{
    private  InputManager manager;
    public Human(World w, Logger l,InputManager man, int s, Pair<Integer, Integer> pos){
        super(w, l, s, Config.HUMAN_INIT,Config.HUMAN_SPEC, pos);
        manager = man;
    }

    @Override
    public Organism giveBirth(World w, Logger l, Pair<Integer, Integer> pos) {
        return null; //Human doesn't breed
    }
    @Override
    public void draw() {

    }
    @Override
    public int howManyMoves()
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
    public int getDirection() //Won't move into border
    {
        //IMPLEMENT
        return 0;
    }
}
