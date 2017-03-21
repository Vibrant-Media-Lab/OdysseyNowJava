import java.awt.*;
import java.awt.event.*;

public class playerBox{
	int x = 0;
	int y = 0;
	int xa = 1;
	int ya = 0;
	private ODSYRunner game;

	public playerBox(ODSYRunner game) {
		this.game = game;
	}

	void move() {
		if (x + xa < 0)
			xa = 1;
		if (x + xa > game.getWidth() - 30)
			xa = -1;
		if (y + ya < 0)
			ya = 1;
		if (y + ya > game.getHeight() - 30)
			ya = -1;

		x = x + xa;
		y = y + ya;
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