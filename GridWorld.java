package World_sim;

import javafx.util.Pair;

public class GridWorld extends World{
    private Organism[][] map;

    public GridWorld(int w, int h)
    {
        super(w,h);
        map = new Organism[h][w];
    }
    @Override
    Organism getOrganismAtPos(Pair<Integer, Integer> pos) {
        return map[pos.getValue()][pos.getKey()];
    }

    @Override
    void drawWorld() {

    }

    @Override
    void saveFile() {

    }
}
