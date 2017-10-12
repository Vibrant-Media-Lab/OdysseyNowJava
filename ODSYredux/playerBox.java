import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class playerBox{
    //player number (player 1 or player 2)
    int player; 
    
    //speed of player (used for games with slowed movement)
    int speed = 5; //NEEDS TWEAKED
    double inertia = 0.01;
    
    //current location
	int x = 20; 
	int y = 20; 
	Queue<Integer> drawxq = new ArrayDeque<Integer>();
	Queue<Integer> drawyq = new ArrayDeque<Integer>();
	int drawx = 20;
	int drawy = 20;
	boolean dropx;
	boolean dropy;
    
    //acceleration values
	int xa = 0; 
	int ya = 0;
    
    boolean present = true;
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
    
    boolean ballBounce;
    boolean ballKillPlayer;
    
	private ODSYRunner game;
	
	//how far the paint function will draw away each time
	int steppingValue = 5;

	public playerBox(ODSYRunner game, int playNum, int xInit, int yInit, boolean an) {
		this.game = game;
        player = playNum;
        x = (xInit/4);
        if(playNum == 2)
            x = 3*x;
        y = yInit/2;
        analog = an;
        
        xDest = x;
        yDest = y;
        
        ballBounce = true;
        ballKillPlayer = false;
        
        size = (game.xSize/18);
        
        xMin = 0 - ((game.xSize/2)/8);
        xMax = (9/8)*game.xSize;
        yMin = 0 - ((game.ySize/2)/8) - 2*size;
        yMax = (9/8)*game.ySize;
		
		drawxq.add(20);
		drawyq.add(20);
        
	}

	public void move() {
        if(analog){
            if(game.inertia && player == 2){
                //I don't want to do this math yet
                x = x + (int)((inertia*(xDest-x)));
                y = y + (int)((inertia*(yDest-y)));
            }
            else{
                x = xDest;
				drawxq.add(x);
                y = yDest;
				drawyq.add(y);
            }
        }
        else{
            xDest = xDest + speed*xa;
            yDest = yDest + speed*ya;
            if(game.inertia && player == 2){
                x = x + (int)((inertia*(xDest-x)));
                y = y + (int)((inertia*(yDest-y)));
            }
            else{
                x = xDest;
                y = yDest;
            }
            
            if(x < xMin){ x = xMin; }
            if(x > xMax){ x = xMax; }
            if(y < yMin){ y = yMin; }
            if(y > yMax){ y = yMax; }
            
            if(xDest < xMin){ xDest = xMin; }
            if(xDest > xMax){ xDest = xMax; }
            if(yDest < yMin){ yDest = yMin; }
            if(yDest > yMax){ yDest = yMax; }
        }
	}
    
    public void setLoc(int a, int b){
        x = a;
        y = b;
    }

	public void paint(Graphics2D g) {
        if(visible && present)
		{
			if(drawxq.isEmpty() && drawyq.isEmpty())	 
				g.fillRect( (int)(x), (int)(y), (int)(size), (int)(size));
			else if(drawxq.peek() == drawx && drawyq.peek() == drawy)
			{
				g.fillRect( (int) (drawxq.remove()), (int) (drawyq.remove()), (int) (size), (int) (size));
			}
		  	else 
			{
				if(drawx < drawxq.peek())
				{
					drawx = drawx + steppingValue;
					if(drawx >= drawxq.peek())
					{
						drawx = drawxq.peek();
						dropx = true;
					}
				}
				if(drawx > drawxq.peek())
				{
					drawx = drawx - steppingValue;
					if(drawx <= drawxq.peek())
					{
						drawx = drawxq.peek();
						dropx = true;
					}
				}
				if(drawy < drawyq.peek())
				{
					drawy = drawy + steppingValue;
					if(drawy > drawyq.peek())
					{
						drawy = drawyq.peek();
						dropy = true;
					}
				}
				if(drawy > drawyq.peek())
				{
					drawy = drawy - steppingValue;
					if(drawy < drawyq.peek())
					{
						drawy = drawyq.peek();
						dropy = true;
					}
				}
				g.fillRect( (int)(drawx), (int)(drawy), (int)(size), (int)(size));
				if(dropy)
				{
					drawyq.remove();
					dropy = false;
				}
				if(dropx)
				{
					drawxq.remove();
					dropx = false;
				}
			}
		}
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
    
    public boolean onScreen(){
        if(x > 800 || y > 600){
            return false;
        }
        if(x < (0 - size) || y < (0 - size) ){
            return false;
        }
        
        return true;
    }
    
    public void checkCollide(){
        if(onScreen()){
            ball pong = game.pongBall;
            int boxCenterX = x + size/2; 
            int ballCenterX = pong.getX() + pong.getSize()/2; 

            if(pong.onScreen()){
                if(Math.abs(boxCenterX - ballCenterX) <= (pong.getSize()/2 + size/2)){
                    int boxCenterY = y + size/2;
                    int ballCenterY = pong.getY() + pong.getSize()/2;
                    if(Math.abs(boxCenterY - ballCenterY) <= (pong.getSize()/2 + size/2)){ 
                            if(ballBounce){
                                if(player == 1){
                                    if(!pong.getDirection())
                                        pong.setDirection(true);
                                }
                                else{
                                    if(pong.getDirection())
                                        pong.setDirection(false);
                                }
                            }
                            else if(ballKillPlayer){
                                visible = false;
                            }
                            else{
                                game.pongBall.setVisibility(false);
                            }
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
    }
    
    public void setDest(int a, int b){
        double ratioA = ((double)a)/((double) 1024);
        double ratioB = ((double)b)/((double) 1024);
        
        xDest = (int)(ratioA*(800));
        yDest = (int)(ratioB*(600));
    }
    
    public void makeVisible(){
        visible = true;
    }
    
    public void setPresence(boolean a){
        present = a;
        makeVisible();
    }
    
    public void setBallCollide(int a){
        if(a == 0){
            ballBounce = true;
            ballKillPlayer = false;
        } 
        else if (a == 1){
            ballBounce = false;
            ballKillPlayer = true;
        } 
        else{
            ballBounce = false;
            ballKillPlayer = false;
        }
    }
}