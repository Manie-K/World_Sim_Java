package World_sim;

import javafx.util.Pair;

public abstract class Organism {
    int strength,initiative,age;
    String species;
    Pair<Integer, Integer> position;
    World world;
    Logger logger;

    public abstract boolean collision(Organism other);//returns true when the attacker dies
    public abstract Organism giveBirth(World w, Logger l,Pair<Integer, Integer> pos);
    public void killOrganism(Organism victim)
    {

    }
    public Organism(World w, Logger l, int s,  int i,  String species, Pair<Integer, Integer> pos)
    {

    }
    public int getStrength() {return strength;}
    public int getInitiative() {return initiative;}
    public int getAge() {return age;}
    public String getSpecies() {return species;}
    public Pair<Integer, Integer> getPosition(){return position;}

    public void setAge(int age){this.age = age;}
    public void setStrength(int strength){this.strength = strength;}
    public void setPosition(Pair<Integer, Integer> pos){this.position = pos;}

    public abstract void action();
    public abstract void draw();
    public void save()
    {

    }
    //public static Organism load() {}
}
