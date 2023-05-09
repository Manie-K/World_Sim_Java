package World_sim;

import javafx.util.Pair;
import java.util.Vector;

public abstract class World {
    public final int worldWidth;
    public final int worldHeight;
    private Vector<Organism> organisms;

    public World(int w, int h){
        worldWidth=w;
        worldHeight = h;
    }
    int getWidth() {return worldWidth;}
    int getHeight() {return worldHeight;}
    Vector<Organism> getOrganisms(){return organisms;}
    void simulateTurn()
    {
        organisms.sort(/*tu funckja porownujaca*/);
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
    abstract Organism getOrganismAtPos(Pair<Integer, Integer> pos);
    abstract void drawWorld();

    abstract void saveFile();
    static World loadFile(Logger logger, InputManager input)
    {
        return null;
    }
}
