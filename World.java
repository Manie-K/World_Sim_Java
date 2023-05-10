package World_sim;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public abstract class World {
    final int worldWidth;
    final int worldHeight;
    private Vector<Organism> organisms;
    World(int w, int h){
        worldWidth=w;
        worldHeight = h;
        organisms =  new Vector<Organism>();
    }
    Vector<Organism> getOrganisms(){return organisms;}
    void setOrganisms( Vector<Organism> val){organisms = val;}
    void simulateTurn()
    {
        organisms.sort((Organism o1, Organism o2) -> {
            if(o1.getInitiative() == o2.getInitiative())
            {
                return Integer.compare(o1.getInitiative(), o2.getInitiative());
            }else {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        });
        int fixedSize = organisms.size();
        for (int i = 0; i < fixedSize; i++)
        {
            Organism currentOrganism = organisms.get(i);
            if (currentOrganism != null)
            {
                currentOrganism.setAge(currentOrganism.getAge() + 1);
                currentOrganism.action();
            }
        }
        organisms.removeIf(Objects::isNull);
    }
    abstract Organism getOrganismAtPos(Pair<Integer, Integer> pos);
    abstract Organism getOrganismAtPos(int x, int y);
    abstract void setOrganismAtPos(Pair<Integer, Integer> pos, Organism newOrganism);
    abstract void setOrganismAtPos(int x, int y, Organism newOrganism);
    abstract int getDirectionCount();
    abstract void drawWorld(JPanel mapPanel);
    abstract boolean handleAnimalBreeding(Pair<Integer, Integer> partnerPos, Animal caller);
    abstract boolean simulateAnimalMove(int direction, int badTiles,boolean dead, Animal caller);
    abstract int simulatePlantMove(int noGoodTile, final int x, Plant caller);
    abstract void killNearbyAnimals(Organism caller);
    abstract void saveFile(FileWriter writer);
    abstract int getHumanDirection(Pair<Integer,Integer> pos, InputManager input);
    public static World loadNewWorld(Logger logger,BufferedReader reader, InputManager input) {
        try {
            String type = reader.readLine();
            if(type == Config.GRID_TYPE)
                return GridWorld.load(logger,reader,input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
