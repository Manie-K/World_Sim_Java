package World_sim;

import javafx.util.Pair;

import java.util.Random;

abstract class Animal extends Organism {

    Animal(World w, Logger l, int s, int i, int a,String species, Pair<Integer, Integer> pos) {
        super(w, l, s, i, a, species, pos);
    }
    @Override
    void action(){
        int DIRECTION_COUNT = world.getDirectionCount();
        int moves = howManyMoves();
        float chanceToNotMove = chanceToStay();
        double stay = Math.random();
        boolean[] dead = new boolean[]{false};
	    final int max = (1 << DIRECTION_COUNT) - 1;
        if (stay < chanceToNotMove)
            return;

        for (int m = 0; m < moves; m++) {
            if (dead[0])
                return;
            boolean foundGoodTile = false;
            int[] badTiles = new int[]{0};
            while (!foundGoodTile)
            {
                int direction = getDirection();
                if (badTiles[0]>=max || direction == -1)
                    return;
                foundGoodTile = world.simulateAnimalMove(direction,badTiles,dead,this);
            }
        }
    }
    @Override
    boolean collision(Organism defender){
        String mess = "";
        boolean testIfPlant = defender instanceof Plant;

        //its plant
        if (testIfPlant) {
            boolean killed = defender.collision(this);
            mess = defender.getSpecies() + " has been eaten by " + getSpecies();
            logger.addLog(mess);
            if (killed)
            {
                mess = getSpecies() + " has been poisoned by " + defender.getSpecies();
                logger.addLog(mess);
            }
            return killed;
        }

        Animal defenderAnimal = (Animal) defender;
        //its animal
        if (getSpecies() == defender.getSpecies())
        {
            breed(defender);
            return false;
        }
        if (defenderAnimal.defenderFlee(this)) {
            mess = defender.getSpecies() + " has fleed from " + getSpecies();
            logger.addLog(mess);
            return false;
        }
        if (defenderAnimal.defenderDeflected(this)) {
            mess = defender.getSpecies() + " has succesfully deflected an attack of " + getSpecies();
            logger.addLog(mess);
            return false;
        }

        if (defender.getStrength() <= getStrength())
        {
            world.setOrganismAtPos(getPosition(),null);
            setPosition(defender.getPosition());
            killOrganism(defender);
            world.setOrganismAtPos(getPosition(),this);

            mess = getSpecies() + " has killed " + defender.getSpecies() + " in attack";
            logger.addLog(mess);
            return false;
        }
        killOrganism(this);
        mess = getSpecies() + " has been killed by " + defender.getSpecies() + " in attack";
        logger.addLog(mess);
        return true;
    }
    void breed(Organism partner){
        boolean success = world.handleAnimalBreeding(partner.getPosition(),this);
        if (success) {
            String mess = getSpecies() + " has been born";
            logger.addLog(mess);
        }
    }
    boolean defenderFlee(Animal attacker){return false;}
    boolean defenderDeflected(Animal attacker){return false;}
    boolean goodSmell(Organism defender){return false;}
    int howManyMoves(){return 1;}
    float chanceToStay(){return 0f;}
    int getDirection() {return new Random().nextInt(world.getDirectionCount());}

}
