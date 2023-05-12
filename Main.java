package World_sim;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args)
    {
        int type=0;
        type = gatherWorldTileType();
        Pair<Integer,Integer> pos = gatherWorldSize();
        if(type == -1 || pos.getValue() == -1)
            return;
        Simulator simulator = new Simulator(pos,type);
        simulator.run();
    }
    private static Pair<Integer,Integer> gatherWorldSize()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Enter X size:"));
        JTextField xField = new JTextField();
        panel.add(xField);
        panel.add(new JLabel("Enter Y size:"));
        JTextField yField = new JTextField();
        panel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Custom Size",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return new Pair<>(Integer.parseInt(xField.getText()),Integer.parseInt(yField.getText()));
        } else {
            return new Pair<>(-1,-1);
        }
    }
    private static int gatherWorldTileType()
    {
        String[] options = {"GridWorld","HexWorld"};
        int choice = JOptionPane.showOptionDialog(null, "Select a world type:", "World Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(choice == 0 || choice == 1)
            return choice;
        else return -1;
    }
}
