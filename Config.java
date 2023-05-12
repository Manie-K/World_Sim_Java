package World_sim;

import java.awt.*;

public class Config {
        public static String SAVE_FILE_NAME = "saveFile.txt";
        public static String GRID_TYPE = "GRID_TYPE";
        public static String HEX_TYPE = "HEX_TYPE";
        public static final int LOG_MAX_MESSAGES = 12;
        public static int TILE_SIZE = 20;
        public static final double PLANT_SEED_CHANCE = 0.35;

        public static final int ABILITY_TIME = 5;
        public static final int ABILITY_COOLDOWN  = 10;

        public static final int FOX_STRENGTH = 3;
        public static final int FOX_INIT = 7;
        public static final String FOX_SPEC = "Fox";
        public static final int ANTELOPE_STRENGTH = 2;
        public static final int ANTELOPE_INIT = 4;
        public static final String ANTELOPE_SPEC = "Antelope";
        public static final int WOLF_STRENGTH = 9;
        public static final int WOLF_INIT = 5;
        public static final String WOLF_SPEC = "Wolf";
        public static final int TURTLE_STRENGTH = 4;
        public static final int TURTLE_INIT = 1;
        public static final String TURTLE_SPEC = "Turtle";
        public static final int SHEEP_STRENGTH = 4;
        public static final int SHEEP_INIT = 4;
        public static final String SHEEP_SPEC = "Sheep";
        public static final int HUMAN_STRENGTH = 5;
        public static final int HUMAN_INIT = 4;
        public static final String HUMAN_SPEC = "Human";
        public static final String DANDELION_SPEC = "Dandelion";
        public static final String GRASS_SPEC = "Grass";
        public static final String GUARANA_SPEC = "Guarana";
        public static final String WOLF_BERRIES_SPEC = "WolfBerries";
        public static final String GIANT_HOGWEED_SPEC = "GiantHogweed";

        public static final Color DEFAULT_COLOR = Color.WHITE;
        public static final Color HUMAN_COLOR = Color.YELLOW;
        public static final Color ANTELOPE_COLOR = Color.MAGENTA;
        public static final Color SHEEP_COLOR = Color.GRAY;
        public static final Color FOX_COLOR = Color.ORANGE;
        public static final Color TURTLE_COLOR = Color.BLUE;
        public static final Color WOLF_COLOR = Color.RED;
        public static final Color DANDELION_COLOR = Color.CYAN;
        public static final Color GRASS_COLOR = Color.GREEN;
        public static final Color GUARANA_COLOR = Color.PINK;
        public static final Color WOLF_BERRIES_COLOR = Color.DARK_GRAY;
        public static final Color GIANT_HOGWEED_COLOR = Color.BLACK;
        public static final String MENU_TEXT = "MACIEJ GÓRALCZYK 193302 \n " +
                "-------STEROWANIE-------\n" +
                "P                      ->      Umiejętność spejcalna\n" +
                "Strzałka w góre        ->      Góra\n" +
                "Strzałka w dół         ->      Dół\n" +
                "Strzałka w prawo       ->      Prawo\n" +
                "Strzałka w lewo        ->      Lewo\n" +
                "T(HEX)                 ->      Prawa góra\n" +
                "G(HEX)                 ->      Prawy dół\n" +
                "-------INFO-------\n" +
                "Żółty          ->      Czlowiek\n" +
                "Czerwony       ->      Wilk\n" +
                "Magenta        ->      Antylopa\n" +
                "Owca           ->      Szary\n" +
                "Pomarańczowy   ->      Lis\n" +
                "Niebieski      ->      Żółw\n" +
                "Zielony        ->      Trawa\n" +
                "Jasnoniebieski ->      Mlecz\n" +
                "Różowy         ->      Guarana\n" +
                "Ciemno szary   ->      Wilcze jagody\n" +
                "Czarny         ->      Barszcz Sosnowskiego\n";
}
