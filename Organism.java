package World_sim;

import javafx.util.Pair;

public abstract class Organism {
    private int strength,initiative,age;
    private String species;
    private Pair<Integer, Integer> position;
    World world;
    Logger logger;

    public Organism(World w, Logger l, int s,  int i,  String species, Pair<Integer, Integer> pos)
    {
        try {
            if (pos.getKey() < 0 || pos.getValue() < 0 || pos.getKey() >= world.getWidth() || pos.getValue() >= world.getHeight())
                throw new IndexOutOfBoundsException("Creating organism outside of map!");
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        position = pos;
        world.getOrganisms().add(this);
        world.setOrganismAtPos(pos, this);

    }
    abstract boolean collision(Organism defender);//returns true when the attacker dies
    abstract Organism giveBirth(World w, Logger l,Pair<Integer, Integer> pos);
    void killOrganism(Organism victim)
    {
        world.setOrganismAtPos(victim.getPosition(),null) ;
        int index = world.getOrganisms().indexOf(victim);
        world.getOrganisms().set(index,null);
    }
    int getStrength() {return strength;}
    int getInitiative() {return initiative;}
    int getAge() {return age;}
    String getSpecies() {return species;}
    Pair<Integer, Integer> getPosition(){return position;}

    void setAge(int age){this.age = age;}
    void setStrength(int strength){this.strength = strength;}
    void setPosition(Pair<Integer, Integer> pos){this.position = pos;}
    void setPosition(int x, int y){this.position = new Pair<Integer,Integer>(x,y);}
    abstract void action();
    abstract void draw();
    void save()
    {

    }
    //public static Organism load() {}
}
