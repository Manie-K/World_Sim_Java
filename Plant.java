package World_sim;

import javafx.util.Pair;

public abstract class Plant extends Organism{
    Plant(World w, Logger l, int s, String species, Pair<Integer, Integer> pos) {
        super(w, l, s, 0, species, pos);
    }

    void sow(Pair<Integer, Integer> pos){
        world.setOrganismAtPos(pos,giveBirth(world, logger, pos));
        String mess = getSpecies() + " has been planted";
        logger.addLog(mess);
    }
    @Override
    void action()
    {
        int numberOfChances = getChances();
        for (int i = 0; i < numberOfChances; i++) {
            double chance = Math.random();
            if (chance <= Config.PLANT_SEED_CHANCE)
            {
                int noGoodTile = 0;
			    final int max = (1 << world.getDirectionCount()) - 1;
                while (noGoodTile < max)
                {
                    noGoodTile = world.simulatePlantMove(noGoodTile, max,this);
                }
            }
        }
    }
    int getChances(){return 1;}
    boolean kill(Organism attacker){return false;}
    void modify(Organism attacker){return;}
    @Override
    boolean collision(Organism attacker)
    {
        killOrganism(this);
        if (kill(attacker))
            return true;
        modify(attacker);
        world.setOrganismAtPos(attacker.getPosition(),null);
        attacker.setPosition(getPosition());
        world.setOrganismAtPos(attacker.getPosition(),attacker);
        return false;
    }
}
