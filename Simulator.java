package World_sim;
import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Simulator {
   private World world;
   private Logger logger;
   private InputManager manager;
   JFrame window;
   JPanel mapPanel;
   JTextArea logsArea;
   Simulator(Pair<Integer,Integer> pos, int type)
   {
      int w = pos.getKey();
      int h = pos.getValue();
      manager = new InputManager(0,0);
      logger = new Logger(manager);
      if(type == 0)
         world = new GridWorld(w, h);
      //else if(type == 1)
        // world = new HexWorld(w,h);
      setUpWindow(w,h);
      setUpWorld();
   }
   private void setUpWindow(int w, int h)
   {
      final int windowWidth = 1080;
      final int windowHeight = 920;
      makeWindow(windowWidth,windowHeight);
      setUpMapPanel(w,h);
      setUpLoggerPanel(h,windowWidth);
      setUpButtonPanel(windowWidth);
      window.revalidate();
      window.repaint();
   }
   private void makeWindow(int w, int h)
   {
      window = new JFrame("193302 Maciej Góralczyk");
      window.setVisible(true);
      window.setLayout(null);
      window.setSize(w,h);
      window.setBackground(Color.BLACK);
      window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
   }
   private void setUpMapPanel(int w, int h)
   {
      mapPanel = new JPanel();
      mapPanel.setBackground(Color.BLUE);
      mapPanel.setBounds(0,0,w*Config.TILE_SIZE,h*Config.TILE_SIZE);
      mapPanel.setLayout(new BorderLayout());
      mapPanel.addKeyListener(new KeyAdapter() {
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
      mapPanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseReleased(MouseEvent e) {
             world.handleMouseClick(e.getX(), e.getY(), logger);
             world.drawWorld(mapPanel);
             logger.display(logsArea);
         }
      });
      window.add(mapPanel);
   }
   private void setUpButtonPanel(int windowWidth)
   {
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBounds(windowWidth-350,0, 350, 500);
      buttonPanel.setBackground(Color.LIGHT_GRAY);
      buttonPanel.setLayout(null);


      JButton saveButton = new JButton("Save");
      JButton loadButton = new JButton("Load");
      JButton menuButton = new JButton("Show menu");


      saveButton.setBounds(75,50,200,100);
      loadButton.setBounds(75,200,200,100);
      menuButton.setBounds(75,350,200,100);

      saveButton.addActionListener(e->{
         save();
      });
      loadButton.addActionListener(e->{
         load();
      });
      menuButton.addActionListener(e->{
         showMenu();
      });

      buttonPanel.add(saveButton);
      buttonPanel.add(loadButton);
      buttonPanel.add(menuButton);
      saveButton.setVisible(true);
      loadButton.setVisible(true);
      menuButton.setVisible(true);
      buttonPanel.setVisible(true);
      window.add(buttonPanel);
   }
   private void setUpLoggerPanel(int h, int windowWidth)
   {
      JPanel loggerDisplay = new JPanel();
      int y = Math.max(500,h*Config.TILE_SIZE+50);
      loggerDisplay.setBounds(0, y,windowWidth-20, 250);

      logsArea = new JTextArea(Config.LOG_MAX_MESSAGES,60);
      logsArea.setLineWrap(false);
      loggerDisplay.add(logsArea);
      window.add(loggerDisplay);
   }
   void run()
   {
      world.drawWorld(mapPanel);
      logger.display(logsArea);
      while (!manager.getQuit())
      {
         mapPanel.setFocusable(true);
         mapPanel.requestFocus();
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
      Organism o0 = new Human(world,logger,manager,Config.HUMAN_STRENGTH,0, new Pair<Integer,Integer>(12,14));
      Organism o1 = new Fox(world,logger,Config.FOX_STRENGTH,0, new Pair<Integer,Integer>(5,5));
      Organism o2 = new Sheep(world,logger,Config.SHEEP_STRENGTH,0, new Pair<Integer,Integer>(9,9));
      Organism o3 = new Wolf(world,logger,Config.WOLF_STRENGTH,0,new Pair<Integer,Integer>(1,1));
      Organism o4 = new Turtle(world,logger,Config.TURTLE_STRENGTH,0,new Pair<Integer,Integer>(3,7));
      Organism o5 = new Antelope(world,logger,Config.ANTELOPE_STRENGTH,0,new Pair<Integer,Integer>(0,0));
      Organism o6 = new Dandelion(world,logger,0,new Pair<Integer,Integer>(14,14));
      Organism o7 = new Grass(world,logger,0,new Pair<Integer,Integer>(10,10));
      Organism o8 = new Guarana(world,logger,0,new Pair<Integer,Integer>(2,6));
      Organism o9 = new WolfBerries(world,logger,0,new Pair<Integer,Integer>(9,1));
      Organism o10 = new GiantHogweed(world,logger,0,new Pair<Integer,Integer>(8,3));
   }
   private void save(){
      try {
         FileWriter writer = new FileWriter(Config.SAVE_FILE_NAME, false);
         manager.saveFile(writer);
         world.saveFile(writer);
         writer.close();
         logger.addLog("Pomyslnie zapisano do pliku");
         logger.display(logsArea);
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
         mapPanel.removeAll();
         manager = manager.loadFile(bufferedReader);
         logger = new Logger(manager);
         world = World.loadNewWorld(logger,bufferedReader, manager);
         bufferedReader.close();
         reader.close();
         logger.addLog("Pomyslnie wczytano swiat");
         logger.display(logsArea);
         world.drawWorld(mapPanel);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
   private void showMenu()
   {
      JFrame frame = new JFrame("MENU");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      JTextArea textArea = new JTextArea(Config.MENU_TEXT);
      textArea.setEditable(false);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      textArea.setPreferredSize(new Dimension(400, 600));

      frame.getContentPane().add(textArea);
      frame.pack();

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

      int x = (screenSize.width - textArea.getWidth()-100);
      int y = (screenSize.height - frame.getHeight()) / 2;

      frame.setLocation(x, y);
      frame.setVisible(true);
   }
}
