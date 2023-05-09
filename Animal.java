package World_sim;

import javafx.util.Pair;

abstract class Animal extends Organism {

    public Animal(World w, Logger l, int s, int i, String species, Pair<Integer, Integer> pos) {
        super(w, l, s, i, species, pos);
    }
    @Override
    public void action(){

    }
    @Override
    public boolean collision(Organism other){
        return true;
    }
    public void breed(Organism partner){

    }
    public boolean defenderFlee(Animal attacker){return false;}
    public boolean defenderDeflected(Animal attacker){return false;}
    public boolean goodSmell(Animal defender){return false;}
    public int howManyMoves(){return 1;}
    public float chanceToStay(){return 0f;}
    public int getDirection() {return 0;}

}
