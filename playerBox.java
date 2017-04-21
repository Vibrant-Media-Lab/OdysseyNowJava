import java.awt.*;
import java.awt.event.*;

public class playerBox{
    int player;
    
    int speed;
	int x = 20;
	int y = 20;
	int xa = 0;
	int ya = 0;
    
    boolean xmp = false;
    boolean xpp = false;
    boolean ymp = false;
    boolean ypp = false;
    
    int xDest = 20;
    int yDest = 20;
    
    boolean analog;
    
    int size = 50;
    
	private ODSYRunner game;

	public playerBox(ODSYRunner game, int playNum, int xInit, int yInit, boolean an) {
		this.game = game;
        player = playNum;
        x = (xInit/4);
        if(playNum == 2)
            x = 3*x;
        y = yInit/2;
        analog = an;
	}

	public void move() {
		x = x + 5*xa;
		y = y + 5*ya;
        if(analog){
            x = xDest;
            y = yDest;
        }
	}
    
    public void setLoc(int a, int b){
        x = a;
        y = b;
    }

	public void paint(Graphics2D g) {
		g.fillRect(x, y, size, size);
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
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getSize(){
        return size;
    }
    
    public void checkCollide(){
        ball pong = game.pongBall;
        int boxCenterX = x + size/2; 
        int ballCenterX = pong.getX() + pong.getSize()/2; 
        
        if(Math.abs(boxCenterX - ballCenterX) <= (pong.getSize()/2 + size/2)){
            int boxCenterY = y + size/2;
            int ballCenterY = pong.getY() + pong.getSize()/2;
            if(Math.abs(boxCenterY - ballCenterY) <= (pong.getSize()/2 + size/2)){
                if(player == 1){
                    if(!pong.getDirection())
                        pong.setDirection(true);
                }
                else{
                    if(pong.getDirection())
                        pong.setDirection(false);
                }
                //System.out.println("Intersect with box!");
            }
        }
    }
    
    public void setDest(int a, int b){
        double ratioA = ((double)a)/((double) 1024);
        double ratioB = ((double)b)/((double) 1024);
        
        xDest = (int)(ratioA*(game.xSize));
        yDest = (int)(ratioB*(game.ySize));
    }
}