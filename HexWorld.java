package World_sim;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class HexWorld extends World{
    private Organism[][] map;
    private final String worldType = Config.HEX_TYPE;
    HexWorld(int w, int h)
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
            else if (!caller.goodSmell((getOrganismAtPos(caller.getPosition().getKey(),
                    caller.getPosition().getValue() - 1 ))))
            {
                dead = caller.collision(getOrganismAtPos(caller.getPosition().getKey(),
                        caller.getPosition().getValue() - 1 ));
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
            else if (!caller.goodSmell((getOrganismAtPos(caller.getPosition().getKey(),
                    caller.getPosition().getValue() + 1))))
            {
                dead = caller.collision(getOrganismAtPos(caller.getPosition().getKey(),
                        caller.getPosition().getValue() + 1 ));
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
            else if (!caller.goodSmell((getOrganismAtPos(caller.getPosition().getKey() + 1,
                    caller.getPosition().getValue()))))
            {
                dead = caller.collision(getOrganismAtPos(caller.getPosition().getKey() + 1,
                        caller.getPosition().getValue() ));
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
            else if (!caller.goodSmell((getOrganismAtPos( caller.getPosition().getKey() - 1,
                    caller.getPosition().getValue() ))))
            {
                dead = caller.collision(getOrganismAtPos( caller.getPosition().getKey() - 1,
                        caller.getPosition().getValue() ));
                return true;
            }
            else badTiles |= (1 << direction);
        }

        else if (direction == 4 && caller.getPosition().getKey() < worldWidth - 1 &&
                caller.getPosition().getValue() > 0 && (badTiles & (1 << direction))==0)//Top right
        {
            if (getOrganismAtPos(caller.getPosition().getKey() + 1,caller.getPosition().getValue() -1) == null)
            {
                setOrganismAtPos(caller.getPosition(),null);
                caller.setPosition(caller.getPosition().getKey()+1, caller.getPosition().getValue()-1);
                setOrganismAtPos(caller.getPosition(),caller);
                return true;
            }
            else if (!caller.goodSmell((getOrganismAtPos( caller.getPosition().getKey() + 1,
                    caller.getPosition().getValue() -1))))
            {
                dead = caller.collision(getOrganismAtPos( caller.getPosition().getKey() + 1,
                        caller.getPosition().getValue() -1));
                return true;
            }
            else badTiles |= (1 << direction);
        }

        else if (direction == 5 && caller.getPosition().getKey() < worldWidth - 1 &&
                caller.getPosition().getValue() < worldHeight - 1&& (badTiles & (1 << direction))==0)//Bottom right
        {
            if (getOrganismAtPos(caller.getPosition().getKey() + 1,caller.getPosition().getValue() +1) == null)
            {
                setOrganismAtPos(caller.getPosition(),null);
                caller.setPosition(caller.getPosition().getKey()+1, caller.getPosition().getValue()-1);
                setOrganismAtPos(caller.getPosition(),caller);
                return true;
            }
            else if (!caller.goodSmell((getOrganismAtPos( caller.getPosition().getKey() + 1,
                    caller.getPosition().getValue() + 1))))
            {
                dead = caller.collision(getOrganismAtPos( caller.getPosition().getKey() + 1,
                        caller.getPosition().getValue() + 1));
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
            //top right
            if (pos.getKey() < worldWidth- 1 &&pos.getValue() > 0&&
                    getOrganismAtPos( pos.getKey() + 1, pos.getValue()-1 ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey()+1, pos.getValue()-1));
                return true;
            }
            //bottom right
            if (pos.getKey() < worldWidth- 1 &&pos.getValue() < worldHeight - 1 &&
                    getOrganismAtPos( pos.getKey() + 1, pos.getValue()+1 ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey()+1, pos.getValue()+1));
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
        else if (dir == 4) //TOP RIGHT
        {
            if (caller.getPosition().getKey() < worldWidth - 1 && caller.getPosition().getValue() > 0 &&
                    getOrganismAtPos(new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue()-1)) == null)
            {
                caller.sow(new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue()-1));
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (dir == 5) //BOTTOM RIGHT
        {
            if (caller.getPosition().getKey() < worldWidth - 1 && caller.getPosition().getValue() < worldHeight - 1 &&
                    getOrganismAtPos(new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue()+1)) == null)
            {
                caller.sow(new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue()+1));
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

        position = new Pair<>(position.getKey()+1, position.getValue()-1);
        if (position.getKey() < worldWidth - 1 &&position.getValue() > 0
                && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }

        position = new Pair<>(position.getKey()+1, position.getValue()+1);
        if (position.getKey() < worldWidth - 1 &&position.getValue() < worldHeight - 1
                && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
    }
    @Override
    boolean handleDefenderFlee(Organism attacker, Organism defender){
        Pair<Integer,Integer> tempPos = defender.getPosition();
        int positionFirst = tempPos.getKey();
        int positionSecond = tempPos.getValue();
        if (positionSecond > 0 &&
                getOrganismAtPos(positionFirst,positionSecond - 1) == null)
        {
            setOrganismAtPos(tempPos,null);
            defender.setPosition(positionFirst,positionSecond-1);
            setOrganismAtPos(defender.getPosition(),defender);
        }
        else if (positionSecond < worldHeight-1 &&
                getOrganismAtPos(positionFirst,positionSecond + 1) == null)
        {
            setOrganismAtPos(tempPos,null);
            defender.setPosition(positionFirst,positionSecond+1);
            setOrganismAtPos(defender.getPosition(),defender);
        }
        else if (positionFirst < worldWidth-1 &&
                getOrganismAtPos(positionFirst+1,positionSecond) == null)
        {
            setOrganismAtPos(tempPos,null);
            defender.setPosition(positionFirst+1,positionSecond);
            setOrganismAtPos(defender.getPosition(),defender);
        }
        else if (positionFirst > 0 &&
                getOrganismAtPos(positionFirst - 1,positionSecond ) == null)
        {
            setOrganismAtPos(tempPos,null);
            defender.setPosition(positionFirst-1,positionSecond);
            setOrganismAtPos(defender.getPosition(),defender);
        }
        else if (positionFirst < worldWidth-1 && positionSecond > 0 &&
                getOrganismAtPos(positionFirst + 1,positionSecond-1 ) == null)
        {
            setOrganismAtPos(tempPos,null);
            defender.setPosition(positionFirst+1,positionSecond-1);
            setOrganismAtPos(defender.getPosition(),defender);
        }
        else if (positionFirst < worldWidth-1 && positionSecond < worldHeight-1 &&
                getOrganismAtPos(positionFirst + 1,positionSecond +1) == null)
        {
            setOrganismAtPos(tempPos,null);
            defender.setPosition(positionFirst+1,positionSecond+1);
            setOrganismAtPos(defender.getPosition(),defender);
        }
        else
            return false; //no empty tile

        setOrganismAtPos(attacker.getPosition(),null);
        attacker.setPosition(tempPos);
        setOrganismAtPos(attacker.getPosition(),attacker);
        return true;
    }
    @Override
    Organism getOrganismAtPos(Pair<Integer, Integer> pos) {
        try {
            if(pos.getKey()<0 || pos.getValue()<0 || pos.getValue() >= worldHeight || pos.getKey() >=worldWidth)
                throw new IndexOutOfBoundsException("Creating organism outside of map!");
            return map[pos.getValue()][pos.getKey()];
        }catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
    @Override
    Organism getOrganismAtPos(int x, int y) {
        try {
            if(x<0 || y<0 || y >= worldHeight || x >=worldWidth)
                throw new IndexOutOfBoundsException("Creating organism outside of map!");
            return map[y][x];
        }catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
    @Override
    void setOrganismAtPos(Pair<Integer, Integer> pos, Organism newOrganism)
    {
        try {
            if(pos.getKey()<0 || pos.getValue()<0 || pos.getValue() >= worldHeight || pos.getKey() >=worldWidth)
                throw new IndexOutOfBoundsException("Creating organism outside of map!");
            map[pos.getValue()][pos.getKey()] = newOrganism;
        }catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    @Override
    void setOrganismAtPos(int x, int y, Organism newOrganism)
    {
        try {
            if(x<0 || y<0 || y >= worldHeight || x >=worldWidth)
                throw new IndexOutOfBoundsException("Creating organism outside of map!");
            map[y][x] = newOrganism;
        }catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    @Override
    int getDirectionCount(){return 6;}
    @Override
    void drawWorld(JPanel mapPanel) {
        mapPanel.removeAll();
        mapPanel.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int y = 0; y < worldHeight; y++)
                {
                    for (int x = 0; x < worldWidth; x++)
                    {
                        Color color = Config.DEFAULT_COLOR;
                        if(map[y][x]!=null)
                            color = map[y][x].draw();
                        g.setColor(color);
                        g.fillRect(x*Config.TILE_SIZE, y*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect(x*Config.TILE_SIZE, y*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(mapPanel.getSize());
            }
        },BorderLayout.CENTER);
        mapPanel.revalidate();
        mapPanel.repaint();
    }

    @Override
    void handleMouseClick(int x, int y, Logger logger)
    {
        int xPos = x / Config.TILE_SIZE;
        int yPos = y / Config.TILE_SIZE;
        if(getOrganismAtPos(xPos,yPos) == null)
        {
            Organism addedOrganism = addNewOrganismFromClick(this, logger, xPos, yPos);
            logger.addLog("Added: " + addedOrganism.getSpecies());
        }
    }
    @Override
    void saveFile(FileWriter writer) {
        try {
            int organismCount = getOrganisms().size();
            writer.write(worldType+'\n');
            writer.write(Integer.toString(worldWidth)+'\n');
            writer.write(Integer.toString(worldHeight)+'\n');
            writer.write(Integer.toString(organismCount)+'\n');
            for (Organism org : getOrganisms()) {
                if(org!=null)
                    org.save(writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static HexWorld load(Logger logger, BufferedReader reader, InputManager input)
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

            HexWorld hexWorld = new HexWorld(w, h);
            Vector<Organism> organismsTemp = new Vector<>();

            for (int i = 0; i < size; i++) {
                organismsTemp.add(Organism.load(hexWorld,logger,reader,input));
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

            hexWorld.setOrganisms(organismsTemp);
            hexWorld.setMap(map);
            return hexWorld;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setMap(Organism[][] tempMap)
    {
        map = tempMap;
    }
    @Override
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
        if(tempDir == 4 && (pos.getValue() <= 0 || pos.getKey() >=worldWidth-1))
            return -1;
        if(tempDir == 5 && (pos.getValue() >= worldHeight-1|| pos.getKey() >=worldWidth-1))
            return -1;
        return tempDir;
    }

}
