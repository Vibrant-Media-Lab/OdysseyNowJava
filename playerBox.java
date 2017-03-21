import java.awt.*;
import java.awt.event.*;

public class playerBox{
    int player;
    
	int x = 20;
	int y = 20;
	int xa = 0;
	int ya = 0;
	private ODSYRunner game;

	public playerBox(ODSYRunner game, int playNum) {
		this.game = game;
        player = playNum;
	}

	void move() {
		x = x + 5*xa;
		y = y + 5*ya;
	}

	public void paint(Graphics2D g) {
		g.fillRect(x, y, 30, 30);
	}
    
    public void keyPressed(KeyEvent e) {
        if(player == 2){
            if (e.getKeyCode() == KeyEvent.VK_A)
                xa = -1;
            if (e.getKeyCode() == KeyEvent.VK_D)
                xa = 1;

            if (e.getKeyCode() == KeyEvent.VK_W)
                ya = -1;
            if (e.getKeyCode() == KeyEvent.VK_S)
                ya = 1;   
        }
        else if(player == 1){
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
                xa = -1;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                xa = 1;

            if (e.getKeyCode() == KeyEvent.VK_UP)
                ya = -1;
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
                ya = 1;   
        }
	}
    
    public void keyReleased(KeyEvent e) {
        
        if(player == 2){
            if (e.getKeyCode() == KeyEvent.VK_A){
                if(xa == -1)
                    xa = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_D){
                if(xa == 1)
                    xa = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_W){
                if(ya == -1)
                    ya = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_S){
                if(ya == 1)
                    ya = 0;
            }
        }
        else if(player == 1){
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
}