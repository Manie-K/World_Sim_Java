package World_sim;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class InputManager {
    boolean nextTurn;
    private boolean quit;
    private boolean save;
    private boolean load;
    private int direction;
    private int abilityLeft;
    private int abilityCooldown;
    InputManager(int al, int ac)
    {
        quit = save = load = nextTurn = false;
        abilityLeft = al;
        abilityCooldown = ac;
        direction = 0;
    }
    int getHumanDirection(Pair<Integer,Integer> pos)
    {
        return direction;
    }
    void reset()
    {
        direction = 0;
        quit = save = load = false;
    }
    void ability()
    {
        if (abilityCooldown == 0) {
            abilityCooldown = Config.ABILITY_COOLDOWN;
            abilityLeft = Config.ABILITY_TIME;
        }
    }

    void moveUp()
    {
        direction = 0;
    }
    void moveDown()
    {
        direction = 1;
    }
    void moveRight()
    {
        direction = 2;
    }
    void moveLeft()
    {
        direction = 3;
    }
    boolean getNextTurn()
    {
        return nextTurn;
    }
    void setNextTurn(boolean val)
    {
        nextTurn = val;
    }
    boolean getQuit() { return quit; }
    boolean getSave() { return save; }
    boolean getLoad() { return load; }
    int getAbility() { return abilityLeft; }
    int getAbilityCooldown() { return abilityCooldown; }

    void nextTurn()
    {
        if (abilityLeft > 0)
            abilityLeft--;
        if (abilityCooldown > 0)
            abilityCooldown--;
    }

    void saveFile(FileWriter writer)
    {
        try {
            writer.write(abilityLeft);
            writer.write(abilityCooldown);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    InputManager loadFile(BufferedReader reader)
    {
       try {
           String al  = reader.readLine();
           String ac  = reader.readLine();
           return new InputManager(Integer.valueOf(al),Integer.valueOf(ac));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
}
