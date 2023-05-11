package World_sim;

import javax.swing.*;
import java.util.Vector;

public class Logger {
    private Vector<String> logs = new Vector<String>();
    private final static int max_messages = Config.LOG_MAX_MESSAGES;
    private boolean added;
    private InputManager input;
    Logger(  InputManager in){}
    Logger(  InputManager in, Vector<String> log, boolean add){

    }
    void display(JTextArea logArea)
    {
        logArea.setText(null);
        for(String s : logs)
        {
            logArea.append(s + '\n');
        }
        logs.removeAllElements();
        logArea.repaint();
    }
    void textMenu()
    {

    }
    void addLog(final String log)
    {
        added = true;
        logs.add(0,log);
        if (logs.size() > max_messages)
            logs.removeElementAt(logs.size()-1);
    }
}
