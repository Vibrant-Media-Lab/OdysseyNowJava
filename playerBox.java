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
        x = x + 300*(playNum - 1);
	}

	void move() {
		x = x + 5*xa;
		y = y + 5*ya;
	}

	public void paint(Graphics2D g) {
		g.fillRect(x, y, 30, 30);
	}
    
    public void keyPressed(int control) {
        if(control == 0){
            xa = -1;
        }
        else if(control == 1){
            xa = 1;
        }
        else if(control == 2){
            ya = -1;
        }
        else if(control == 3){
            ya = 1;
        }
	}
    
    public void keyReleased(int control) {
        if(control == 0){
            xa = 0;
        }
        else if(control == 1){
            xa = 0;
        }
        else if(control == 2){
            ya = 0;
        }
        else if(control == 3){
            ya = 0;
        }
	}
}