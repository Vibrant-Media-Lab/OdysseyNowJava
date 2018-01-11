import java.awt.*;
import java.awt.event.*;

public class midLine{
    
	private ODSYRunner game;
    int x;
    int width;
    int center;
    int left;
    boolean full;

	public midLine(ODSYRunner game) {
		this.game = game;
        x = game.xSize/2;
        left = game.xSize/4;
        width = game.xSize/40;
        center = x;
        full = true;
	}
    
    public void checkCollision(){
        int pongX = (game.pongBall).getX();
        int pongSize = (game.pongBall).getSize();
        
        if(x == (pongX + pongSize) || x == (pongX - pongSize)){
            int collide = game.ballLineCollision;
            if(collide != 0){
                if(collide == 1){
                    (game.pongBall).bounce();
                }
                else {
                    (game.pongBall).setVisibility(false);   
                }
            }
        }
    }
    
    public void move(int newX){
        x = newX;
    }
    
    public void setFull(boolean a){
        full = a;
    }

	public void paint(Graphics2D g) {
        if(game.linePresent){
            if(full)
                g.fillRect( x, 0, width, game.ySize);
            else
                g.fillRect( x, game.ySize/4, width, game.ySize/2); 
        }
	}
    
}