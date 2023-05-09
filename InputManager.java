package World_sim;

public class InputManager {
    private boolean quit;
    private boolean save;
    private boolean load;
    private boolean arrowKey;
    private int inputChar;
    private int abilityLeft;
    private int abilityCooldown;
    InputManager(int al, int ac)
    {
        quit = save = load = arrowKey = false;
        inputChar = 0;
        abilityLeft = al;
        abilityCooldown = ac;
    }
    void input()
    {
        arrowKey = quit = save = load = false;
       // inputChar = _getch();
        if(inputChar== Config.ARROW_MODIFIER_KEY){
            //inputChar = _getch();
            if (inputChar == Config.UP_ARROW || inputChar == Config.DOWN_ARROW ||
                    inputChar == Config.LEFT_ARROW || inputChar == Config.RIGHT_ARROW)
            {
                arrowKey = true;
            }
        }
        if (inputChar == Config.END_SIMULATION_KEY)
        {
            quit = true;
        }
        if (inputChar == Config.SAVE_KEY)
        {
            save = true;
        }
        if (inputChar == Config.LOAD_KEY)
        {
            load = true;
        }
        if (inputChar == Config.ABILITY_KEY)
        {
            if (abilityCooldown == 0) {
                abilityCooldown = Config.ABILITY_COOLDOWN;
                abilityLeft = Config.ABILITY_TIME;
            }
        }
    }

    boolean getQuit() { return quit; }
    boolean getSave() { return save; }
    boolean getLoad() { return load; }
    boolean getArrowKey() { return arrowKey; }
    int getInput(){return inputChar;}
    int getAbility() { return abilityLeft; }
    int getAbilityCooldown() { return abilityCooldown; }

    void nextTurn()
    {
        if (abilityLeft > 0)
            abilityLeft--;
        if (abilityCooldown > 0)
            abilityCooldown--;
    }

    void saveFile()
    {
    }
    InputManager loadFile()
    {
        return  new InputManager(0,0);
    }
}
