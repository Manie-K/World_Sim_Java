package World_sim;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class GridWorld extends World{
    private Organism[][] map;
    private String worldType = Config.GRID_TYPE;
    GridWorld(int w, int h)
    {
        super(w,h);
        map = new Organism[h][w];
    }
    @Override
    boolean simulateAnimalMove(int direction, int badTiles,boolean dead, Animal caller){
        if (direction == 0 && caller.getPosition().getValue() > 0 && (badTiles & (1 << direction))==0)//up
        {
            if (getOrganismAtPos(caller.getPosition().getKey(), caller.getPosition().getValue() - 1 ) == null)
            {
                setOrganismAtPos(caller.getPosition(),null);
                caller.setPosition(caller.getPosition().getKey(), caller.getPosition().getValue()-1);
                setOrganismAtPos(caller.getPosition(),caller);
                return true;
            }
            else if (!caller.goodSmell((getOrganismAtPos(caller.getPosition().getKey(),caller.getPosition().getValue() - 1 )))) {
                dead = caller.collision(getOrganismAtPos(caller.getPosition().getKey(),caller.getPosition().getValue() - 1 ));
                return true;
            }
            else badTiles|=(1<<direction);
        }
        else if (direction == 1 && caller.getPosition().getValue() < worldHeight - 1 && (badTiles & (1 << direction))==0)//bottom
        {
            if (getOrganismAtPos(caller.getPosition().getKey(),caller.getPosition().getValue() + 1 ) == null)
            {
                setOrganismAtPos(caller.getPosition(),null);
                caller.setPosition(caller.getPosition().getKey(), caller.getPosition().getValue()+1);
                setOrganismAtPos(caller.getPosition(),caller);
                return true;
            }
            else if (!caller.goodSmell((getOrganismAtPos(caller.getPosition().getKey(),caller.getPosition().getValue() + 1)))) {
                dead = caller.collision(getOrganismAtPos(caller.getPosition().getKey(),caller.getPosition().getValue() + 1 ));
                return true;
            }
            else badTiles |= (1 << direction);
        }
        else if (direction == 2 && caller.getPosition().getKey() < worldWidth - 1 && (badTiles & (1 << direction))==0)//right
        {
            if (getOrganismAtPos(caller.getPosition().getKey() + 1,caller.getPosition().getValue() ) == null)
            {
                setOrganismAtPos(caller.getPosition(),null);
                caller.setPosition(caller.getPosition().getKey()+1, caller.getPosition().getValue());
                setOrganismAtPos(caller.getPosition(),caller);
                return true;
            }
            else if (!caller.goodSmell((getOrganismAtPos(caller.getPosition().getKey() + 1,caller.getPosition().getValue())))) {
                dead = caller.collision(getOrganismAtPos(caller.getPosition().getKey() + 1,caller.getPosition().getValue() ));
                return true;
            }
            else badTiles |= (1 << direction);
        }
        else if (direction == 3 && caller.getPosition().getKey() > 0 && (badTiles & (1 << direction))==0)//left
        {
            if (getOrganismAtPos(caller.getPosition().getKey() - 1,caller.getPosition().getValue() ) == null)
            {
                setOrganismAtPos(caller.getPosition(),null);
                caller.setPosition(caller.getPosition().getKey()-1, caller.getPosition().getValue());
                setOrganismAtPos(caller.getPosition(),caller);
                return true;
            }
            else if (!caller.goodSmell((getOrganismAtPos( caller.getPosition().getKey() - 1,caller.getPosition().getValue() )))) {
                dead = caller.collision(getOrganismAtPos( caller.getPosition().getKey() - 1,caller.getPosition().getValue() ));
                return true;
            }
            else badTiles |= (1 << direction);
        }
        else
        {
            badTiles |= (1 << direction);
        }
        return false;
    }
    @Override
    boolean handleAnimalBreeding(Pair<Integer, Integer> partnerPos, Animal caller)
    {
        Pair<Integer, Integer> pos = partnerPos;
        for (int i = 0; i < 2; i++) {
            if (pos.getValue() > 0 &&getOrganismAtPos(pos.getKey(), pos.getValue() - 1) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey(), pos.getValue() - 1 ));
                return true;
            }
            if (pos.getValue() < worldHeight - 1 && getOrganismAtPos( pos.getKey(), pos.getValue() + 1 ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey(), pos.getValue() + 1 ));
                return true;
            }
            if (pos.getKey() > 0 && getOrganismAtPos( pos.getKey() - 1, pos.getValue() ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey()-1, pos.getValue()));
                return true;
            }
            if (pos.getKey() < worldWidth- 1 && getOrganismAtPos( pos.getKey() + 1, pos.getValue() ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey()+1, pos.getValue()));
                return true;
            }
            pos = caller.getPosition(); //second loop iteration will look at tiles near this organism
        }
        return false;
    }
    @Override
    int simulatePlantMove(int noGoodTile, final int x, Plant caller)
    {
        int dir = new Random().nextInt(getDirectionCount());
        if (dir == 0) //TOP
        {
            if (caller.getPosition().getValue() > 0 && getOrganismAtPos
                    (new Pair<>(caller.getPosition().getKey(),caller.getPosition().getValue()-1)) == null)
            {
                caller.sow(new Pair<>(caller.getPosition().getKey(),caller.getPosition().getValue()-1));
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 <<dir);
        }
        else if (dir == 1) //BOTTOM
        {
            if (caller.getPosition().getValue() < worldHeight - 1 && getOrganismAtPos
                    (new Pair<>(caller.getPosition().getKey(),caller.getPosition().getValue()+1)) == null)
            {
                caller.sow(new Pair<>(caller.getPosition().getKey(),caller.getPosition().getValue()+1));
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (dir == 2) //RIGHT
        {
            if (caller.getPosition().getKey() < worldWidth - 1 && getOrganismAtPos
                    (new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue())) == null)
            {
                caller.sow(new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue()));
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (dir == 3) //LEFT
        {
            if (caller.getPosition().getKey() > 0 && getOrganismAtPos
                    (new Pair<>(caller.getPosition().getKey()-1,caller.getPosition().getValue())) == null)
            {
                caller.sow(new Pair<>(caller.getPosition().getKey()-1,caller.getPosition().getValue()));
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        return noGoodTile;
    }
    @Override
    void killNearbyAnimals(Organism caller)
    {
        Pair<Integer,Integer> position = caller.getPosition();
        position = new Pair<>(position.getKey(), position.getValue()-1);
        if (position.getValue() > 0 && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = new Pair<>(position.getKey(), position.getValue()+1);
        if (position.getValue() < worldHeight - 1 && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = new Pair<>(position.getKey()+1, position.getValue());
        if (position.getKey() < worldWidth - 1 && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = new Pair<>(position.getKey()-1, position.getValue());
        if (position.getKey() > 0 && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
    }
    @Override
    Organism getOrganismAtPos(Pair<Integer, Integer> pos) {
        return map[pos.getValue()][pos.getKey()];
    }
    @Override
    Organism getOrganismAtPos(int x, int y) {
        return map[y][x];
    }
    @Override
    void setOrganismAtPos(Pair<Integer, Integer> pos, Organism newOrganism)
    {
        map[pos.getValue()][pos.getKey()] = newOrganism;
    }
    @Override
    void setOrganismAtPos(int x, int y, Organism newOrganism)
    {
        map[y][x] = newOrganism;
    }
    @Override
    int getDirectionCount(){return 4;}
    @Override
    void drawWorld() {

    }

    @Override
    void saveFile(FileWriter writer) {
        try {
            int organismCount = getOrganisms().size();
            writer.write(worldType);
            writer.write(worldWidth);
            writer.write(worldHeight);
            writer.write(organismCount);
            for (Organism org : getOrganisms()) {
                if(org!=null)
                    org.save(writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static GridWorld load(Logger logger,BufferedReader reader, InputManager input)
    {
        try {
            int w, h, size;
            String line;
            line = reader.readLine();
            w = Integer.valueOf(line);
            line = reader.readLine();
            h = Integer.valueOf(line);
            line = reader.readLine();
            size = Integer.valueOf(line);

            GridWorld gridWorld = new GridWorld(w, h);
            Vector<Organism> organismsTemp = new Vector<>();

            for (int i = 0; i < size; i++) {
                organismsTemp.add(Organism.load(gridWorld,logger,reader,input));
            }

            Organism[][] map = new Organism[h][w];
            for (int y = 0; y < h; y++)
            {
                for (int x = 0; x < w; x++)
                {
                    map[y][x] = null;
                }
            }

            for (Organism org : organismsTemp)
            {
                if (org != null)
                    map[org.getPosition().getValue()][org.getPosition().getKey()] = org;
            }

            gridWorld.setOrganisms(organismsTemp);
            gridWorld.setMap(map);
            return gridWorld;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setMap(Organism[][] tempMap)
    {
        map = tempMap;
    }
    int getHumanDirection(Pair<Integer,Integer> pos, InputManager input)
    {
        int tempDir = input.getHumanDirection(pos);
        if(tempDir <0 || tempDir >= getDirectionCount())
            return -1;
        if(tempDir == 0 && pos.getValue() <= 0)
            return -1;
        if(tempDir == 1 && pos.getValue() >= worldHeight-1)
            return -1;
        if(tempDir == 2 && pos.getKey() >= worldWidth-1)
            return -1;
        if(tempDir == 3 && pos.getKey() <= 0)
            return -1;
        return tempDir;
    }
}
