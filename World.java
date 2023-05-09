package World_sim;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;
import java.util.Vector;

public abstract class World {
    final int worldWidth;
    final int worldHeight;
    private Vector<Organism> organisms;
    World(int w, int h){
        worldWidth=w;
        worldHeight = h;
    }
    int getWidth() {return worldWidth;}
    int getHeight() {return worldHeight;}
    Vector<Organism> getOrganisms(){return organisms;}
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
        organisms.removeIf(null);
    }
    abstract boolean handleAnimalBreeding(Pair<Integer, Integer> partnerPos, Animal caller);
    abstract Organism getOrganismAtPos(Pair<Integer, Integer> pos);
    abstract Organism getOrganismAtPos(int x, int y);
    abstract void setOrganismAtPos(Pair<Integer, Integer> pos, Organism newOrganism);
    abstract void setOrganismAtPos(int x, int y, Organism newOrganism);
    abstract int getDirectionCount();
    abstract void drawWorld();
    abstract boolean simulateAnimalMove(int direction, int badTiles,boolean dead, Animal caller);
    abstract void saveFile();

    static World loadFile(Logger logger, InputManager input)
    {
        return null;
    }
}
