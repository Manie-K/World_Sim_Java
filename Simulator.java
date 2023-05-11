package World_sim;
import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Simulator {
   private World world;
   private Logger logger;
   private InputManager manager;
   JFrame window;
   JPanel mapPanel;
   JTextArea logsArea;
   private void makeWindow(int w, int h)
   {
      window = new JFrame("193302 Maciej Góralczyk");
      window.setVisible(true);
      window.setLayout(null);
      window.setSize(w,h);
      window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      window.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
               manager.moveRight();
               manager.setNextTurn(true);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
               manager.moveLeft();
               manager.setNextTurn(true);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
               manager.moveDown();
               manager.setNextTurn(true);
            } else if (e.getKeyCode() == KeyEvent.VK_UP){
               manager.moveUp();
               manager.setNextTurn(true);
            }
            /*
            * else if (e.getKeyCode() == KeyEvent.VK_E && world.getDirectionCount==6){
               manager.moveNE();
               manager.setNextTurn(true);
            }
            * else if (e.getKeyCode() == KeyEvent.VK_Q && world.getDirectionCount==6){
               manager.moveNW();
               manager.setNextTurn(true);
            }
            * */
            else if (e.getKeyCode() == KeyEvent.VK_P){
               manager.ability();
               manager.setNextTurn(false);
            }
         }
      });
   }
   private void setUpWindow(int w, int h)
   {
      final int windowWidth = 1080;
      final int windowHeight = 920;
      makeWindow(windowWidth,windowHeight);

      mapPanel = new JPanel();
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

      JPanel loggerDisplay = new JPanel();
      loggerDisplay.setBounds(0, h*Config.TILE_SIZE+300,windowWidth-20, 100);
      logsArea = new JTextArea(Config.LOG_MAX_MESSAGES,60);
      logsArea.setLineWrap(false);
      loggerDisplay.add(logsArea);
      window.add(loggerDisplay);

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
      world.drawWorld(mapPanel);
      logger.display(logsArea);
      while (!manager.getQuit())
      {
         manager.reset();
         if (manager.getNextTurn())
         {
            world.simulateTurn();
            world.drawWorld(mapPanel);
            manager.nextTurn();
            logger.display(logsArea);
            manager.setNextTurn(false);
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
      Organism o1 = new Dandelion(world,logger,0, new Pair<Integer,Integer>(5,5));
      Organism o2 = new Human(world,logger,manager,Config.HUMAN_STRENGTH,0, new Pair<Integer,Integer>(12,15));
      Organism o3 = new Fox(world,logger,Config.FOX_STRENGTH,0,new Pair<Integer,Integer>(19,19));
   }
   private void save(){
      try {
         FileWriter writer = new FileWriter(Config.SAVE_FILE_NAME, false);
         manager.saveFile(writer);
         world.saveFile(writer);
         writer.close();
         logger.addLog("Pomyslnie zapisano do pliku");
         System.out.println(logger);
         world.drawWorld(mapPanel);
      }catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
   private void load() {
      try {
         File file = new File(Config.SAVE_FILE_NAME);
         FileReader reader = new FileReader(file);
         BufferedReader bufferedReader = new BufferedReader(reader);
         manager = manager.loadFile(bufferedReader);
         logger = new Logger(manager);
         world = World.loadNewWorld(logger,bufferedReader, manager);
         bufferedReader.close();
         reader.close();
         logger.addLog("Pomyslnie wczytano swiat");
         world.drawWorld(mapPanel);
      } catch (FileNotFoundException e) {
         throw new RuntimeException(e);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
