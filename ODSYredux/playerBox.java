import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class playerBox{
    //player number (player 1 or player 2)
    int player; 
    
    //speed of player (used for games with slowed movement)
    int speed = 5; //NEEDS TWEAKED
    double inertia = 0.01; //used for non-analog.  problematic
    
    //current location
	int x = 20; 
	int y = 20; 
	
	//variables related to the drawing of the players location
	//rather than teleport to each new location, the program will gradually draw each location
	
	//the list of locations the player has visited
	Queue<Integer> drawxq = new ArrayDeque<Integer>();
	Queue<Integer> drawyq = new ArrayDeque<Integer>();
	//what to currently draw
	int drawx = 20;
	int drawy = 20;
	//the previous one drawn
	//--used to determine what to draw next -- did we reach our destination?
	int prevx;
	int prevy;
	//should we drop the current value in the queue?
	boolean dropx;
	boolean dropy;
	//how many frames to draw between each input
	int frameRate = 12;
	int inertiaFrameRate = 63;
    
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
	int prevXDest = 20;
	int prevYDest = 20;
    
    //boundaries of play area (goes off-screen)
    int xMin;
    int xMax;
    int yMin;
    int yMax;
	
	//current inertia implementation slows the drawing process.
	int inertiaCount = 1;
	//will only draw every time inertiaCount reaches inertiaDelay
	int inertiaDelay = 70;  //tweak.  the higher this number, the longer it takes the block to reach its destination
    
    //if game is using analoge controls
    boolean analog;
    
    //size of box
    int size;
    
    boolean ballBounce;
    boolean ballKillPlayer;
    
	private ODSYRunner game;
	
	//how far the paint function will draw away each time
	int steppingValueX = 5;
	int steppingValueY = 5;

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
	
	public void setAnalog(boolean a)
	{
		analog = a;
	}

	public void move() {
        if(analog){
            /*if(game.inertia && player == 2){
                //I don't want to do this math yet
				
				//drawxq.add(x + (int)((inertia*(xDest-x))));
				//drawyq.add(y + (int)((inertia*(yDest-y))));
              //  x = x + (int)((inertia*(xDest-x)));
              //  y = y + (int)((inertia*(yDest-y)));
                x = xDest;
				drawxq.add(x);
                y = yDest;
				drawyq.add(y);
            }*/
           // else{
			   if(drawxq.peek() != xDest && drawyq.peek() != yDest){
				drawxq.add(xDest);
				drawyq.add(yDest);
			   }
            //}
        }
        else{
            xDest = xDest + speed*xa;
            yDest = yDest + speed*ya;
           /* if(game.inertia && player == 2){  //this inertia approach does not allow the player to ever reach the edge
                x = x + (int)((inertia*(xDest-x)));
                y = y + (int)((inertia*(yDest-y)));
            }*/
            //else{
            //}
            
           /* if(x < xMin){ x = xMin; }
            if(x > xMax){ x = xMax; }
            if(y < yMin){ y = yMin; }
            if(y > yMax){ y = yMax; }*/
            
            if(xDest < xMin){ xDest = xMin; }
            if(xDest > xMax){ xDest = xMax; }
            if(yDest < yMin){ yDest = yMin; }
            if(yDest > yMax){ yDest = yMax; }
        }
	}
    
    public void setLoc(int a, int b){
        x = a;
		drawxq.clear();
        y = b;
		drawyq.clear();
    }
	
	private int getFrameRate(double dist)
	{
		if( dist > 800 )
			return 30;
		if( dist > 700 )
			return 25;
		if( dist > 600 )
			return 21;
		if( dist > 500 )
			return 17;
		if( dist > 400 )
			return 15;
		if( dist > 300 )
			return 13;
		if( dist > 200 )
			return 10;
		if( dist > 100 )
			return 7;
		if( dist > 70 )
			return 5; 
		if( dist > 50 )
			return 4;
		if( dist > 30 )
			return 3;
		if( dist > 20 )
			return 2; 
		return 1;
	}

	public void paint(Graphics2D g) {
        if(visible && present)
		{
			
			if(!game.inertia || player == 1 || inertiaCount % inertiaDelay == 0){
				if(xDest - prevXDest > 3 || xDest - prevXDest < -3 || yDest - prevYDest > 3 || yDest - prevYDest < -3)
				{
					System.out.println(drawx);
					System.out.println(xDest);
					//pythagoras' theorem
					//double xlength = Math.abs( (double) xDest - (double) x );
					//double ylength = Math.abs( (double) yDest - (double)y );
					double xlength =  (double) xDest - (double) x ;
					double ylength =  (double) yDest - (double) y ;
					System.out.println(xlength);
					double dist = Math.sqrt( xlength*xlength + ylength*ylength ) ;
					
					//System.out.println(dist);
					double distEachStep = dist/getFrameRate(dist);
					steppingValueX = (int) (xlength*distEachStep/dist); //dist/distEachStep = xlength/svx
					steppingValueY = (int) (ylength*distEachStep/dist);
					
					System.out.println(steppingValueX);
					
					System.out.println();
				}
				
				drawy = drawy + steppingValueY;
				drawx = drawx + steppingValueX;
				if(steppingValueY > 0 && drawy > yDest ) drawy = yDest;
				if(steppingValueX > 0 && drawx > xDest) drawx = xDest;
				if(steppingValueY < 0 && drawy < yDest ) drawy = yDest;
				if(steppingValueX <	0 && drawx < xDest) drawx = xDest;
				
				
				g.fillRect( (int)(drawx), (int)(drawy), (int)(size), (int)(size));
				x = drawx;
				y = drawy;
				
				prevXDest = xDest;
				prevYDest = yDest;
			}
			else
			{
				g.fillRect( (int)(drawx), (int)(drawy), (int)(size), (int)(size));

			}
			inertiaCount++;
			if(inertiaCount > inertiaDelay) inertiaCount = 0;
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