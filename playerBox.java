import java.awt.*;
import java.awt.event.*;

public class playerBox{
    int player;
    
	int x = 20;
	int y = 20;
	int xa = 0;
	int ya = 0;
    boolean xmp = false;
    boolean xpp = false;
    boolean ymp = false;
    boolean ypp = false;
    
	private ODSYRunner game;

	public playerBox(ODSYRunner game, int playNum, int xInit, int yInit) {
		this.game = game;
        player = playNum;
        x = (xInit/4);
        if(playNum == 2)
            x = 3*x;
        y = yInit/2;
	}

	public void move() {
		x = x + 5*xa;
		y = y + 5*ya;
	}
    
    public void setLoc(int a, int b){
        x = a;
        y = b;
    }

	public void paint(Graphics2D g) {
		g.fillRect(x, y, 30, 30);
	}
    
    public void keyPressed(int control) {
        if(control == 0){
            xa = -1;
            xmp = true;
        }
        else if(control == 1){
            xa = 1;
            xpp = true;
        }
        else if(control == 2){
            ya = -1;
            ymp = true;
        }
        else if(control == 3){
            ya = 1;
            ypp = true;
        }
	}
    
    public void keyReleased(int control) {
        if(control == 0){
            xmp = false;
            if(!xpp)
                xa = 0;
            else
                xa = 1;
        }
        else if(control == 1){
            xpp = false;
            if(!xmp)
                xa = 0;
            else
                xa = -1;
        }
        else if(control == 2){
            ymp = false;
            if(!ypp)
                ya = 0;
            else
                ya = 1;
        }
        else if(control == 3){
            ypp = false;
            if(!ymp)
                ya = 0;
            else
                ya = -1;
        }
	}
}