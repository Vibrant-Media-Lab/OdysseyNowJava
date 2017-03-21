import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ODSYRunner extends JPanel {
    
    int leftX = 0;
    int leftY = 0;
    int rightX = 0;
    int rightY = 0;
    
    playerBox box1 = new playerBox(this);
    
    public ODSYRunner(){
        addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				box1.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				box1.keyPressed(e);
			}
		});
		setFocusable(true);
    }
    

	private void moveBox() {
		box1.move();
	}
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        box1.paint(g2d);
        
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("ODSY Redux");
        ODSYRunner game = new ODSYRunner();
        frame.add(game);
        frame.setSize(300, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            game.moveBox();
            game.repaint();
            Thread.sleep(10);
        }
    }
}