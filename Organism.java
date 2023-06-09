package World_sim;

import javafx.util.Pair;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Organism {
    private int strength,age;
    private final int initiative;
    private final String species;
    private Pair<Integer, Integer> position;
    World world;
    Logger logger;

    public Organism(World w, Logger l, int s,  int i, int a, String spec, Pair<Integer, Integer> pos)
    {
        world = w;
        logger = l;
        strength = s;
        initiative = i;
        age = a;
        species = spec;
        position = pos;
        try {
            if (pos.getKey() < 0 || pos.getValue() < 0 || pos.getKey() >= world.worldWidth ||
                    pos.getValue() >= world.worldHeight) {
                throw new IndexOutOfBoundsException("Creating organism outside of map!");
            }
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
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
    abstract Color draw();
    void save(FileWriter writer)
    {
        try{
        writer.write(species+'\n');
        writer.write(Integer.toString(strength)+'\n');
        writer.write(Integer.toString(age)+'\n');
        writer.write(Integer.toString(position.getKey())+'\n');
        writer.write(Integer.toString(position.getValue())+'\n');
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Organism load(World wor, Logger log, BufferedReader reader,InputManager input)
    {
        try{
            int strength, age, posF, posS;
            String spec;
            String line;
            spec = reader.readLine();
            line = reader.readLine();
            strength = Integer.parseInt(line);
            line = reader.readLine();
            age = Integer.parseInt(line);
            line = reader.readLine();
            posF = Integer.parseInt(line);
            line = reader.readLine();
            posS = Integer.parseInt(line);

            Pair<Integer,Integer> pos = new Pair<>(posF,posS);
            if (spec.equals(Config.WOLF_SPEC))
                return new Wolf(wor, log, strength,age,pos);
            if (spec.equals(Config.SHEEP_SPEC))
                return new Sheep(wor, log, strength,age,pos);
            if (spec.equals(Config.FOX_SPEC))
                return new Fox(wor, log, strength,age,pos);
            if (spec.equals(Config.TURTLE_SPEC))
                return new Turtle(wor, log, strength,age,pos);
            if (spec.equals(Config.ANTELOPE_SPEC))
                return new Antelope(wor, log, strength,age,pos);
            if (spec.equals(Config.HUMAN_SPEC))
                return new Human(wor, log,input, strength,age,pos);
            if (spec.equals(Config.GRASS_SPEC))
                return new Grass(wor, log,age, pos);
            if (spec.equals(Config.DANDELION_SPEC))
                return new Dandelion(wor, log,age, pos);
            if (spec.equals(Config.GUARANA_SPEC))
                return new Guarana(wor, log,age, pos);
            if (spec.equals(Config.WOLF_BERRIES_SPEC))
                return new WolfBerries(wor, log,age, pos);
            if (spec.equals(Config.GIANT_HOGWEED_SPEC))
                return new GiantHogweed(wor, log,age, pos);
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
