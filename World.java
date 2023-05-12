package World_sim;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public abstract class World {
    final int worldWidth;
    final int worldHeight;
    private Vector<Organism> organisms;

    World(int w, int h) {
        worldWidth = w;
        worldHeight = h;
        organisms = new Vector<Organism>();
    }

    Vector<Organism> getOrganisms() {
        return organisms;
    }

    void setOrganisms(Vector<Organism> val) {
        organisms = val;
    }

    void simulateTurn() {
        organisms.sort((Organism o1, Organism o2) -> {
            if (o1.getInitiative() == o2.getInitiative()) {
                return Integer.compare(o1.getInitiative(), o2.getInitiative());
            } else {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        });
        int fixedSize = organisms.size();
        for (int i = 0; i < fixedSize; i++) {
            Organism currentOrganism = organisms.get(i);
            if (currentOrganism != null) {
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

    abstract boolean simulateAnimalMove(int direction, int badTiles, boolean dead, Animal caller);

    abstract int simulatePlantMove(int noGoodTile, final int x, Plant caller);

    abstract void killNearbyAnimals(Organism caller);

    abstract int getHumanDirection(Pair<Integer, Integer> pos, InputManager input);

    abstract void saveFile(FileWriter writer);

    abstract void handleMouseClick(int x, int y, Logger logger);

    static World loadNewWorld(Logger logger, BufferedReader reader, InputManager input) {
        try {
            String type = reader.readLine();
            if (type.equals(Config.GRID_TYPE))
                return GridWorld.load(logger, reader, input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    Organism addNewOrganismFromClick(World wor, Logger log, int x, int y) {
        String spec = organismPicker();
        Pair<Integer, Integer> pos = new Pair<Integer, Integer>(x, y);
        if (spec.equals(Config.WOLF_SPEC))
            return new Wolf(wor, log, Config.WOLF_STRENGTH, 0, pos);
        if (spec.equals(Config.SHEEP_SPEC))
            return new Sheep(wor, log, Config.SHEEP_STRENGTH, 0, pos);
        if (spec.equals(Config.FOX_SPEC))
            return new Fox(wor, log, Config.FOX_STRENGTH, 0, pos);
        if (spec.equals(Config.TURTLE_SPEC))
            return new Turtle(wor, log, Config.TURTLE_STRENGTH, 0, pos);
        if (spec.equals(Config.ANTELOPE_SPEC))
            return new Antelope(wor, log, Config.ANTELOPE_STRENGTH, 0, pos);
        if (spec.equals(Config.GRASS_SPEC))
            return new Grass(wor, log, 0, pos);
        if (spec.equals(Config.DANDELION_SPEC))
            return new Dandelion(wor, log, 0, pos);
        if (spec.equals(Config.GUARANA_SPEC))
            return new Guarana(wor, log, 0, pos);
        if (spec.equals(Config.WOLF_BERRIES_SPEC))
            return new WolfBerries(wor, log, 0, pos);
        if (spec.equals(Config.GIANT_HOGWEED_SPEC))
            return new GiantHogweed(wor, log, 0, pos);
        return null;
    }
    private String organismPicker()
    {
        String[] options = {Config.WOLF_SPEC, Config.SHEEP_SPEC, Config.FOX_SPEC, Config.ANTELOPE_SPEC,
        Config.TURTLE_SPEC, Config.GRASS_SPEC, Config.DANDELION_SPEC, Config.GUARANA_SPEC,
        Config.WOLF_BERRIES_SPEC, Config.GIANT_HOGWEED_SPEC};

        int choice = JOptionPane.showOptionDialog(null, "Select organism to add:",
                "Organism picker", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return options[choice];
    }
}
