import java.awt.*;
import java.awt.event.*;

public class playerBox{
	int x = 20;
	int y = 20;
	int xa = 0;
	int ya = 0;
	private ODSYRunner game;

	public playerBox(ODSYRunner game) {
		this.game = game;
	}

	void move() {
		x = x + 5*xa;
		y = y + 5*ya;
	}

	public void paint(Graphics2D g) {
		g.fillRect(x, y, 30, 30);
	}
    
    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			xa = -1;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			xa = 1;
        
        if (e.getKeyCode() == KeyEvent.VK_UP)
			ya = -1;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			ya = 1;
	}
    
    public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if(xa == -1)
                xa = 0;
        }
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(xa == 1)
                xa = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP){
			if(ya == -1)
                ya = 0;
        }
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if(ya == 1)
                ya = 0;
        }
        
	}
}