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
    boolean simulateAnimalMove(int direction, int[] badTiles,boolean[] dead, Animal caller){
        boolean evenRow = caller.getPosition().getValue()%2==0;
        Pair<Integer,Integer> tempPos;
        if (direction == 0 && caller.getPosition().getValue() > 0 && (badTiles[0] & (1 << direction))==0)//up
        {
            tempPos = new Pair<>(caller.getPosition().getKey(), caller.getPosition().getValue() - 1 );
            if(evenRow)
                tempPos= new Pair<>(caller.getPosition().getKey()-1, caller.getPosition().getValue() - 1 );
            if(tempPos.getKey() < 0) {
                badTiles[0] |= (1 << direction);
                return false;
            }
            if (getOrganismAtPos(tempPos) == null)
            {
                if(evenRow)
                    return moveOrganism(caller,-1,-1);
                return moveOrganism(caller,0,-1);
            }
            else if (!caller.goodSmell((getOrganismAtPos(tempPos))))
            {
                dead[0] = caller.collision(getOrganismAtPos(tempPos));
                return true;
            }
            else badTiles[0]|=(1<<direction);
        }

        else if (direction == 1 && caller.getPosition().getValue() < worldHeight - 1 &&
                (badTiles[0] & (1 << direction))==0)//bottom
        {
            tempPos = new Pair<>(caller.getPosition().getKey(), caller.getPosition().getValue() + 1 );
            if(evenRow)
                tempPos= new Pair<>(caller.getPosition().getKey()-1, caller.getPosition().getValue() + 1 );
            if(tempPos.getKey() < 0) {
                badTiles[0] |= (1 << direction);
                return false;
            }
            if (getOrganismAtPos(tempPos) == null)
            {
                if(evenRow)
                    return moveOrganism(caller,-1,1);
                return moveOrganism(caller,0,1);
            }
            else if (!caller.goodSmell((getOrganismAtPos(tempPos))))
            {
                dead[0] = caller.collision(getOrganismAtPos(tempPos));
                return true;
            }
            else badTiles[0] |= (1 << direction);
        }

        else if (direction == 2 && caller.getPosition().getKey() < worldWidth - 1 &&
                (badTiles[0] & (1 << direction))==0)//right
        {
            tempPos = new Pair<>(caller.getPosition().getKey()+1, caller.getPosition().getValue() );
            if (getOrganismAtPos(tempPos) == null)
            {
                return moveOrganism(caller,1,0);
            }
            else if (!caller.goodSmell((getOrganismAtPos(tempPos))))
            {
                dead[0] = caller.collision(getOrganismAtPos(tempPos));
                return true;
            }
            else badTiles[0] |= (1 << direction);
        }

        else if (direction == 3 && caller.getPosition().getKey() > 0 && (badTiles[0] & (1 << direction))==0)//left
        {
            tempPos = new Pair<>(caller.getPosition().getKey()-1, caller.getPosition().getValue() );
            if (getOrganismAtPos(tempPos) == null)
            {
                return moveOrganism(caller,-1,0);
            }
            else if (!caller.goodSmell((getOrganismAtPos( tempPos))))
            {
                dead[0] = caller.collision(getOrganismAtPos( tempPos));
                return true;
            }
            else badTiles[0] |= (1 << direction);
        }

        else if (direction == 4 && caller.getPosition().getValue() > 0 &&
                (badTiles[0] & (1 << direction))==0)//Top right
        {
            tempPos = new Pair<>(caller.getPosition().getKey()+1, caller.getPosition().getValue()-1 );
            if(evenRow)
                tempPos= new Pair<>(caller.getPosition().getKey(), caller.getPosition().getValue() - 1 );
            if(tempPos.getKey() > worldWidth - 1) {
                badTiles[0] |= (1 << direction);
                return false;
            }
            if (getOrganismAtPos(tempPos) == null)
            {
                if(evenRow)
                    return moveOrganism(caller,0,-1);
                return moveOrganism(caller,1,-1);
            }
            else if (!caller.goodSmell((getOrganismAtPos( tempPos))))
            {
                dead[0] = caller.collision(getOrganismAtPos( tempPos));
                return true;
            }
            else badTiles[0] |= (1 << direction);
        }

        else if (direction == 5 && caller.getPosition().getValue() < worldHeight - 1
                && (badTiles[0] & (1 << direction))==0)//Bottom right
        {
            tempPos = new Pair<>(caller.getPosition().getKey()+1, caller.getPosition().getValue()+1 );
            if(evenRow)
                tempPos= new Pair<>(caller.getPosition().getKey(), caller.getPosition().getValue() + 1 );
            if(tempPos.getKey() > worldWidth - 1) {
                badTiles[0] |= (1 << direction);
                return false;
            }
            if (getOrganismAtPos(tempPos) == null)
            {
                if(evenRow)
                    return moveOrganism(caller,0,1);
                return moveOrganism(caller,1,1);
            }
            else if (!caller.goodSmell((getOrganismAtPos( tempPos))))
            {
                dead[0] = caller.collision(getOrganismAtPos( tempPos));
                return true;
            }
            else badTiles[0] |= (1 << direction);
        }

        else
        {
            badTiles[0] |= (1 << direction);
        }
        return false;
    }
    @Override
    boolean handleAnimalBreeding(Pair<Integer, Integer> partnerPos, Animal caller)
    {
        Pair<Integer, Integer> pos = partnerPos;
        for (int i = 0; i < 2; i++) {
            boolean evenRow = pos.getValue()%2==0;
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
            if (!evenRow && pos.getKey() < worldWidth- 1 &&pos.getValue() > 0&&
                    getOrganismAtPos( pos.getKey() + 1, pos.getValue()-1 ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey()+1, pos.getValue()-1));
                return true;
            }
            //top left
            if (evenRow && pos.getKey() > 0 &&pos.getValue() > 0&&
                    getOrganismAtPos( pos.getKey() - 1, pos.getValue()-1 ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey()-1, pos.getValue()-1));
                return true;
            }
            //bottom right
            if (!evenRow && pos.getKey() < worldWidth- 1 &&pos.getValue() < worldHeight - 1 &&
                    getOrganismAtPos( pos.getKey() + 1, pos.getValue()+1 ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey()+1, pos.getValue()+1));
                return true;
            }
            //bottom left
            if (evenRow && pos.getKey() > 0 &&pos.getValue() < worldHeight - 1 &&
                    getOrganismAtPos( pos.getKey() - 1, pos.getValue()+1 ) == null)
            {
                caller.giveBirth(this, caller.logger, new Pair<>(pos.getKey()-1, pos.getValue()+1));
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
        boolean evenRow = caller.getPosition().getValue()%2==0;
        Pair<Integer,Integer> tempPos;
        if (dir == 0) //TOP
        {
            tempPos =new Pair<>(caller.getPosition().getKey(),caller.getPosition().getValue()-1);
            if (caller.getPosition().getValue() > 0 && getOrganismAtPos(tempPos) == null)
            {
                caller.sow(tempPos);
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 <<dir);
        }
        else if (dir == 1) //BOTTOM
        {
            tempPos =new Pair<>(caller.getPosition().getKey(),caller.getPosition().getValue()+1);
            if (caller.getPosition().getValue() < worldHeight - 1 && getOrganismAtPos(tempPos) == null)
            {
                caller.sow(tempPos);
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (dir == 2) //RIGHT
        {
            tempPos =new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue());
            if (caller.getPosition().getKey() < worldWidth - 1 && getOrganismAtPos(tempPos) == null)
            {
                caller.sow(tempPos);
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (dir == 3) //LEFT
        {
            tempPos =new Pair<>(caller.getPosition().getKey()-1,caller.getPosition().getValue());
            if (caller.getPosition().getKey() > 0 && getOrganismAtPos(tempPos) == null)
            {
                caller.sow(tempPos);
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (!evenRow && dir == 4) //TOP RIGHT
        {
            tempPos =new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue()-1);
            if (caller.getPosition().getKey() < worldWidth - 1 && caller.getPosition().getValue() > 0 &&
                    getOrganismAtPos(tempPos) == null)
            {
                caller.sow(tempPos);
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (evenRow && dir == 4) //TOP RIGHT
        {
            tempPos =new Pair<>(caller.getPosition().getKey()-1,caller.getPosition().getValue()-1);
            if (caller.getPosition().getKey() >0 && caller.getPosition().getValue() > 0 &&
                    getOrganismAtPos(tempPos) == null)
            {
                caller.sow(tempPos);
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (!evenRow && dir == 5) //BOTTOM RIGHT
        {
            tempPos =new Pair<>(caller.getPosition().getKey()+1,caller.getPosition().getValue()+1);
            if (caller.getPosition().getKey() < worldWidth - 1 && caller.getPosition().getValue() < worldHeight - 1 &&
                    getOrganismAtPos(tempPos) == null)
            {
                caller.sow(tempPos);
                noGoodTile = x;
            }
            else
                noGoodTile = noGoodTile | (1 << dir);
        }
        else if (evenRow && dir == 5) //BOTTOM LEFT
        {
            tempPos =new Pair<>(caller.getPosition().getKey()-1,caller.getPosition().getValue()+1);
            if (caller.getPosition().getKey() > 0 && caller.getPosition().getValue() < worldHeight - 1 &&
                    getOrganismAtPos(tempPos) == null)
            {
                caller.sow(tempPos);
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
        Pair<Integer,Integer> defPosition = caller.getPosition();
        Pair<Integer,Integer> position = defPosition;
        boolean evenRow = position.getValue()%2==0;
        position = new Pair<>(position.getKey(), position.getValue()-1);
        if (position.getValue() >= 0 && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = defPosition;
        position = new Pair<>(position.getKey(), position.getValue()+1);
        if (position.getValue() <= worldHeight - 1 && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = defPosition;
        position = new Pair<>(position.getKey()+1, position.getValue());
        if (position.getKey() <= worldWidth - 1 && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = defPosition;
        position = new Pair<>(position.getKey()-1, position.getValue());
        if (position.getKey() >= 0 && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = defPosition;
        position = new Pair<>(position.getKey()+1, position.getValue()-1);
        if (!evenRow && position.getKey() <= worldWidth - 1 &&position.getValue() >= 0
                && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = defPosition;
        position = new Pair<>(position.getKey()+1, position.getValue()+1);
        if (!evenRow && position.getKey() <= worldWidth - 1 &&position.getValue() <= worldHeight - 1
                && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = defPosition;
        position = new Pair<>(position.getKey()-1, position.getValue()-1);
        if (evenRow && position.getKey() >= 0 && position.getValue() >= 0
                && getOrganismAtPos(position) instanceof Animal)
        {
            caller.logger.addLog(caller.getSpecies() + " killed " + getOrganismAtPos(position).getSpecies());
            caller.killOrganism(getOrganismAtPos(position));
        }
        position = defPosition;
        position = new Pair<>(position.getKey()-1, position.getValue()+1);
        if (evenRow && position.getKey() >= 0 && position.getValue() <= worldHeight - 1
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
        boolean evenRow = positionSecond % 2 == 0;

        if (positionSecond > 0 &&
                getOrganismAtPos(positionFirst,positionSecond - 1) == null)
        {
            moveOrganism(defender,0,-1);
        }
        else if (positionSecond < worldHeight-1 &&
                getOrganismAtPos(positionFirst,positionSecond + 1) == null)
        {
            moveOrganism(defender,0,1);
        }
        else if (positionFirst < worldWidth-1 &&
                getOrganismAtPos(positionFirst+1,positionSecond) == null)
        {
            moveOrganism(defender,1,0);
        }
        else if (positionFirst > 0 &&
                getOrganismAtPos(positionFirst - 1,positionSecond ) == null)
        {
            moveOrganism(defender,-1,0);
        }
        else if (!evenRow && positionFirst < worldWidth-1 && positionSecond > 0 &&
                getOrganismAtPos(positionFirst + 1,positionSecond-1 ) == null)
        {
            moveOrganism(defender,1,-1);
        }
        else if (!evenRow && positionFirst < worldWidth-1 && positionSecond < worldHeight-1 &&
                getOrganismAtPos(positionFirst + 1,positionSecond +1) == null)
        {
            moveOrganism(defender,1,1);
        }
        else if (evenRow && positionFirst >0 && positionSecond > 0 &&
                getOrganismAtPos(positionFirst + 1,positionSecond-1 ) == null)
        {
            moveOrganism(defender,-1,-1);
        }
        else if (evenRow && positionFirst > 0 && positionSecond < worldHeight-1 &&
                getOrganismAtPos(positionFirst + 1,positionSecond +1) == null)
        {
            moveOrganism(defender,-1,1);
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
                throw new IndexOutOfBoundsException("Getting organism outside of map!");
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
                throw new IndexOutOfBoundsException("Getting organism outside of map!");
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
                throw new IndexOutOfBoundsException("Setting organism outside of map!");
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
                throw new IndexOutOfBoundsException("Setting organism outside of map!");
            map[y][x] = newOrganism;
        }catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    @Override
    int getDirectionCount(){return 6;}
    @Override
    int getMapWidth(){return Config.TILE_SIZE/2 + Config.TILE_SIZE*worldWidth;}
    @Override
    int getMapHeight(){
        int x = Config.TILE_SIZE;
        int sum = 0;
        for(int i = 0; i < worldHeight; i++)
        {
            if(i%2==0)
                sum += x;
            else
                sum += x/3;
        }
        return sum + x/3;
    }
    @Override
    void drawWorld(JPanel mapPanel) {
        mapPanel.removeAll();
        mapPanel.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final int xSize = worldWidth;
                final int ySize = worldHeight;
                final int z = Config.TILE_SIZE;
                int xGlobal;
                int yGlobal=0;
                //odd rows
                for(int y = 0; y < ySize; y+=2)
                {
                    xGlobal = 0;
                    if(y==0)
                        yGlobal = 0;
                    for (int x = xGlobal; x < xSize; x++)
                    {
                        Color color = Config.DEFAULT_COLOR;
                        if(map[y][x]!=null)
                            color = map[y][x].draw();
                        g.setColor(color);
                        int[] xPoints = {xGlobal,xGlobal,xGlobal+(z/2),xGlobal+z,xGlobal+z,xGlobal+(z/2)};
                        int[] yPoints = {yGlobal+(z/3),yGlobal+((2*z)/3),yGlobal+z,
                                yGlobal+((2*z)/3),yGlobal+(z/3),yGlobal};
                        Polygon hexagon = new Polygon(xPoints, yPoints, 6);
                        g.fillPolygon(hexagon);
                        g.setColor(Color.BLACK);
                        g.drawPolygon(hexagon);

                        xGlobal+=z;
                    }
                    yGlobal += (4*z)/3;
                }
                //even rows
                for(int y = 1; y < ySize; y+=2)
                {
                    xGlobal = z/2;
                    if(y==1)
                        yGlobal = (2*z)/3;
                    for (int x = 0; x < xSize; x++)
                    {
                        Color color = Config.DEFAULT_COLOR;
                        if(map[y][x]!=null)
                            color = map[y][x].draw();
                        g.setColor(color);
                        int[] xPoints = {xGlobal,xGlobal,xGlobal+(z/2),xGlobal+z,xGlobal+z,xGlobal+(z/2)};
                        int[] yPoints = {yGlobal+(z/3),yGlobal+((2*z)/3),yGlobal+z,
                                yGlobal+((2*z)/3),yGlobal+(z/3),yGlobal};
                        Polygon hexagon = new Polygon(xPoints, yPoints, 6);
                        g.fillPolygon(hexagon);
                        g.setColor(Color.BLACK);
                        g.drawPolygon(hexagon);

                        xGlobal+=z;
                    }
                    yGlobal += (4*z)/3;
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
    void handleMouseClick(int xClick, int yClick, Logger logger)
    {
        final int xSize = worldWidth;
        final int ySize = worldHeight;
        final int z = Config.TILE_SIZE;
        int xGlobal;
        int yGlobal = 0;
        int xPos = -1;
        int yPos = -1;
        boolean found = false;
        Point clickPoint = new Point(xClick,yClick);
        //odd rows
        for(int y = 0; y < ySize; y+=2)
        {
            if(found)
                break;
            xGlobal = 0;
            if(y==0)
                yGlobal = 0;
            for (int x = xGlobal; x < xSize; x++)
            {
                int[] xPoints = {xGlobal,xGlobal,xGlobal+(z/2),xGlobal+z,xGlobal+z,xGlobal+(z/2)};
                int[] yPoints = {yGlobal+(z/3),yGlobal+((2*z)/3),yGlobal+z,
                        yGlobal+((2*z)/3),yGlobal+(z/3),yGlobal};
                Polygon hexagon = new Polygon(xPoints, yPoints, 6);
                if(hexagon.contains(clickPoint))
                {
                    found = true;
                    xPos = x;
                    yPos = y;
                    break;
                }
                xGlobal+=z;
            }
            yGlobal += (4*z)/3;
        }
        //even rows

        for(int y = 1; y < ySize; y+=2)
        {
            if(found)
                break;
            xGlobal = z/2;
            if(y==1)
                yGlobal = (2*z)/3;
            for (int x = 0; x < xSize; x++)
            {
                int[] xPoints = {xGlobal,xGlobal,xGlobal+(z/2),xGlobal+z,xGlobal+z,xGlobal+(z/2)};
                int[] yPoints = {yGlobal+(z/3),yGlobal+((2*z)/3),yGlobal+z,
                        yGlobal+((2*z)/3),yGlobal+(z/3),yGlobal};
                Polygon hexagon = new Polygon(xPoints, yPoints, 6);
                if(hexagon.contains(clickPoint))
                {
                    found = true;
                    xPos = x;
                    yPos = y;
                    break;
                }
                xGlobal+=z;
            }
            yGlobal += (4*z)/3;
        }

        if(found && getOrganismAtPos(xPos,yPos) == null)
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
            w = Integer.parseInt(line);
            line = reader.readLine();
            h = Integer.parseInt(line);
            line = reader.readLine();
            size = Integer.parseInt(line);

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
        boolean isEven = pos.getValue()%2==0;
        if(tempDir <0 || tempDir >= getDirectionCount())
            return -1;

        if(tempDir == 0 && (pos.getValue() <= 0||(isEven&&pos.getKey()<=0)))
            return -1;
        if(tempDir == 1 && (pos.getValue() >= worldHeight-1 ||(isEven&&pos.getKey()<=0)))
            return -1;
        if(tempDir == 2 && pos.getKey() >= worldWidth-1)
            return -1;
        if(tempDir == 3 && pos.getKey() <= 0)
            return -1;
        if(tempDir == 4 && (pos.getValue() <= 0 || (!isEven&&pos.getKey() >= worldWidth-1)))
            return -1;
        if(tempDir == 5 && (pos.getValue() >= worldHeight-1|| (!isEven&&pos.getKey() >=worldWidth-1)))
            return -1;
        return tempDir;
    }

}
