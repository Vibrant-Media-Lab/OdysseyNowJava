import java.awt.*;
import java.awt.event.*;

public class playerBox{
    //player number (player 1 or player 2)
    int player; 
    
    //speed of player (used for games with slowed movement)
    int speed = 5; 
    
    //current location
	int x = 20; 
	int y = 20; 
    
    //acceleration values
	int xa = 0; 
	int ya = 0;
    
    boolean visible = true;
    
    //booleans storing values if 4 position buttons are currently held down
    boolean xmp = false; 
    boolean xpp = false;
    boolean ymp = false;
    boolean ypp = false;
    
    //current goal destination (used for games with slowed movement)
    int xDest = 20;
    int yDest = 20;
    
    //boundaries of play area (goes off-screen)
    int xMin;
    int xMax;
    int yMin;
    int yMax;
    
    //if game is using analoge controls
    boolean analog;
    
    //size of box
    int size;
    
	private ODSYRunner game;

	public playerBox(ODSYRunner game, int playNum, int xInit, int yInit, boolean an) {
		this.game = game;
        player = playNum;
        x = (xInit/4);
        if(playNum == 2)
            x = 3*x;
        y = yInit/2;
        analog = an;
        
        xMin = 0 - ((game.xSize/2)/8);
        xMax = (9/8)*game.xSize;
        yMin = 0 - ((game.ySize/2)/8) - size;
        yMax = (9/8)*game.ySize;
        
        size = game.xSize/16;
	}

	public void move() {
        if(analog){
            if(game.delayedMove){
                //I don't want to do this math yet
            }
            else{
                x = xDest;
                y = yDest;
            }
        }
        else{
            x = x + speed*xa;
            y = y + speed*ya;
            
            if(x < xMin){ x = xMin; }
            if(x > xMax){ x = xMax; }
            if(y < yMin){ y = yMin; }
            if(y > yMax){ y = yMax; }
        }
	}
    
    public void setLoc(int a, int b){
        x = a;
        y = b;
    }

	public void paint(Graphics2D g) {
        if(visible)
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
            }
        }
        
        if(player == 2 && game.PlayerPlayerCollision){
            playerBox otherPlayer = game.box1;
            int otherCenterX = otherPlayer.getX() + otherPlayer.getSize()/2;
            
            if(Math.abs(boxCenterX - otherCenterX) <= (otherPlayer.getSize()/2 + size/2)){
                int boxCenterY = y + size/2;
                int otherCenterY = otherPlayer.getY() + otherPlayer.getSize()/2;
                if(Math.abs(boxCenterY - otherCenterY) <= (otherPlayer.getSize()/2 + size/2)){
                    visible = false;
                }
            }
        }
        
    }
    
    public void setDest(int a, int b){
        double ratioA = ((double)a)/((double) 1024);
        double ratioB = ((double)b)/((double) 1024);
        
        xDest = (int)(ratioA*(game.xSize));
        yDest = (int)(ratioB*(game.ySize));
    }
    
    public void makeVisible(){
        visible = true;
    }
}