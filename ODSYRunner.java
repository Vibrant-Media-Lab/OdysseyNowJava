import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ODSYRunner extends JPanel {
    
    //Window size
    static int xSize = 800;
    static int ySize = 600;
    
    //player objects
    playerBox box1 = new playerBox(this, 1);
    playerBox box2 = new playerBox(this, 2);
    
    //midline object
    midLine mid = new midLine(this);
    
    
    //constructor
    public ODSYRunner(){
        addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
                int keyA = detPlayerAction(e);
                if(keyA < 4)
                    box1.keyReleased(keyA);
				else
                    box2.keyReleased(keyA%4);
			}
            
			@Override
			public void keyPressed(KeyEvent e) {
                int keyB = detPlayerAction(e);
                if(keyB < 4)
                    box1.keyPressed(keyB);
                else
				    box2.keyPressed(keyB%4);
			}
		});
		setFocusable(true);
    }
    
    //calls movement methods of all player actors
	private void moveBox() {
		box1.move();
        box2.move();
	}
    
    private int detPlayerAction(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_A)
            return 0;
        if (e.getKeyCode() == KeyEvent.VK_D)
            return 1;
        if (e.getKeyCode() == KeyEvent.VK_W)
            return 2;
        if (e.getKeyCode() == KeyEvent.VK_S)
            return 3;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            return 4;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            return 5;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            return 6;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            return 7;
        
        return -1;
    }
    
    
    @Override
    public void paint(Graphics g) {//
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Call paint method of all actors
        box1.paint(g2d);
        box2.paint(g2d);
        mid.paint(g2d);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("ODSY Redux");
        ODSYRunner game = new ODSYRunner();
        frame.add(game);
        frame.setSize(xSize, ySize);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            //call 
            game.moveBox();
            game.repaint();
            Thread.sleep(5);
        }
    }
}