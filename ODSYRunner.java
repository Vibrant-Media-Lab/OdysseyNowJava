import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.lang.*;

@SuppressWarnings("serial")
public class ODSYRunner extends JPanel {
    
    //SYSTEM CONFIG
    boolean PlayerSpotLeft = true; //: [yes or no] (does it appear or not)
    boolean PlayerSpotRight = true; // [yes or no]
    boolean ballInit = false; // [yes or no]
    boolean ballLarge = false; // Type: [normal, large]
    boolean linePresent = true; // [yes or no]
    int BallPlayerCollisionType = 0; // [bounce, passthrough, extinguish]
    boolean PlayerPlayerCollision = false; // [passthrough, extinguish] (note: only the Right Player Spot is extinguished)
    boolean BallLineCollision = false; // [passthrough, bounce]
    int LinePosition = 400;// [center, left, #, moving] (#= numerical position on screen from left to right, maybe 256 steps) (moving= starts on left of screen and slowly moves toward the right until a Reset button is hit)
    boolean lineMoving = false;
    int LineHeight = 10; // [full, #] [#= numerical value, 10 steps]
    int PlayerSpotSpeed = 0; //: [#] (10 increments; determines the rate of travel as a player spot moves to the position indicated by the controller input)
    int Inertia = 0; //: [#] (indicates the rate of velocity decay, or the momentum that a player spot has beyond actual user control; this is complex; see actual Cards 6 and 12 for behavior to emulate; a value of 0 should indicate no inertia)
    boolean LeftReset = false; //: [defines behavior of the Left Controller's Reset button: usually serves the ball from left to right]
    boolean RightReset = false; //: [same options, except also: visible (makes Right Player Spot visible if it is in an extinguished state)]
    boolean Accessory = false; //: [yes or no]
    boolean AccessoryHitExtinguish = false; //: [ball, Player Dot Left, Player Dot Right] (behavior when gun hit is triggered)
    boolean AccessoryResetAction = false; //: [reveal ball, reveal player dot]

    
    //Window size
    static int xSize = 800;
    static int ySize = 600;
    
    //player objects
    playerBox box1 = new playerBox(this, 1, xSize, ySize);
    playerBox box2 = new playerBox(this, 2, xSize, ySize);
    
    //midline object
    midLine mid = new midLine(this);
    
    //ball object
    ball pongBall = new ball(this);
    
    int k = 0;
    
    //constructor
    public ODSYRunner(){
        addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
                int keyA = detPlayerAction(e);
                if(keyA == -1)
                    return;
                else if(keyA < 4)
                    box1.keyReleased(keyA);
				else if(keyA < 8)
                    box2.keyReleased(keyA%4);
                else if(keyA == 8)
                    pongBall.addSpin();
                else if(keyA == 9)
                    pongBall.addSpeed();
			}
            
			@Override
			public void keyPressed(KeyEvent e) {
                int keyB = detPlayerAction(e);
                if(keyB == -1)
                    return;
                else if(keyB < 4)
                    box1.keyPressed(keyB);
                else if(keyB < 8)
				    box2.keyPressed((keyB%4));
			}
		});
		setFocusable(true);
    }
    
    //calls movement methods of all player actors
	private void moveBox() {
		box1.move();
        box2.move();
	}
    
    private void moveBall(){
        pongBall.move();
    }
    
    private int detPlayerAction(KeyEvent e){
        int a = e.getKeyCode();
        if (a == KeyEvent.VK_A)
            return 0;
        if (a == KeyEvent.VK_D)
            return 1;
        if (a == KeyEvent.VK_W)
            return 2;
        if (a == KeyEvent.VK_S)
            return 3;
        if (a == KeyEvent.VK_LEFT)
            return 4;
        if (a == KeyEvent.VK_RIGHT)
            return 5;
        if (a == KeyEvent.VK_UP)
            return 6;
        if (a == KeyEvent.VK_DOWN)
            return 7;
        
        if (a == KeyEvent.VK_MINUS)
            return 8;
        
        
        if (a == KeyEvent.VK_0)
            return 9;
        
        
        if (a == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        
        return -1;
    }
    
    private boolean ball1Collide(){
        return false;
    }
    
    private boolean ball2Collide(){
        return false;
    }
    
    @Override
    public void paint(Graphics g) {//
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Call paint method of all actors
        g2d.setColor(Color.BLACK);
        g2d.fillRect( 0, 0, xSize, ySize);
        
        g2d.setColor(Color.WHITE);
        box1.paint(g2d);
        box2.paint(g2d);
        mid.paint(g2d);
        pongBall.paint(g2d);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("ODSY Redux");
        ODSYRunner game = new ODSYRunner();
        frame.add(game);
        frame.setSize(xSize, ySize);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            //call 
            
            //INSERT FUNCTION HERE TO GET INPUT FROM CONTROLLER
            
            //check for colisions
            if(ball1Collide()){
                pongBall.setDirection(true);
            }
            if(ball2Collide()){
                pongBall.setDirection(false);
            }
            
            //repaint everything
            game.moveBox();
            game.moveBall();
            game.repaint();
            Thread.sleep(5);
        }
    }
}