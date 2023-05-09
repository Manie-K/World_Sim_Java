package World_sim;
import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;

public class Simulator {
   private World world;
   private Logger logger;
   private InputManager manager;
   Simulator(int w, int h)
   {
      manager = new InputManager(0,0);
      logger = new Logger(manager);
      world = new GridWorld(w, h);
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
   private void save(){}
   private void load(){}
}
