package World_sim;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        String[] options = {"HexWorld", "GridWorld"};
        int choice = JOptionPane.showOptionDialog(null, "Select a world type:", "World Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            System.out.println("hex");
            // user clicked "HexWorld"
            // create a HexWorld...
        } else {
            System.out.println("grid");
            // user clicked "GridWorld" or closed the dialog
            // create a GridWorld...
        }
        Simulator simulator = new Simulator(20,20);
        simulator.run();
    }
}
