import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.lang.*;

@SuppressWarnings("serial")
public class setup extends JPanel{
    
    public setup(){
        
    }

    @Override
    public void paint(Graphics g) {//
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
     public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("ODSY Redux Setup");
        setup set = new setup();
        frame.add(set);
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        // define items in a String array:
        String[] ratios = new String[] {"800x600p", "960x720p", "1280x960p", "1920x1080p"};

        // create a combo box with the fixed array:
        JComboBox<String> aspectRatios = new JComboBox<String>(ratios);
        frame.add(aspectRatios);
         
         
        /*while (true) {
            set.repaint();
            Thread.sleep(5);
        }*/
    }

}