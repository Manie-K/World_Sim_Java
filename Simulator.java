package World_sim;
import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;

public class Simulator {
   private World world;
   private Logger logger;
   private InputManager manager;
   JFrame window;
   private void setUpWindow(int w, int h)
   {
      final int windowWidth = 1080;
      final int windowHeight = 920;
      window = new JFrame("193302 Maciej Góralczyk");
      window.setVisible(true);
      window.setLayout(null);
      window.setSize(windowWidth,windowHeight);
      window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      JPanel mapPanel = new JPanel();
      mapPanel.setBackground(Color.BLUE);
      mapPanel.setBounds(0,0,w*Config.TILE_SIZE,h*Config.TILE_SIZE);
      window.add(mapPanel);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setBounds(windowWidth-350,0, 350, 500);
      buttonPanel.setBackground(Color.RED);
      buttonPanel.setLayout(null);


      JButton saveButton = new JButton("Save");
      JButton loadButton = new JButton("Load");
      JButton menuButton = new JButton("Show menu");

      saveButton.setVisible(true);
      loadButton.setVisible(true);
      menuButton.setVisible(true);

      saveButton.setBounds(75,50,200,100);
      loadButton.setBounds(75,200,200,100);
      menuButton.setBounds(75,350,200,100);

      saveButton.addActionListener(e->{
         save();
      });
      loadButton.addActionListener(e->{
         load();
      });

      buttonPanel.add(saveButton);
      buttonPanel.add(loadButton);
      buttonPanel.add(menuButton);
      window.add(buttonPanel);
   }
   Simulator(int w, int h)
   {
      manager = new InputManager(0,0);
      logger = new Logger(manager);
      world = new GridWorld(w, h);
      setUpWindow(w,h);
      setUpWorld();
   }
   void run()
   {
      world.drawWorld();
      logger.display();
      while (!manager.getQuit())
      {
         manager.input();
         if (manager.getArrowKey())
         {
            world.simulateTurn();
            world.drawWorld();
            manager.nextTurn();
            logger.display();
         }
         else if (manager.getLoad())
         {
            load();
         }
         else if (manager.getSave())
         {
            save();
         }
      }
   }
   private void setUpWorld()//here are the initial conditions
   {

   }
   private void save(){System.out.println("SAVE");}
   private void load(){System.out.println("LOAD");}
}
